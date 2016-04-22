package com.dig_i.front.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.InformationSearch;
import com.dig_i.front.util.NGram;

@Repository
public class InformationSearchDao extends AbstractDao<InformationSearch> {

	public String queryWordFilter(String word) {
		String[] words = StringUtils.split(word);
		List<String> parts = new ArrayList<String>();
		for (String w : words) {
			parts.addAll(NGram.split(w));
		}
		if (parts.isEmpty()) {
			return null;
		}
		return "\"" + "+" + StringUtils.join(parts, " +") + "\"";
	}

	public List<Information> findByWord(String word, Integer limit, Integer page) {
		if (StringUtils.isEmpty(word)) {
			return null;
		}
		int offset = page == null || page < 1 ? 0 : (page - 1) * limit;
		String ftsw = queryWordFilter(word);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i FROM InformationSearch si JOIN si.information i JOIN fetch i.resource ");
		sb.append("WHERE match_against(si.description, :word) > 0 ");
		sb.append("ORDER BY si.id DESC");
		TypedQuery<Information> tq = getEntityManager()
				.createQuery(sb.toString(), Information.class)
				.setParameter("word", ftsw).setMaxResults(limit)
				.setFirstResult(offset);
		return tq.getResultList();
	}

	public List<Information> findByWord(String word, int limit,
			List<Integer> excludes) {
		String ftsw = queryWordFilter(word);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i FROM InformationSearch si JOIN si.information i JOIN fetch i.resource ");
		sb.append("WHERE match_against(si.description, :word) > 0 ");
		if (excludes != null && !excludes.isEmpty()) {
			sb.append("AND si.id < :excludes ");
		}
		sb.append("ORDER BY si.id DESC");
		TypedQuery<Information> tq = getEntityManager()
				.createQuery(sb.toString(), Information.class)
				.setParameter("word", ftsw).setMaxResults(limit);

		if (excludes != null && !excludes.isEmpty()) {
			tq.setParameter("excludes", Collections.min(excludes));
		}
		return tq.getResultList();
	}
}
