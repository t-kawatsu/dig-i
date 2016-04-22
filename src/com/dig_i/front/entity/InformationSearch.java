package com.dig_i.front.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "information_searches")
public class InformationSearch extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer informationId;
	private String description;
	private List<String> relateWords;
	private Information information;
	private List<TagInformation> tagInformations;

	@Id
	@Column(name = "information_id")
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return informationId;
	}

	public void setId(Integer informationId) {
		this.informationId = informationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "relate_words")
	@Type(type = "com.dig_i.front.hibernate.type.SpaceSplitArrayUserType")
	public List<String> getRelateWords() {
		return relateWords;
	}

	public void setRelateWords(List<String> relateWords) {
		this.relateWords = relateWords;
	}

	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "information_id", referencedColumnName = "id", insertable = false, updatable = false)
	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	@OneToMany(fetch= FetchType.LAZY)
	@JoinColumn(name = "information_id", referencedColumnName = "information_id", insertable = false, updatable = false)
	public List<TagInformation> getTagInformations() {
		return tagInformations;
	}

	public void setTagInformations(List<TagInformation> tagInformations) {
		this.tagInformations = tagInformations;
	}

}
