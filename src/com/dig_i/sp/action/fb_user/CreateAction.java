package com.dig_i.sp.action.fb_user;

import java.security.InvalidParameterException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.FbUserDao;
import com.dig_i.front.dao.UserDao;
import com.dig_i.front.entity.FbUser;
import com.dig_i.front.entity.LanguageCode;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.service.UserService;
import com.dig_i.front.service.facebook.MyDefaultFacebookClient;
import com.dig_i.front.service.facebook.MyFacebookClient;
import com.dig_i.front.util.UrlHelper;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.form.FbUserForm;
import com.restfb.exception.FacebookException;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserDao userDao;
	@Resource
	private FbUserDao fbUserDao;
	@Resource
	private UserService userService;
	@Resource
	private FbUserForm fbUserForm;

	private String loginUrl;

	@Action(value = "fb-user/create", results = {
			@Result(name = "input", location = "fb-user/create.ftl"),
			@Result(name = "success", location = "${loginUrl}", type = "redirect") })
	public String execute() throws Exception {
		if (!"POST".equals(request.getMethod())) {
			return INPUT;
		}
		if (!fbUserForm.validate(this)) {
			return INPUT;
		}

		MyFacebookClient facebookClient = new MyDefaultFacebookClient();

		UrlHelper urlHelper = new UrlHelper();
		loginUrl = facebookClient
				.buildLoginUrl(getText("app.facebook.appId"),
						urlHelper.buildUrl("/fb-user/create-complete", null,
								true, true), "touch", new String[] { "email" });

		return SUCCESS;
	}

	@Action(value = "fb-user/create-complete", results = {
			@Result(name = "input", location = "fb-user/create.ftl"),
			@Result(name = "exists", location = "fb-user/create-already-exists.ftl"),
			@Result(name = "success", location = "user-tag/index", type="redirect") })
	// https://developers.facebook.com/docs/reference/dialogs/oauth/
	public String completeAction() throws Exception {

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
									.buildUrl("/fb-user/create-complete", null,
											true, true));

			facebookClient = new MyDefaultFacebookClient(
					accessToken.getAccessToken());
			facebookUser = facebookClient.fetchObject("me",
					com.restfb.types.User.class);

			if (StringUtils.isEmpty(facebookUser.getEmail())
					|| facebookUser.getId() == null) {
				return INPUT;
			}

		} catch (FacebookException fe) {
			logger.info("{}", fe);
			return INPUT;
		} catch (Exception e) {
			throw e;
		}

		FbUser fbUser = new FbUser();
		fbUser = fbUserDao.findByFbIdAndState(facebookUser.getId(),
				UserStatus.LIVE);
		if (fbUser != null) {
			// already exists.
			return "exists";
		}

		fbUser = userService.createFbUser(facebookUser.getId(),
				facebookUser.getEmail(), LanguageCode.JA,
				request.getHeader("User-Agent"));

		setCurrentUser(fbUser.getUser());

		return SUCCESS;
	}

	public void setFbUserForm(FbUserForm fbUserForm) {
		this.fbUserForm = fbUserForm;
	}

	public FbUserForm getFbUserForm() {
		return fbUserForm;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

}
