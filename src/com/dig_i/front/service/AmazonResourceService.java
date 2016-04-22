package com.dig_i.front.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.LanguageCode;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.service.amazon.AmazonProductAdvertisingClient;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;

@Scope("prototype")
@Service
public class AmazonResourceService extends TagBaseTraversalResource {

	private AmazonProductAdvertisingClient amazonProductAdvertisingClient;

	// private static int TRAVERSAL_NUM = 5;

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

	public void buildAWSClient() {
		if (amazonProductAdvertisingClient == null) {
			TextProvider tp = new DefaultTextProvider();
			amazonProductAdvertisingClient = new AmazonProductAdvertisingClient(
					tp.getText("app.amazon.apiKey"),
					AmazonProductAdvertisingClient.CountryCode.JA,
					tp.getText("app.amazon.secretKey"),
					tp.getText("app.amazon.associateTag"));
		}
	}

	public Resource traversal(Resource resource, Tag tag) throws IOException,
			Exception {
		buildAWSClient();
		List<Information> informations = amazonProductAdvertisingClient
				.itemSearch(tag.getName());
		if (informations == null) {
			return resource;
		}
		for (Information info : informations) {
			info.setResourceId(resource.getId());
			info.setCategories(resource.getCategories());
			info.setLanguageCode(new Locale(LanguageCode.JA.toString()));
		}
		resource.setInformations(informations);
		return resource;
	}

}
