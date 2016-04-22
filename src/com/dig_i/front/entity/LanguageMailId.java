package com.dig_i.front.entity;

import java.util.Locale;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
public class LanguageMailId extends AbstractEmbeddable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Locale languageCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "language_code")
	public Locale getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(Locale languageCode) {
		this.languageCode = languageCode;
	}

}
