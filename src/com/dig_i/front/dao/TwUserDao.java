package com.dig_i.front.dao;

import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.entity.TwUser;

import org.springframework.stereotype.Repository;

@Repository
public class TwUserDao extends AbstractDao<TwUser> {

	public TwUser findByTwIdAndState(String twId, UserStatus status) {
		if (twId == null) {
			return null;
		}
		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT tu FROM TwUser tu JOIN fetch tu.user u WHERE tu.twId = :twId AND u.status = :status",
						TwUser.class).setParameter("twId", twId)
				.setParameter("status", status));
	}

}
