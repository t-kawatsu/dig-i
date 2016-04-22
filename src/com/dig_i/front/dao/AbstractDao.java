package com.dig_i.front.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.entity.AbstractEntity;
import com.dig_i.front.util.DbSelectPaginator;
import com.dig_i.front.util.Paginator;
import com.dig_i.front.util.SimplePager;

abstract public class AbstractDao<T> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@PersistenceContext
	private EntityManager em;
	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		Type t = getClass().getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) t;
			entityClass = (Class<T>) pt.getActualTypeArguments()[0];
		}
	}

	public T findById(final Object id) {
		if (id == null) {
			return null;
		}
		return getEntityManager().find(entityClass, id);
	}

	public EntityManager getEntityManager() {
		// return HibernateUtil.getEntityManager();
		return em;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	/*
	 * public EntityTransaction getTransaction() { return
	 * getEntityManager().getTransaction(); //return tx; }
	 * 
	 * public void beginTransaction() { getTransaction().begin(); }
	 * 
	 * public void commit() { getTransaction().commit(); }
	 * 
	 * public void rollback() { if(getTransaction().isActive()) {
	 * getTransaction().rollback(); } }
	 */
	@Transactional
	public void persist(Object entity) {
		getEntityManager().persist(entity);
	}

	@Transactional
	public void remove(Object entity) {
		entity = getEntityManager().merge(entity);
		getEntityManager().remove(entity);
	}

	@Transactional
	public void flush() {
		getEntityManager().flush();
	}

	@Transactional
	public void update(Object entity) {
		entity = getEntityManager().merge(entity);
		getEntityManager().persist(entity);
	}

	public <X> X returnNullIfNoResult(TypedQuery<X> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public <E extends AbstractEntity> List<Integer> detectIds(List<E> entities) {
		if (entities == null) {
			return null;
		}
		List<Integer> ret = new ArrayList<Integer>();
		for (E e : entities) {
			ret.add(e.getId());
		}
		return ret;
	}

	public SimplePager<T> paginate(int limit, final Integer page) {
		Paginator<T> p = new DbSelectPaginator<T>(this);
		p.setLimit(limit);
		p.setPage(page);
		return p.paginate();
	}
}
