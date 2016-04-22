package com.dig_i.api.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dig_i.api.dto.InformationItem;
import com.dig_i.front.entity.Information;

@Service
public class ApiService {

	public List<InformationItem> convertInformationItems(
			List<Information> informations) {
		if (CollectionUtils.isEmpty(informations)) {
			return null;
		}
		List<InformationItem> items = new ArrayList<InformationItem>();
		for (Information row : informations) {
			InformationItem item = new InformationItem();
			try {
				BeanUtils.copyProperties(item, row);
			} catch (Exception e) {
			}
			item.setResourceImageUrl(row.getResource().getImageUrl());
			item.setResourceTitle(row.getResource().getTitle());
			item.setResourceLink(row.getResource().getLink());
			item.setResourceType(row.getResource().getType());
			items.add(item);
		}
		return items;
	}
}
