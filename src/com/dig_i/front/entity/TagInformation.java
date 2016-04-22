package com.dig_i.front.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tag_informations")
public class TagInformation extends AbstractCompositeIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TagInformationId id;
	private Information information;
	private Tag tag;

	@EmbeddedId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public TagInformationId getId() {
		return id;
	}

	public void setId(TagInformationId id) {
		this.id = id;
	}

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "information_id", referencedColumnName = "id", insertable = false, updatable = false)
	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "tag_id", referencedColumnName = "id", insertable = false, updatable = false)
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
