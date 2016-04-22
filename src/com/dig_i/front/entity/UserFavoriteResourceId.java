package com.dig_i.front.entity;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class UserFavoriteResourceId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private Integer resourceId;

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "resource_id")
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

}
