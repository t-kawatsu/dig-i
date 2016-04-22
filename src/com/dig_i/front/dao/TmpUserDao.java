package com.dig_i.front.dao;

import com.dig_i.front.util.crypt.MD5Crypt;
import com.dig_i.front.util.crypt.BlowfishCrypt;
import com.dig_i.front.entity.TmpUser;

import org.springframework.stereotype.Repository;

@Repository
public class TmpUserDao extends AbstractDao<TmpUser> {

	public TmpUser findByToken(final String token) {
		if (token == null) {
			return null;
		}

		return getEntityManager()
				.createQuery(
						"SELECT tu FROM TmpUser tu WHERE tu.token = :token",
						TmpUser.class).setParameter("token", token)
				.getSingleResult();
	}

	public TmpUser create(String plainPassword, String mailAddress)
			throws Exception {
		TmpUser tmpUser = new TmpUser();
		tmpUser.setMailAddress(mailAddress);
		String encryptedPass = BlowfishCrypt.encrypt(plainPassword);
		tmpUser.setPassword(encryptedPass);
		persist(tmpUser);
		tmpUser.setToken(MD5Crypt.crypt(tmpUser.getId().toString()));
		persist(tmpUser);
		return tmpUser;
	}
}
