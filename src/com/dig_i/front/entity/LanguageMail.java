package com.dig_i.front.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.Cacheable;

@Cacheable(true)
@Entity
@Table(name = "language_mails")
public class LanguageMail extends AbstractCompositeIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LanguageMailId id;
	private String subject;
	private String body;

	@EmbeddedId
	public LanguageMailId getId() {
		return id;
	}

	public void setId(LanguageMailId id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
