package com.dig_i.front.entity;

import java.util.Date;
import java.util.Locale;
import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String mailAddress;
	private String password;
	private Date lastLoginedAt;
	private Locale languageCode;
	private Date lastMailNoticedAt;
	private Integer mailNoticeFrequencyCode;
	private Boolean isTestUser;
	private EnumSet<UserAccountType> accountTypes;
	private String useragent;
	private UserStatus status;
	private Date createdAt;
	private Date updatedAt;
	private String token;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "mail_address")
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_logined_at")
	public Date getLastLoginedAt() {
		return lastLoginedAt;
	}

	public void setLastLoginedAt(Date lastLoginedAt) {
		this.lastLoginedAt = lastLoginedAt;
	}

	@Column(name = "language_code")
	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_mail_noticed_at")
	public Date getLastMailNoticedAt() {
		return lastMailNoticedAt;
	}

	public void setLastMailNoticedAt(Date lastMailNoticedAt) {
		this.lastMailNoticedAt = lastMailNoticedAt;
	}

	@Column(name = "mail_notice_frequency_code")
	public Integer getMailNoticeFrequencyCode() {
		return mailNoticeFrequencyCode;
	}

	public void setMailNoticeFrequencyCode(Integer mailNoticeFrequencyCode) {
		this.mailNoticeFrequencyCode = mailNoticeFrequencyCode;
	}

	@Enumerated(EnumType.ORDINAL)
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getUseragent() {
		return useragent;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	@Column(name = "is_test_user")
	public Boolean getIsTestUser() {
		return isTestUser;
	}

	public void setIsTestUser(Boolean isTestUser) {
		this.isTestUser = isTestUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "account_types")
	@Type(type = "com.dig_i.front.hibernate.type.UserAccountTypeUserType")
	public EnumSet<UserAccountType> getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(EnumSet<UserAccountType> accountTypes) {
		this.accountTypes = accountTypes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Transient
	public void addAccountType(UserAccountType accountType) {
		if (accountTypes == null) {
			accountTypes = EnumSet.noneOf(UserAccountType.class);
		}
		accountTypes.add(accountType);
	}

	@Transient
	public boolean hasAccountType(UserAccountType accountType) {
		if (accountTypes == null) {
			return false;
		}
		return accountTypes.contains(accountType);
	}

	@Transient
	public boolean isFacebookUser() {
		if (accountTypes == null) {
			return false;
		}
		return accountTypes.contains(UserAccountType.FACEBOOK);
	}

	@Transient
	public boolean isProperUser() {
		if (accountTypes == null) {
			return false;
		}
		return accountTypes.contains(UserAccountType.PROPER);
	}

	@Transient
	public boolean isTwitterUser() {
		if (accountTypes == null) {
			return false;
		}
		return accountTypes.contains(UserAccountType.TWITTER);
	}

	@PrePersist
	public void setPersistDate() {
		setCreatedAt(new Date());
		setUpdatedAt(new Date());
	}

	@PreUpdate
	public void setUpdateDate() {
		setUpdatedAt(new Date());
	}
}
