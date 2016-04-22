package com.dig_i.front.dao;

import java.util.Locale;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.entity.LanguageMail;
import com.dig_i.front.entity.LanguageMailId;

@Repository
@Transactional
public class LanguageMailDao extends AbstractDao<LanguageMail> {

	public LanguageMail findByCode(final String code) {
		return findByCode(code, new Locale("ja"));
	}

	public LanguageMail findByCode(final String code, final Locale locale) {
		if (code == null || locale == null) {
			return null;
		}
		return this
				.getEntityManager()
				.createQuery(
						"SELECT lm FROM LanguageMail lm WHERE lm.id.code = :code AND lm.id.languageCode = :languageCode",
						LanguageMail.class).setParameter("code", code)
				.setParameter("languageCode", locale).getSingleResult();
	}

	public void update(LanguageMail entity, String subject, String body) {
		entity.setSubject(subject);
		entity.setBody(body);
		update(entity);
	}

	public void persist(String code, String subject, String body,
			Locale languageCode) {
		LanguageMail languageMail = new LanguageMail();
		LanguageMailId id = new LanguageMailId();
		id.setCode(code);
		id.setLanguageCode(languageCode);
		languageMail.setId(id);
		languageMail.setSubject(subject);
		languageMail.setBody(body);
		persist(languageMail);
	}
}
