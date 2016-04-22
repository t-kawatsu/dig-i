package com.dig_i.front.interceptor;

public interface LoginAuthorizationAware<T> {

	public boolean getIsLogined();

	public T getCurrentUser();

	public void removeCurrentUser();

	public void setCurrentUser(T user);
}
