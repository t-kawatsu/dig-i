package com.dig_i.front.interceptor;

public interface LoginAuthenticationAware {

	public boolean isSecured();

	public boolean getIsLogined();

	public String getLoginActionName();
}
