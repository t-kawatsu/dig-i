package com.dig_i.front.service.facebook;

import java.util.List;
import com.restfb.FacebookClient;

public interface MyFacebookClient extends FacebookClient {

	public static final String FACEBOOK_ENDPOINT_URL = "https://www.facebook.com";

	public String buildLoginUrl(String appId, String redirectUri,
			String display, String[] scopes);

	public AccessToken obtainAccessToken(String appId, String appSecret,
			String code, String redirectUrl);

	public <T> List<T> search(String type, Class<T> clazz, String query,
			String fields, int limit);
}
