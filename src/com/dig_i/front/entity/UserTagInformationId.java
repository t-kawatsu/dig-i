package com.dig_i.front.entity;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class UserTagInformationId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userTagId;
	private Integer informationId;

	@Column(name = "user_tag_id")
	public Integer getUserTagId() {
		return userTagId;
	}

	public void setUserTagId(Integer userTagId) {
		this.userTagId = userTagId;
	}

	@Column(name = "information_id")
	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

}
