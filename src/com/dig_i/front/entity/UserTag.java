package com.dig_i.front.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_tags")
public class UserTag extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Integer tagId;
	private Date lastReadedAt;
	private Date informationUpdatedAt;
	private Date createdAt;
	private Tag tag;
	private List<UserTag> userTags;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "tag_id")
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_readed_at")
	public Date getLastReadedAt() {
		return lastReadedAt;
	}

	public void setLastReadedAt(Date lastReadedAt) {
		this.lastReadedAt = lastReadedAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "information_updated_at")
	public Date getInformationUpdatedAt() {
		return informationUpdatedAt;
	}

	public void setInformationUpdatedAt(Date informationUpdatedAt) {
		this.informationUpdatedAt = informationUpdatedAt;
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
	@JoinColumn(name = "tag_id", referencedColumnName = "id", insertable = false, updatable = false)
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@OneToMany(fetch= FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	public List<UserTag> getUserTags() {
		return userTags;
	}

	public void setUserTags(List<UserTag> userTags) {
		this.userTags = userTags;
	}

	@PrePersist
	public void setPersistDate() {
		setCreatedAt(new Date());
	}
}
