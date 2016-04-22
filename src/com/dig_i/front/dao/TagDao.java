package com.dig_i.front.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.dig_i.front.entity.Tag;

@Repository
public class TagDao extends AbstractDao<Tag> {

	public List<Tag> findRelateTagsByTagId(final Integer id, int limit) {
		if (id == null) {
			return null;
		}

		return getEntityManager()
				.createQuery(
						"SELECT t FROM UserTag ut JOIN ut.tag t JOIN ut.userTags _ut WHERE _ut.tagId = (:id) AND ut.tagId != (:id) GROUP BY ut.tagId ORDER BY COUNT(ut.id) DESC",
						Tag.class).setParameter("id", id).setMaxResults(limit)
				.getResultList();
	}

	public List<Tag> findTagsOrderByNew(int limit) {
		return getEntityManager()
				.createQuery(
						"SELECT t FROM UserTag ut JOIN ut.tag t GROUP BY ut.tagId ORDER BY ut.id DESC",
						Tag.class).setMaxResults(limit).getResultList();
	}

	public Tag findByName(String name) {
		return returnNullIfNoResult(getEntityManager().createQuery(
				"SELECT t FROM Tag t WHERE t.name LIKE :name", Tag.class)
				.setParameter("name", name));
	}

	public List<Tag> findProximity(String name, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT t FROM TagInformation ti JOIN ti.tag t WHERE ");

		// 最初の文字は変換しない (indexきかないから)
		List<String> queries = new ArrayList<String>();
		int len = name.length();
		for (int i = 1; i < len; i++) {
			queries.add("t.name like ?" + i);
		}
		sb.append(StringUtils.join(queries, " OR "));
		sb.append(" GROUP BY t.name ORDER BY COUNT(*) DESC ");

		TypedQuery<Tag> tq = getEntityManager().createQuery(sb.toString(),
				Tag.class);

		for (int i = 1; i < len; i++) {
			char[] carr = name.toCharArray();
			// とりあえず間と最後のみ拡張
			carr[i] = '%';
			// carr = ArrayUtils.add(carr[i] = '%', '%');
			tq.setParameter(i, new String(carr) + '%');
		}

		List<Tag> tags = tq.setMaxResults(limit).getResultList();
		if (tags == null || tags.isEmpty()) {
			return tags;
		}

		List<Tag> ret = new ArrayList<Tag>();
		for (Tag t : tags) {
			if (t.getName().equals(name)) {
				continue;
			}
			ret.add(t);
		}
		return ret;
	}
}
