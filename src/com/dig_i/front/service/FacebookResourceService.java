package com.dig_i.front.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.LanguageCode;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.service.facebook.MyDefaultFacebookClient;
import com.dig_i.front.service.facebook.MyFacebookClient;
import com.dig_i.front.util.UrlHelper;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import com.restfb.types.Application;
import com.restfb.types.Event;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Page;

@Scope("prototype")
@Service
public class FacebookResourceService extends TagBaseTraversalResource {

	private MyFacebookClient facebookClient;
	private static int TRAVERSAL_NUM = 5;

	public List<Information> extractNewInformations(Resource oldOne,
			Resource newOne) {
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

	public void buildFacebookClient() {
		if (facebookClient == null) {
			TextProvider tp = new DefaultTextProvider();
			MyFacebookClient.AccessToken accessToken = new MyDefaultFacebookClient()
					.obtainAppAccessToken(tp.getText("app.facebook.appId"),
							tp.getText("app.facebook.secret"));
			facebookClient = new MyDefaultFacebookClient(
					accessToken.getAccessToken());
		}
	}

	public Resource traversal(Resource resource, Tag tag) throws IOException,
			Exception {
		Map<String, String> queryMap = UrlHelper.parseQuery(resource.getUrl().getQuery());
		if (queryMap == null) {
			return resource;
		}

		buildFacebookClient();
		List<Information> informations = new ArrayList<Information>();
		String type = queryMap.get("type");
		String fields = queryMap.get("fields");

		if ("page".equals(type)) {
			List<Page> res = facebookClient.search(type, Page.class,
					tag.getName(), fields, TRAVERSAL_NUM);
			if (res != null) {
				for (Page row : res) {
					Information info = mapCommonValue(row);
					info.setDescription(row.getDescription());
					//info.setImageUrl(row.getPicture());
					info.setResourceId(resource.getId());
					info.setCategories(resource.getCategories());
					informations.add(info);
				}
			}
		} else if ("event".equals(type)) {
			List<Event> res = facebookClient.search(type, Event.class,
					tag.getName(), fields, TRAVERSAL_NUM);
			if (res != null) {
				for (Event row : res) {
					Information info = mapCommonValue(row);
					info.setDescription(row.getDescription());
					info.setResourceId(resource.getId());
					info.setCategories(resource.getCategories());
					informations.add(info);
				}
			}
		} else if ("application".equals(type)) {
			List<Application> res = facebookClient.search(type,
					Application.class, tag.getName(), fields, TRAVERSAL_NUM);
			if (res != null) {
				for (Application row : res) {
					Information info = mapCommonValue(row);
					info.setDescription(row.getDescription());
					info.setResourceId(resource.getId());
					info.setCategories(resource.getCategories());
					informations.add(info);
				}
			}
		}

		resource.setInformations(informations);
		return resource;
	}

	private Information mapCommonValue(NamedFacebookType row) {
		Information info = new Information();
		info.setTitle(row.getName());
		try {
			info.setLink(new URL(MyFacebookClient.FACEBOOK_ENDPOINT_URL + "/" + row.getId()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		}
		info.setPublishedAt(new Date());
		info.setLanguageCode(new Locale(LanguageCode.JA.toString()));
		return info;
	}
}
