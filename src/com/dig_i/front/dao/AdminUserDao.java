package com.dig_i.front.dao;

import java.util.EnumSet;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.entity.AdminUser;
import com.dig_i.front.entity.AdminUserRole;
import com.dig_i.front.util.crypt.BlowfishCrypt;

@Repository
@Transactional
public class AdminUserDao extends AbstractDao<AdminUser> {

	public AdminUser create(String name, String password, AdminUserRole role)
			throws Exception {
		AdminUser user = new AdminUser();
		user.setName(name);
		user.setPassword(BlowfishCrypt.encrypt(password));
		user.setRoles(EnumSet.of(role));
		this.persist(user);
		return user;
	}

	public AdminUser find(String name, String plainPassword)
			throws HibernateException, Exception {
		if (name == null || plainPassword == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u FROM AdminUser u WHERE ");
		sb.append("u.name = :name AND u.password = :password ");

		return returnNullIfNoResult(getEntityManager()
				.createQuery(sb.toString(), AdminUser.class)
				.setParameter("name", name)
				.setParameter("password", BlowfishCrypt.encrypt(plainPassword)));
	}

	public AdminUser findByName(String name) throws HibernateException,
			Exception {
		if (name == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u FROM AdminUser u WHERE ");
		sb.append("u.name = :name");

		return returnNullIfNoResult(getEntityManager().createQuery(
				sb.toString(), AdminUser.class).setParameter("name", name));
	}
}
