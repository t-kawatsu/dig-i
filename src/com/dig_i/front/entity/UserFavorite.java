package com.dig_i.front.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_favorites")
public class UserFavorite extends AbstractCompositeIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserFavoriteId id;
	private Date createdAt;
	private Information information;

	@EmbeddedId
	public UserFavoriteId getId() {
		return id;
	}

	public void setId(UserFavoriteId id) {
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

	@PrePersist
	public void setPersistDate() {
		setCreatedAt(new Date());
	}
}
