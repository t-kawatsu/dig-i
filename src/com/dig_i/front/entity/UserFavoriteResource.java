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
@Table(name = "user_favorite_resources")
public class UserFavoriteResource extends AbstractCompositeIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserFavoriteResourceId id;
	private Resource resource;
	private Date createdAt;

	@EmbeddedId
	public UserFavoriteResourceId getId() {
		return id;
	}

	public void setId(UserFavoriteResourceId id) {
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
	@JoinColumn(name = "resource_id", referencedColumnName = "id", insertable = false, updatable = false)
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@PrePersist
	public void setPersistDate() {
		setCreatedAt(new Date());
	}
}
