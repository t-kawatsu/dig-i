package com.dig_i.front.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dig_i.front.entity.LanguageSetting;
import com.dig_i.front.entity.LanguageSettingId;

@Repository
public class LanguageSettingDao extends AbstractDao<LanguageSetting> {

	public LanguageSetting findByCode(final String code) {
		Locale languageCode = new Locale("ja");
		return findByCode(code, languageCode);
	}

	public LanguageSetting findByCode(final String code,
			final Locale languageCode) {
		if (code == null) {
			return null;
		}
		return getEntityManager()
				.createQuery(
						"SELECT ls FROM LanguageSetting ls WHERE ls.id.code = (:settingCode) AND ls.id.languageCode = (:languageCode)",
						LanguageSetting.class)
				.setParameter("settingCode", code)
				.setParameter("languageCode", languageCode).getSingleResult();
	}

	public String getContentsValue(final String code, final String key) {
		Locale languageCode = new Locale("ja");
		return getContentsValue(code, key, languageCode);
	}

	public String getContentsValue(final String code, final String key,
			final Locale languageCode) {
		Map<String, String> contents = findByCode(code, languageCode)
				.getContents();
		return contents.get(key);
	}

	public void persist(String code, Locale languageCode, String contents)
			throws IOException {
		LanguageSetting ls = new LanguageSetting();
		LanguageSettingId id = new LanguageSettingId();
		id.setCode(code);
		id.setLanguageCode(languageCode);
		ls.setId(id);
		ls.setContents(stringToMapContents(contents));
		persist(ls);
	}

	public void update(LanguageSettingId id, String contents)
			throws IOException {
		LanguageSetting ls = findById(id);
		ls.setContents(stringToMapContents(contents));
		update(ls);
	}

	public String mapToStringContents(Map<String, String> maps) {
		if (maps == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> e : maps.entrySet()) {
			sb.append(e.getKey() + ":" + e.getValue()
					+ System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	private Map<String, String> stringToMapContents(String string)
			throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(string));
		Map<String, String> map = new LinkedHashMap<String, String>();
		String line;
		while ((line = reader.readLine()) != null) {
			int index = line.indexOf(":");
			if (index == -1) {
				throw new IllegalArgumentException();
			}
			String key = line.substring(0, index);
			String value = line.substring(index + 1);
			map.put(key, value);
		}
		return map;
	}
}
