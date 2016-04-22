package com.dig_i.front.interceptor;

import javax.persistence.EntityManager;

import com.dig_i.front.hibernate.HibernateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class EntityManagerInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// protected static final Logger logger =
	// LoggerFactory.getLogger(EntityManagerInterceptor.class);

	public void init() {
		// logger.debug(getClass().getSimpleName() + "#init()");
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// logger.debug(getClass().getSimpleName() + "#intercept()");
		EntityManager em = HibernateUtil.getEntityManagerFactory()
				.createEntityManager();
		HibernateUtil.setEntityManager(em);
		String ret = invocation.invoke();
		HibernateUtil.removeEntityManager();
		return ret;
	}

}
