package com.dig_i.admin.service;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.dao.AdminUserDao;
import com.dig_i.front.entity.AdminUser;
import com.dig_i.front.entity.AdminUserRole;
import com.dig_i.front.util.crypt.BlowfishCrypt;

@Transactional
// @Scope("prototype")
@Service
public class AdminUserService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected AdminUserDao adminUserDao;

	public AdminUser create(String name, String password, AdminUserRole role)
			throws Exception {
		return adminUserDao.create(name, password, role);
	}

	public AdminUser login(String name, String password) throws Exception {
		AdminUser user = adminUserDao.find(name, password);
		return user;
	}

	public String detectPasswordByName(String name) throws HibernateException,
			Exception {
		AdminUser adminUser = adminUserDao.findByName(name);
		return BlowfishCrypt.decrypt(adminUser.getPassword());
	}
}