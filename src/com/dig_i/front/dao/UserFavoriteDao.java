package com.dig_i.front.dao;

import java.util.List;

import com.dig_i.front.entity.UserFavorite;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.UserFavoriteId;

import com.dig_i.front.util.SimplePager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserFavoriteDao extends AbstractDao<UserFavorite> {

	public int countByInformationId(final Integer informationId) {
		if (informationId == null) {
			return 0;
		}
		return ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserFavorite uf WHERE uf.id.informationId = :informationId")
				.setParameter("informationId", informationId).getSingleResult())
				.intValue();
	}

	public UserFavorite findByUserIdAndInformationId(final Integer userId,
			final Integer informationId) {
		if (informationId == null || userId == null) {
			return null;
		}
		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT uf FROM UserFavorite uf WHERE uf.id.informationId = :informationId AND uf.id.userId = :userId",
						UserFavorite.class)
				.setParameter("informationId", informationId)
				.setParameter("userId", userId));
	}

	public boolean isUserFavorited(final Integer userId,
			final Integer informationId) {
		int res = ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserFavorite uf WHERE uf.id.informationId = :informationId AND uf.id.userId = :userId")
				.setParameter("informationId", informationId)
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
						"SELECT count(*) FROM UserFavorite uf WHERE uf.id.userId = :userId")
				.setParameter("userId", userId).getSingleResult()).intValue();
	}

	public SimplePager<Information> paginateByUserId(int limit,
			final Integer page, Integer userId) {
		int _page = page == null ? 1 : (int) page;
		int total = ((Long) getEntityManager()
				.createQuery(
						"SELECT count(*) FROM UserFavorite uf JOIN uf.information i WHERE uf.id.userId = :userId")
				.setParameter("userId", userId).getSingleResult()).intValue();
		if (total == 0) {
			return new SimplePager<Information>(limit, _page, null, total);
		}
		List<Information> rows = getEntityManager()
				.createQuery(
						"SELECT i FROM UserFavorite uf JOIN uf.information i JOIN fetch i.resource WHERE uf.id.userId = :userId",
						Information.class).setParameter("userId", userId)
				.setFirstResult((_page - 1) * limit).setMaxResults(limit)
				.getResultList();
		return new SimplePager<Information>(limit, _page, rows, total);
	}

	public int deleteByUserId(Integer userId) {
		return getEntityManager()
				.createQuery(
						"DELETE FROM UserFavorite uf WHERE uf.id.userId = :userId")
				.setParameter("userId", userId).executeUpdate();
	}
	
	@Transactional
	public void create(Integer userId, Integer informationId) {
		UserFavoriteId userFavoriteId = new UserFavoriteId();
		userFavoriteId.setUserId(userId);
		userFavoriteId.setInformationId(informationId);
		UserFavorite userFavorite = new UserFavorite();
		userFavorite.setId(userFavoriteId);
		persist(userFavorite);
	}
}
