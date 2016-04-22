package com.dig_i.api.dto;

import java.net.URL;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;

import com.dig_i.front.entity.ResourceCategory;
import com.dig_i.front.entity.ResourceType;

public class InformationItem {

	private Integer id;
	private Integer resourceId;
	private String title;
	private URL link;
	private URL url;
	private String description;
	private URL imageUrl;
	private Date publishedAt;
	private EnumSet<ResourceCategory> categories;
	private Locale languageCode;
	private Date createdAt;

	private String resourceTitle;
	private URL resourceLink;
	private ResourceType resourceType;
	private URL resourceImageUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public URL getLink() {
		return link;
	}

	public void setLink(URL link) {
		this.link = link;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URL getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public EnumSet<ResourceCategory> getCategories() {
		return categories;
	}

	public void setCategories(EnumSet<ResourceCategory> categories) {
		this.categories = categories;
	}

	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	public URL getResourceLink() {
		return resourceLink;
	}

	public void setResourceLink(URL resourceLink) {
		this.resourceLink = resourceLink;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public URL getResourceImageUrl() {
		return resourceImageUrl;
	}

	public void setResourceImageUrl(URL resourceImageUrl) {
		this.resourceImageUrl = resourceImageUrl;
	}
}
