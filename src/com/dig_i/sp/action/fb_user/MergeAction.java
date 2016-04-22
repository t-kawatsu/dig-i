package com.dig_i.sp.action.fb_user;

import java.security.InvalidParameterException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.FbUser;
import com.dig_i.front.entity.UserAccountType;
import com.dig_i.front.service.UserService;
import com.dig_i.front.service.facebook.MyDefaultFacebookClient;
import com.dig_i.front.service.facebook.MyFacebookClient;
import com.dig_i.front.util.UrlHelper;
import com.dig_i.sp.action.AbstractAction;
import com.restfb.exception.FacebookException;

public class MergeAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserDao userDao;
	@Resource
	private UserService userService;

	private String loginUrl;

	@Action(value = "fb-user/merge", results = { @Result(name = "success", location = "${loginUrl}", type = "redirect") })
	public String execute() throws Exception {
		com.dig_i.front.entity.User user = getCurrentUser();
		if (user == null) {
			throw new Exception();
		}
		if (user.hasAccountType(UserAccountType.FACEBOOK)) {
			throw new Exception();
		}

		MyFacebookClient facebookClient = new MyDefaultFacebookClient();

		UrlHelper urlHelper = new UrlHelper();
		loginUrl = facebookClient
				.buildLoginUrl(getText("app.facebook.appId"), urlHelper
						.buildUrl("/fb-user/merge-complete", null, true, true),
						"touch", new String[] {});

		return SUCCESS;
	}

	@Action(value = "fb-user/merge-complete", results = { @Result(name = "success", location = "user/index", type = "redirect") })
	public String completeAction() throws Exception {
		com.dig_i.front.entity.User user = getCurrentUser();
		if (user == null) {
			throw new Exception();
		}
		if (user.hasAccountType(UserAccountType.FACEBOOK)) {
			throw new Exception();
		}

		String code = request.getParameter("code");
		if (code == null) {
			throw new InvalidParameterException();
		}

		com.restfb.types.User facebookUser;
		try {
			MyFacebookClient facebookClient = new MyDefaultFacebookClient();

			UrlHelper urlHelper = new UrlHelper();
			MyFacebookClient.AccessToken accessToken = facebookClient
					.obtainAccessToken(getText("app.facebook.appId"),
							getText("app.facebook.secret"), code, urlHelper
									.buildUrl("/fb-user/merge-complete", null,
											true, true));

			facebookClient = new MyDefaultFacebookClient(
					accessToken.getAccessToken());
			facebookUser = facebookClient.fetchObject("me",
					com.restfb.types.User.class);

			if (StringUtils.isEmpty(facebookUser.getEmail())
					|| facebookUser.getId() == null) {
				return SUCCESS;
			}

		} catch (FacebookException fe) {
			logger.info("{}", fe);
			return SUCCESS;
		} catch (Exception e) {
			throw e;
		}

		FbUser fbUser = userService.mergeFbUser(user, facebookUser.getId());

		setCurrentUser(fbUser.getUser());

		return SUCCESS;
	}

	public String getLoginUrl() {
		return loginUrl;
	}
}
