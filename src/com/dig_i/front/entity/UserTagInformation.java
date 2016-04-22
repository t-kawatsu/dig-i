package com.dig_i.front.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_tag_informations")
public class UserTagInformation extends AbstractCompositeIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserTagInformationId id;
	private Information information;
	private Date createdAt;
	private List<TagInformation> tagInformations;

	@EmbeddedId
	public UserTagInformationId getId() {
		return id;
	}

	public void setId(UserTagInformationId id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@ManyToOne(fetch= FetchType.LAZY)
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

	@PrePersist
	public void setPersistDate() {
		setCreatedAt(new Date());
	}
}
