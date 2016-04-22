package com.dig_i.front.dao;

import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.entity.FbUser;

import org.springframework.stereotype.Repository;

@Repository
public class FbUserDao extends AbstractDao<FbUser> {

	public FbUser findByFbIdAndState(String fbId, UserStatus status) {
		if (fbId == null) {
			return null;
		}
		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT fu FROM FbUser fu JOIN fetch fu.user u WHERE fu.fbId = :fbId AND u.status = :status",
						FbUser.class).setParameter("fbId", fbId)
				.setParameter("status", status));
	}

}
