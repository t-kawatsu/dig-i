package com.dig_i.front.util;

import java.util.List;

import com.dig_i.front.dao.AbstractDao;

public class DbSelectMorePaginator<T> extends DbSelectPaginator<T> implements
		MorePaginator<T> {

	private SessionManager sessionManager;
	private Integer id;

	public DbSelectMorePaginator(SessionManager sessionManager,
			AbstractDao<T> dao, Integer id) {
		super(dao);
		this.sessionManager = sessionManager;
		this.id = id;
	}

	private String detectPageKey() {
		return "page_" + dao.getEntityClass().getName() + "_" + id;
	}

	public DbSelectMorePaginator<T> clearPage() {
		sessionManager.removeNamespace(detectPageKey());
		return this;
	}

	public Integer getPage() {
		Integer page = (Integer) sessionManager.getNamespace(detectPageKey());
		return page == null ? 1 : (int) page;
	}

	public List<T> getItems() {
		List<T> items = super.getItems();
		sessionManager.putNamespace(detectPageKey(), getPage() + 1);
		return items;
	}
}
