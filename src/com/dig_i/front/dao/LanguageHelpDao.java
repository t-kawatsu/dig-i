package com.dig_i.front.dao;

import java.util.List;
import java.util.Locale;

import com.dig_i.front.entity.LanguageHelp;

import org.springframework.stereotype.Repository;

@Repository
public class LanguageHelpDao extends AbstractDao<LanguageHelp> {

	public LanguageHelp findById(final Integer id) {
		Locale languageCode = new Locale("ja");
		return findById(id, languageCode);
	}

	public LanguageHelp findById(final Integer id, final Locale languageCode) {
		if (id == null) {
			return null;
		}
		return getEntityManager()
				.createQuery(
						"SELECT lh FROM LanguageHelp lh WHERE lh.id = (:id) AND lh.languageCode = (:languageCode)",
						entityClass).setParameter("id", id)
				.setParameter("languageCode", languageCode).getSingleResult();
	}

	public List<LanguageHelp> findAll() {
		Locale languageCode = new Locale("ja");
		return this
				.getEntityManager()
				.createQuery(
						"SELECT lh FROM LanguageHelp lh WHERE lh.languageCode = :languageCode",
						LanguageHelp.class)
				.setParameter("languageCode", languageCode).getResultList();
	}

	public void persist(String title, String description, Locale languageCode) {
		LanguageHelp languageHelp = new LanguageHelp();
		languageHelp.setTitle(title);
		languageHelp.setDescription(description);
		languageHelp.setLanguageCode(languageCode);
		persist(languageHelp);
	}

	public void update(LanguageHelp entity, String title, String description) {
		entity.setTitle(title);
		entity.setDescription(description);
		update(entity);
	}
}
