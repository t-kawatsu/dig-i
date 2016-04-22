package com.dig_i.sp.action.tw_user;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.dig_i.front.dao.TwUserDao;
import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.LanguageCode;
import com.dig_i.front.entity.TwUser;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.service.UserService;
import com.dig_i.front.service.twitter.MyTwitterClient;
import com.dig_i.front.util.UrlHelper;
import com.dig_i.sp.action.AbstractAction;

public class LoginAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserDao userDao;
	@Resource
	private TwUserDao twUserDao;
	@Resource
	private UserService userService;

	private String loginUrl;

	@Action(value = "tw-user/login", results = { @Result(name = "success", location = "${loginUrl}", type = "redirect") })
	public String execute() throws Exception {
		Twitter twitter = MyTwitterClient.factoryTwitter(
				getText("app.twitter.key"), getText("app.twitter.secret"));
		UrlHelper urlHelper = new UrlHelper();
		RequestToken requestToken = twitter.getOAuthRequestToken(urlHelper
				.buildUrl("/tw-user/login-complete", null, true, true));
		sessionManager.putNamespace("twRequestToken", requestToken);
		loginUrl = requestToken.getAuthenticationURL();
		return SUCCESS;
	}

	@Action(value = "tw-user/login-complete", results = {
			@Result(name = "input", location = "user/login.ftl"),
			@Result(name = "success", location = "user-tag/index", type = "redirect") })
	public String completeAction() throws Exception {
		String oauthToken = request.getParameter("oauth_token");
		String oauthVerifier = request.getParameter("oauth_verifier");
		if (StringUtils.isEmpty(oauthToken)
				|| StringUtils.isEmpty(oauthVerifier)) {
			// denied
			return LOGIN;
		}

		Twitter twitter = MyTwitterClient.factoryTwitter(
				getText("app.twitter.key"), getText("app.twitter.secret"));
		AccessToken accessToken = twitter.getOAuthAccessToken(
				(RequestToken) sessionManager.getNamespace("twRequestToken"),
				oauthVerifier);

		sessionManager.removeNamespace("twRequestToken");
		sessionManager.putNamespace("twToken", accessToken.getToken());
		sessionManager.putNamespace("twTokenSecret",
				accessToken.getTokenSecret());

		String twId = String.valueOf(twitter.verifyCredentials().getId());

		TwUser twUser = twUserDao.findByTwIdAndState(twId, UserStatus.LIVE);
		User user = null;
		if (twUser == null) {
			// create twUser if not exists.
			twUser = userService.createTwUser(twId, null, LanguageCode.JA,
					request.getHeader("User-Agent"));
			user = twUser.getUser();
			// return "exists";
		} else {
			user = userService.updateLoginState(twUser.getUser(),
					request.getHeader("User-Agent"));
		}
		
		userService.login(user, true);

		setCurrentUser(user);

		return SUCCESS;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

}
