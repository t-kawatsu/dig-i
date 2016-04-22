package com.dig_i.admin.form;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dig_i.front.entity.ResourceCategory;
import com.dig_i.front.entity.ResourceStatus;
import com.dig_i.front.entity.ResourceType;
import com.dig_i.front.form.AbstractForm;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component
public class ResourceForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String CATEGORY_CODE = "C004";
	public static final String MATCH_URL = "^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$";

	private String title;
	private String link;
	private String description;
	private String imageTitle;
	private String imageLink;
	private String imageUrl;
	private String url;
	private ResourceType type;
	private EnumSet<ResourceCategory> categories;
	private Locale languageCode;
	private ResourceStatus status;

	private Map<String, String> types;
	private Map<String, String> statuses;
	private Map<String, String> categoryList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public EnumSet<ResourceCategory> getCategories() {
		return categories;
	}

	// @TypeConversion("com.dig_i.front.util.ResourceCategoryMultiCheckEnumTypeConverter")
	public void setCategories(String categories) {
		EnumSet<ResourceCategory> eSet = null;
		if (!StringUtils.isEmpty(categories)) {
			eSet = EnumSet.noneOf(ResourceCategory.class);
			for (String v : categories.split(",")) {
				eSet.add(ResourceCategory.valueOf(v));
			}
		}
		this.categories = eSet;
	}

	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}

	public ResourceStatus getStatus() {
		return status;
	}

	public void setStatus(ResourceStatus status) {
		this.status = status;
	}

	public Map<String, String> getTypes() {
		if (types == null) {
			types = getEnumSelectList(ResourceType.class);
		}
		return types;
	}

	public Map<String, String> getStatuses() {
		if (statuses == null) {
			statuses = getEnumSelectList(ResourceStatus.class);
		}
		return statuses;
	}

	public Map<String, String> getCategoryList() {
		if (categoryList == null) {
			categoryList = getEnumSelectList(ResourceCategory.class,
					CATEGORY_CODE);
		}
		return categoryList;
	}

	public boolean validate(ActionSupport as) {
		if (url == null) {
			as.addFieldError("resourceForm.url",
					as.getText("invalidate.required"));
		}

		return !as.hasFieldErrors();
	}

	public boolean validateUrl(ActionSupport as) {
		if (url == null) {
			as.addFieldError("resourceForm.url",
					as.getText("invalidate.required"));
		} else if (!getUrl().toString().matches(MATCH_URL)) {
			as.addFieldError("resourceForm.url", as.getText("invalidate.url"));
		} else {
			/*
			try {
				new URL(getUrl());
			} catch (Exception e) {
				as.addFieldError("resourceForm.url",
						as.getText("invalidate.valueForm"));
			}*/
		}
		return !as.hasFieldErrors();
	}
}
