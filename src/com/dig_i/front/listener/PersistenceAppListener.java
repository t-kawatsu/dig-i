package com.dig_i.front.listener;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import com.dig_i.front.hibernate.HibernateUtil;

public class PersistenceAppListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent evt) {
	}

	public void contextDestroyed(ServletContextEvent evt) {
		HibernateUtil.closeEntityManagerFactory();
	}
}
