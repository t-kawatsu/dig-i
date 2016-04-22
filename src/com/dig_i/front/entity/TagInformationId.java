package com.dig_i.front.entity;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class TagInformationId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tagId;
	private Integer informationId;

	@Column(name = "tag_id")
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	@Column(name = "information_id")
	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

}
