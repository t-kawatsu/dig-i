package com.dig_i.front.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.entity.Tag;
import com.dig_i.front.entity.UserTag;

@Repository
public class UserTagDao extends AbstractDao<UserTag> {
	
	public UserTag findById(Integer id) throws Exception {
		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT ut FROM UserTag ut JOIN fetch ut.tag t WHERE ut.id = :id",
						UserTag.class).setParameter("id", id));
	}


	public List<UserTag> findPopularTags(int limit) {
		int c = 5;
		List<UserTag> result = getEntityManager()
				.createQuery(
						"SELECT ut FROM UserTag ut JOIN fetch ut.tag t GROUP BY ut.tagId ORDER BY COUNT(ut.id) DESC",
						UserTag.class).setMaxResults(limit + c).getResultList();

		if (result == null || result.isEmpty()) {
			return result;
		}

		if (result.size() < limit) {
			return result;
		}

		Collections.shuffle(result);
		return result.subList(0, limit - 1);
	}

	public int countTagRegistingUsersByTagId(final Integer tagId) {
		if (tagId == null) {
			return 0;
		}
		return ((Long) getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserTag ut WHERE ut.tagId = :tagId")
				.setParameter("tagId", tagId).getSingleResult()).intValue();
	}

	public boolean isUserRegisteredTag(final Integer userId, final Integer tagId) {
		return 0 < countByUserIdAndTagId(userId, tagId);
	}

	public List<UserTag> findByUserId(final Integer userId) {
		return getEntityManager()
				.createQuery(
						"SELECT ut FROM UserTag ut JOIN fetch ut.tag t WHERE ut.userId = :userId",
						UserTag.class).setParameter("userId", userId)
				.getResultList();
	}

	public int countByUserId(final Integer userId) {
		return ((Long) getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserTag ut WHERE ut.userId = :userId")
				.setParameter("userId", userId).getSingleResult()).intValue();
	}

	public int countByUserIdAndTagId(final Integer userId, final Integer tagId) {
		return ((Long) getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserTag ut WHERE ut.tagId = :tagId AND ut.userId = :userId")
				.setParameter("tagId", tagId).setParameter("userId", userId)
				.getSingleResult()).intValue();
	}

	public List<UserTag> findByUserIdOrderByInformationUpdatedAt(
			final Integer userId, int limit) {
		if (userId == null) {
			return null;
		}
		return getEntityManager()
				.createQuery(
						"SELECT ut FROM UserTag ut JOIN fetch ut.tag t WHERE ut.userId = :userId ORDER BY ut.informationUpdatedAt DESC",
						UserTag.class).setParameter("userId", userId)
				.setMaxResults(limit).getResultList();
	}

	public List<Tag> findRelateTagsByUserTags(List<UserTag> userTags,
			Integer userId, int limit) {
		return findRelateTagsByTagIds(detectIds(userTags), userId, limit);
	}

	public List<Tag> findRelateTagsByTagIds(List<Integer> tagIds,
			Integer userId, int limit) {
		if (userId == null || tagIds == null || tagIds.isEmpty()) {
			return null;
		}

		return getEntityManager()
				.createQuery(
						"SELECT t FROM UserTag ut JOIN ut.tag t JOIN ut.userTags _ut WHERE _ut.tagId in (:ids) AND ut.tagId NOT IN (:ids) AND ut.userId != :userId GROUP BY ut.tagId ORDER BY COUNT(ut.id) DESC",
						Tag.class).setParameter("userId", userId)
				.setParameter("ids", tagIds).setMaxResults(limit)
				.getResultList();
	}

	public int deleteByUserId(Integer userId) {
		return getEntityManager()
				.createQuery("DELETE FROM UserTag ut WHERE ut.userId = :userId")
				.setParameter("userId", userId).executeUpdate();
	}
	
	@Transactional
	public void updateLastReadedAt(UserTag userTag) {
		userTag.setLastReadedAt(new Date());
		update(userTag);
	}

}
