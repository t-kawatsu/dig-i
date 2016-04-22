package com.dig_i.front.dao;

import java.util.EnumSet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserAccountType;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.util.crypt.BlowfishCrypt;

@Repository
public class UserDao extends AbstractDao<User> {

	public int countByMailAddressAndStatusAndAccountType(
			final String mailAddress, final UserStatus status,
			final UserAccountType accountType) {
		if (mailAddress == null || status == null || accountType == null) {
			return 0;
		}

		return ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM User u WHERE u.mailAddress = :mailAddress AND u.status = :status AND find_in_set(:accountTypes, u.accountTypes) > 0 ")
				.setParameter("mailAddress", mailAddress)
				.setParameter("status", status)
				.setParameter("accountTypes", EnumSet.of(accountType))
				.getSingleResult()).intValue();
	}

	public int countByMailAddressAndStatus(final String mailAddress,
			final UserStatus status) {
		if (mailAddress == null || status == null) {
			return 0;
		}

		return ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM User u WHERE u.mailAddress = :mailAddress AND u.status = :status ")
				.setParameter("mailAddress", mailAddress)
				.setParameter("status", status).getSingleResult()).intValue();
	}

	public int countByMailAddressAndPasswordAndStatus(final String mailAddress,
			final String plainPassword, final UserStatus status)
			throws Exception {
		if (mailAddress == null || plainPassword == null) {
			return 0;
		}
		String encryptedPass = BlowfishCrypt.encrypt(plainPassword);
		return ((Long) this
				.getEntityManager()
				.createQuery(
						"SELECT count(*) FROM User u WHERE u.mailAddress = :mailAddress AND u.password = :password AND u.status = :status")
				.setParameter("mailAddress", mailAddress)
				.setParameter("password", encryptedPass)
				.setParameter("status", status).getSingleResult()).intValue();
	}

	public User findByMailAddressAndStatus(final String mailAddress,
			final UserStatus status) {
		if (mailAddress == null || status == null) {
			return null;
		}

		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT u FROM User u WHERE u.mailAddress = :mailAddress AND u.status = :status",
						User.class).setParameter("mailAddress", mailAddress)
				.setParameter("status", status));
	}

	public User findByMailAddressAndPasswordAndStatus(String mailAddress,
			String plainPassword, UserStatus status) throws Exception {
		String encryptedPass = BlowfishCrypt.encrypt(plainPassword);
		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT u FROM User u WHERE u.mailAddress = :mailAddress AND u.password = :password AND u.status = :status",
						User.class).setParameter("mailAddress", mailAddress)
				.setParameter("password", encryptedPass)
				.setParameter("status", status));
	}

	@Transactional
	public User updatePassword(User user, String password) throws Exception {
		String encryptedPassword = BlowfishCrypt.encrypt(password);
		user.setPassword(encryptedPassword);
		update(user);
		return user;
	}
}
