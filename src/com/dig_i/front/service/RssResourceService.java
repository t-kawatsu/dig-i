package com.dig_i.front.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.LanguageCode;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.ResourceStatus;
import com.dig_i.front.entity.ResourceType;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.io.XmlReaderException;

@Scope("prototype")
@Service
public class RssResourceService extends TraversalResource {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static final Pattern IMG_SRC_PATTERN = Pattern
			.compile("<\\s*img.*src\\s*=\\s*([\\\"'])?([^ \\\"']*)[^>]*>");

	public Resource traversal(Resource resource) throws IOException,
		XmlReaderException, FeedException {
		Resource res = traversal(resource.getUrl());
		return mergeResource(resource, res);
	}
	
	public Resource traversal(URL url) throws IOException,
		XmlReaderException, FeedException {
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(url));
		Resource resource = traversalResource(feed);
		resource.setUrl(url);
		List<Information> imformations = traversalInformation(feed);
		resource.setInformations(imformations);
		return resource;
	}
	
	public List<Information> extractNewInformations(Resource oldOne,
			Resource newOne) {
		if (!isRebuilded(oldOne, newOne)) {
			return null;
		}
		if (oldOne == null) {
			return newOne.getInformations();
		}
		if (newOne.getInformations() == null
				|| newOne.getInformations().isEmpty()) {
			return null;
		}
		List<Information> informations = new ArrayList<Information>();
		// DBに存在しないURLを持つinformationを抽出
		for (Information row : newOne.getInformations()) {
			if (0 < informationDao.countByLink(row.getLink())) {
				continue;
			}
			informations.add(row);
		}
		// 前回のlastBuildedAt以降に作られたinformationを抽出
		if (oldOne.getLastBuildedAt() != null && !informations.isEmpty()) {
			for (Information row : informations) {
				Date publishedAt = row.getPublishedAt();
				if (publishedAt != null
						&& !oldOne.getLastBuildedAt().before(publishedAt)) {
					// informations.add(row);
				} else {
					informations.remove(row);
				}
			}
			//return informations;
		}
		return informations;
	}

	private boolean isRebuilded(Resource oldOne, Resource newOne) {
		if (oldOne == null) {
			return true;
		}
		if (newOne == null) {
			return false;
		}
		return oldOne.getLastBuildedAt().before(newOne.getLastBuildedAt());
	}

	private Resource mergeResource(Resource oldOne, Resource newOne) {
		newOne.setId(oldOne.getId());
		newOne.setCategories(oldOne.getCategories());
		newOne.setType(oldOne.getType());
		List<Information> informations = newOne.getInformations();
		if (informations == null) {
			return newOne;
		}
		for (Information row : informations) {
			row.setResourceId(newOne.getId());
			row.setCategories(newOne.getCategories());
			if (row.getPublishedAt() == null) {
				row.setPublishedAt(newOne.getLastBuildedAt());
			}
		}
		return newOne;
	}

	private Resource traversalResource(SyndFeed feed) throws MalformedURLException {
		Resource resource = new Resource();
		resource.setTitle(feed.getTitle());
		resource.setDescription(feed.getDescription());
		resource.setLink(new URL(feed.getLink()));
		resource.setLastBuildedAt(feed.getPublishedDate());
		if (resource.getLastBuildedAt() == null) {
			resource.setLastBuildedAt(new Date());
		}
		resource.setStatus(ResourceStatus.LIVE);
		//resource.setUrl(new URL(feed.getUri()));
		resource.setLanguageCode(new Locale(LanguageCode.JA.toString()));
		if("rss_2.0".equals(feed.getFeedType())) {
			resource.setType(ResourceType.RSS2_0);
		} else {
			resource.setType(ResourceType.RSS1_0);
		}
		SyndImage image = feed.getImage();
		if (image != null) {
			resource.setImageTitle(image.getTitle());
			resource.setImageUrl(new URL(image.getUrl()));
			resource.setImageLink(new URL(image.getLink()));
		}
		return resource;
	}

	private List<Information> traversalInformation(SyndFeed feed) {
		if (feed.getEntries() == null || feed.getEntries().isEmpty()) {
			return null;
		}
		List<Information> informatios = new ArrayList<Information>();
		for (Object object : feed.getEntries()) {
		  try {
			SyndEntry entry = (SyndEntry) object;
			Information row = new Information();
			row.setTitle(entry.getTitle());
			row.setLink(new URL(entry.getLink()));
			row.setPublishedAt(entry.getPublishedDate());
			row.setLanguageCode(new Locale(LanguageCode.JA.toString()));
			// row.setCategories();
			if (entry.getDescription() != null) {
				row.setDescription(entry.getDescription().getValue());
				String imgSrc = detectImgSrc(row.getDescription());
				if (imgSrc != null) {
					if (imgSrc.startsWith("//")) {
						imgSrc = "http:" + imgSrc;
					} else if (imgSrc.startsWith("/")) {
						imgSrc = feed.getLink() + imgSrc;
					}
					row.setImageUrl(new URL(imgSrc));
				}
			}
			informatios.add(row);
		  } catch (Exception e) {
			  logger.error("{}", e.getMessage());
		  }
		}
		return informatios;
	}

	private String detectImgSrc(String html) {
		Matcher m = IMG_SRC_PATTERN.matcher(html);
		if (!m.find()) {
			return null;
		}
		return m.group(2);
	}

}
