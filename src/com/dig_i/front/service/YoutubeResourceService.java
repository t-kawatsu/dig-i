package com.dig_i.front.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.LanguageCode;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.Tag;
import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;

@Scope("prototype")
@Service
// https://developers.google.com/gdata/articles/eclipse?hl=ja
// http://code.google.com/p/google-collections/
public class YoutubeResourceService extends TagBaseTraversalResource {

	private YouTubeService ytService = new YouTubeService(null);
	private static int TRAVERSAL_NUM = 5;

	public List<Information> extractNewInformations(Resource oldOne, Resource newOne) {
		if (oldOne == null) {
			return newOne.getInformations();
		}
		if (newOne.getInformations() == null
				|| newOne.getInformations().isEmpty()) {
			return null;
		}
		List<Information> informations = new ArrayList<Information>();
		// no exists link on db
		for (Information row : newOne.getInformations()) {
			if (0 < informationDao.countByLink(row.getLink())) {
				continue;
			}
			informations.add(row);
		}
		return informations;
	}

	public Resource traversal(Resource resource, Tag tag) throws IOException,
			Exception {
		YouTubeQuery query = new YouTubeQuery(resource.getUrl());
		query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
		query.setSafeSearch(YouTubeQuery.SafeSearch.STRICT);
		query.setFullTextQuery(tag.getName());
		query.setMaxResults(TRAVERSAL_NUM);

		VideoFeed videoFeed = ytService.query(query, VideoFeed.class);
		if (videoFeed == null) {
			return resource;
		}

		List<Information> informations = new ArrayList<Information>();
		for (VideoEntry entry : videoFeed.getEntries()) {
			Information info = new Information();
			info.setTitle(entry.getTitle().getPlainText());
			info.setDescription(entry.getMediaGroup().getDescription()
					.getPlainTextContent());
			if (entry.getMediaGroup().getThumbnails() != null) {
				for (MediaThumbnail mt : entry.getMediaGroup().getThumbnails()) {
					info.setImageUrl(new URL(mt.getUrl()));
					break;
				}
			}
			info.setLink(new URL(entry.getLink("alternate", "text/html").getHref()));
			info.setResourceId(resource.getId());
			info.setCategories(resource.getCategories());
			info.setPublishedAt(new Date(entry.getPublished().getValue()));
			info.setLanguageCode(new Locale(LanguageCode.JA.toString()));
			informations.add(info);
		}
		resource.setInformations(informations);
		return resource;
	}

}
