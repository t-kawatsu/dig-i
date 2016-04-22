package com.dig_i.front.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dig_i.front.dao.InformationSearchDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.InformationSearch;
import com.dig_i.front.util.MeCab;
import com.dig_i.front.util.NGram;
import com.dig_i.front.util.filter.CharacterStandardizeFilter;
import com.dig_i.front.util.filter.ExceptSearchWordFilter;

//@Scope("prototype")
@Service
public class InformationSearchService {
	
	protected final Logger logger = LoggerFactory.getLogger(InformationSearchService.class);

	@javax.annotation.Resource
	private InformationSearchDao informationSearchDao;

	// @TODO to db or file storage
	private static final String[] NG_WORDS = { "the", "in", "mr" };

	public List<Information> recommendByWords(final String[] words, Integer limit,
			Integer page) {
		if(words == null || words.length <= 0) {
			return null;
		}
		
		// 優先順位にソート
		TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();
		for (String row : words) {
			String _word = searchWordFilter(row);
			if (_word == null) {
				continue;
			}
			int point = _word.length();
			sortMap.put(point, _word);
		}
		if (sortMap.isEmpty()) {
			return null;
		}
		
		Collection<String> _words = sortMap.descendingMap().values();
		List<Information> ret = new ArrayList<Information>();
		for(String row : _words) {
			List<Information> _ret = 
					informationSearchDao.findByWord(row, limit, page);
			if(_ret == null) {
				continue;
			}
			for(Information j : _ret) {
				for(Information k : ret) {
					if(k.getId().equals(j.getId())) {
						_ret.remove(j);
					}
				}
			}
			if(limit - ret.size() <= 0) {
				return ret.subList(0, limit);
			}
			
			ret.addAll(_ret);
		}
		
		return ret;
	}
	
	public List<Information> searchByWord(final String word, Integer limit,
			Integer page) {
		return informationSearchDao.findByWord(searchWordFilter(word), limit, page);
	}
	
	public List<Information> searchByWord(final String word, Integer limit,
			List<Integer> excludes) {
		return informationSearchDao.findByWord(searchWordFilter(word), limit, excludes);
	}

	public void bulkInsert(List<Information> informations) {
		if (informations == null || informations.isEmpty()) {
			return;
		}

		for (Information row : informations) {
		  try {
			  insert(row);
		  } catch (Exception e) {
			  logger.error("{}", e.getMessage());
			  continue;
		  }
		}
	}
	
	public void insert(Information row) {
		if(row == null) {
			return;
		}
		EntityManager em = informationSearchDao.getEntityManager();
		String[] words = extractSearchWord(row);
		InformationSearch informationSearch = new InformationSearch();
		informationSearch.setId(row.getId());
		informationSearch.setDescription(formatSearchDescription(words));
		informationSearch.setRelateWords(formatRelateWords(words));
		// informationSearch = em.merge(informationSearch);
		em.persist(informationSearch);
	}
	
	public String detectSearchWord(String word) {
		String _word = MeCab.detectMainWord(word);
		return searchWordFilter(_word);
	}
	
	public String searchWordFilter(String word) {
		if (StringUtils.isEmpty(word)) {
			return null;
		}

		String _word = CharacterStandardizeFilter.filter(word);
		if (StringUtils.isEmpty(_word)) {
			return null;
		}
		_word = ExceptSearchWordFilter.filter(word);
		if (StringUtils.isEmpty(_word)) {
			return null;
		}
		if (isUnRegistableWord(_word)) {
			return null;
		}
		return _word;
	}
	
	protected String formatSearchDescription(String[] words) {
		if (words == null || words.length == 0) {
			return null;
		}
		String word = StringUtils.join(words, null);
		return StringUtils.join(NGram.split(word), " ");
	}

	protected List<String> formatRelateWords(String[] words) {
		if (words == null || words.length == 0) {
			return null;
		}

		// 優先順位にソート
		Map<String, Integer> countMap = new HashMap<String, Integer>();

		for (String word : words) {
			word = searchWordFilter(word);
			if (word == null) {
				continue;
			}

			int point = word.length();
			if (countMap.get(word) == null) {
				countMap.put(word, point);
			} else {
				countMap.put(word, countMap.get(word) + point);
			}
		}
		if (countMap.isEmpty()) {
			return null;
		}
		
		TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();
		for(String row : countMap.keySet()) {
			sortMap.put(countMap.get(row), row);
		}
		return Arrays.asList(sortMap.descendingMap().values()
				.toArray(new String[0]));
	}

	protected String[] extractSearchWord(Information info) {
		String title = info.getTitle();
		String description = info.getDescription();
		Whitelist whitelist = Whitelist.none();
		title = Jsoup.clean(title, whitelist);
		description = description == null ? null : Jsoup.clean(description, whitelist);

		if (StringUtils.isEmpty(title) && StringUtils.isEmpty(description)) {
			return null;
		}
		try {
			String t[] = MeCab.splitWithWhitelist(title, MeCab.basicWhitelist());
			String d[] = MeCab.splitWithWhitelist(description, MeCab.basicWhitelist());
			return ArrayUtils.addAll(t, d);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * after filterForSearchWord
	 */
	public boolean isUnRegistableWord(String word) {
		word = StringUtils.trim(word);
		if (word == null) {
			return false;
		}

		if (word.length() <= 1) {
			if (StringUtils.isEmpty(word)) {
				return true;
			}

			// http://d.hatena.ne.jp/dirablue/20090506/1241607961
			// ひらがな
			if (word.matches("^[\\u3040-\\u309F]+$")) {
				return true;
			}
			// 全角カタカナ
			if (word.matches("^[\\u30A0-\\u30FF]+$")) {
				return true;
			}
			// 半角カタカナ
			if (word.matches("^[\\uFF65-\\uFF9F]+$")) {
				return true;
			}
			// 英数 全角数字も範疇
			if (word.matches("^[a-zA-Z0-9]+$")) {
				return true;
			}
		}

		return isNGWord(word);
	}
	
	protected boolean isNGWord(String word) {
		return ArrayUtils.contains(NG_WORDS, word.toLowerCase());
	}
}
