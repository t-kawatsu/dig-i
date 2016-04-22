package com.dig_i.front.util;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.dig_i.front.dao.AbstractDao;

public class DbSelectPaginator<T> implements Paginator<T> {

	protected AbstractDao<T> dao;

	protected int limit;
	protected int page;

	public DbSelectPaginator(AbstractDao<T> dao) {
		this.dao = dao;
	}

	public int getTotal() {
		CriteriaBuilder cb = dao.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cql = cb.createQuery(Long.class);
		cql.select(cb.count(cql.from(dao.getEntityClass())));
		return ((Long) dao.getEntityManager().createQuery(cql)
				.getSingleResult()).intValue();
	}

	public List<T> getItems() {
		CriteriaBuilder cb = dao.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(dao.getEntityClass());
		Root<T> root = cq.from(dao.getEntityClass());
		cq.select(root);
		return dao.getEntityManager().createQuery(cq)
				.setFirstResult((getPage() - 1) * limit).setMaxResults(limit)
				.getResultList();
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page == null ? 1 : (int) page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public SimplePager<T> paginate() {
		int total = getTotal();
		if (total == 0) {
			return new SimplePager<T>(getLimit(), getPage(), null, total);
		}
		return new SimplePager<T>(getLimit(), getPage(), getItems(), total);
	}
}
