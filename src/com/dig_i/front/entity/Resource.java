package com.dig_i.front.entity;

import java.net.URL;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "resources")
public class Resource extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private URL link;
	private String description;
	private String imageTitle;
	private URL imageLink;
	private URL imageUrl;
	private Date lastBuildedAt;
	private URL url;
	private ResourceType type;
	private EnumSet<ResourceCategory> categories;
	private Locale languageCode;
	private ResourceStatus status;
	private int causeConnectCnt;
	private List<Information> informations;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "image_title")
	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	@Column(name = "image_link")
	public URL getImageLink() {
		return imageLink;
	}

	public void setImageLink(URL imageLink) {
		this.imageLink = imageLink;
	}

	@Column(name = "image_url")
	public URL getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_builded_at")
	public Date getLastBuildedAt() {
		return lastBuildedAt;
	}

	public void setLastBuildedAt(Date lastBuildedAt) {
		this.lastBuildedAt = lastBuildedAt;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Enumerated(EnumType.ORDINAL)
	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public EnumSet<ResourceCategory> getCategories() {
		return categories;
	}

	@Type(type = "com.dig_i.front.hibernate.type.ResourceCategoryUserType")
	public void setCategories(EnumSet<ResourceCategory> categories) {
		this.categories = categories;
	}

	@Column(name = "language_code")
	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}

	@Enumerated(EnumType.ORDINAL)
	public ResourceStatus getStatus() {
		return status;
	}

	public void setStatus(ResourceStatus status) {
		this.status = status;
	}

	@Column(name = "cause_connect_cnt")
	public int getCauseConnectCnt() {
		return causeConnectCnt;
	}

	public void setCauseConnectCnt(int causeConnectCnt) {
		this.causeConnectCnt = causeConnectCnt;
	}

	@Transient
	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}

}
