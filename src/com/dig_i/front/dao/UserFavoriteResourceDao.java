package com.dig_i.front.dao;

import java.util.List;
import com.dig_i.front.entity.UserFavoriteResource;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.UserFavoriteResourceId;

import com.dig_i.front.util.SimplePager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserFavoriteResourceDao extends AbstractDao<UserFavoriteResource> {

	public int countByResourceId(final Integer resourceId) {
		if (resourceId == null) {
			return 0;
		}
		return ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserFavoriteResource ufr WHERE ufr.id.resourceId = :resourceId")
				.setParameter("resourceId", resourceId).getSingleResult())
				.intValue();
	}

	public UserFavoriteResource findByUserIdAndResourceId(final Integer userId,
			final Integer resourceId) {
		if (resourceId == null || userId == null) {
			return null;
		}
		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT ufr FROM UserFavoriteResource ufr WHERE ufr.id.resourceId = :resourceId AND ufr.id.userId = :userId",
						UserFavoriteResource.class)
				.setParameter("resourceId", resourceId)
				.setParameter("userId", userId));
	}

	public boolean isUserFavorited(final Integer userId,
			final Integer resourceId) {
		int res = ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserFavoriteResource ufr WHERE ufr.id.resourceId = :resourceId AND ufr.id.userId = :userId")
				.setParameter("resourceId", resourceId)
				.setParameter("userId", userId).getSingleResult()).intValue();
		return 0 < res;
	}

	public int countByUserId(final Integer userId) {
		if (userId == null) {
			return 0;
		}
		return ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserFavoriteResource ufr WHERE ufr.id.userId = :userId")
				.setParameter("userId", userId).getSingleResult()).intValue();
	}

	public SimplePager<Resource> paginateByUserId(int limit,
			final Integer page, Integer userId) {
		int _page = page == null ? 1 : (int) page;
		int total = ((Long) getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserFavoriteResource ufr JOIN ufr.resource r WHERE ufr.id.userId = :userId")
				.setParameter("userId", userId).getSingleResult()).intValue();
		if (total == 0) {
			return new SimplePager<Resource>(limit, _page, null, total);
		}
		List<Resource> rows = getEntityManager()
				.createQuery(
						"SELECT r FROM UserFavoriteResource ufr JOIN ufr.resource r WHERE ufr.id.userId = :userId",
						Resource.class).setParameter("userId", userId)
				.setFirstResult((_page - 1) * limit).setMaxResults(limit)
				.getResultList();
		return new SimplePager<Resource>(limit, _page, rows, total);
	}

	public int deleteByUserId(Integer userId) {
		return getEntityManager()
				.createQuery(
						"DELETE FROM UserFavoriteResource ufr WHERE ufr.id.userId = :userId")
				.setParameter("userId", userId).executeUpdate();
	}
	
	@Transactional
	public void create(Integer userId, Integer resourceId) {
		UserFavoriteResourceId userFavoriteResourceId = new UserFavoriteResourceId();
		userFavoriteResourceId.setUserId(userId);
		userFavoriteResourceId.setResourceId(resourceId);
		UserFavoriteResource userFavoriteResource = new UserFavoriteResource();
		userFavoriteResource.setId(userFavoriteResourceId);
		persist(userFavoriteResource);
	}
}
