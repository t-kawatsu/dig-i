package com.dig_i.sp.action.fb_user;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.util.CollectionUtils;

import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserAccountType;
import com.dig_i.front.entity.UserTag;
import com.dig_i.front.service.InformationSearchService;
import com.dig_i.front.service.UserTagService;
import com.dig_i.front.service.facebook.MyDefaultFacebookClient;
import com.dig_i.front.service.facebook.MyFacebookClient;
import com.dig_i.front.util.UrlHelper;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.dto.ExceedUserTag;
import com.dig_i.sp.form.TagForm;
import com.restfb.Connection;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;

public class ImportFavoriteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static int IMPORT_INTERESTS_LIMIT = 10;
	
	@Resource
	private InformationSearchService informationSearchService;
	@Resource
	private UserTagService userTagService;
	@Resource
	private UserTagDao userTagDao;
	@Resource
	private TagForm tagForm;
	
	private String loginUrl;

	@Action(value = "fb-user/import-favorite", results = { @Result(name = "success", location = "${loginUrl}", type = "redirect") })
	public String execute() throws Exception {
		User user = getCurrentUser();
		if (!user.hasAccountType(UserAccountType.FACEBOOK)) {
			throw new Exception();
		}

		MyFacebookClient facebookClient = new MyDefaultFacebookClient();

		UrlHelper urlHelper = new UrlHelper();
		loginUrl = facebookClient.buildLoginUrl(
				getText("app.facebook.appId"),
				urlHelper.buildUrl("/fb-user/import-favorite-complete", null, true, true),
				"touch", 
				new String[] { "user_interests", "user_likes" });

		return SUCCESS;
	}

	@Action(value = "fb-user/import-favorite-complete", results = { 
			@Result(name = "success", location = "user-tag/index", type = "redirect"),
			@Result(name = "exceed", location = "user-tag/exceed", type = "redirect") 
	})
	public String CompleteAction() throws Exception {
		sessionManager.removeNamespace("exceedTags");
		
		String code = request.getParameter("code");
		if (code == null) {
			throw new InvalidParameterException();
		}
		
		User user = getCurrentUser();
		if (!user.hasAccountType(UserAccountType.FACEBOOK)) {
			throw new Exception();
		}
		
		List<String> names = new ArrayList<String>();
		try {
			MyFacebookClient facebookClient = new MyDefaultFacebookClient();

			UrlHelper urlHelper = new UrlHelper();
			MyFacebookClient.AccessToken accessToken = facebookClient
					.obtainAccessToken(
							getText("app.facebook.appId"),
							getText("app.facebook.secret"), 
							code, 
							urlHelper.buildUrl("/fb-user/import-favorite-complete", null, true, true));

			facebookClient = new MyDefaultFacebookClient(
					accessToken.getAccessToken());
			
			int limit = 15;
			
			Connection<JsonObject> likes = facebookClient.fetchConnection(
					"me/likes",
					JsonObject.class);
			
			if(likes != null && likes.getData() != null) {
				for(JsonObject row : likes.getData()) {
					names.add(row.getString("name"));
					if(limit <= names.size() ) {
						break;
					}
				}
			}

			Connection<JsonObject> interests = facebookClient.fetchConnection(
					"me/interests",
					JsonObject.class);
			
			if(interests != null && interests.getData() != null) {
				for(JsonObject row : interests.getData()) {
					names.add(row.getString("name"));
					if(limit <= names.size() ) {
						break;
					}
				}
			}

		} catch (FacebookException fe) {
			throw new Exception(fe.getMessage());
		} catch (Exception e) {
			throw e;
		}
		
		if(CollectionUtils.isEmpty(names)) {
			return SUCCESS;
		}
		
		List<String> detectedNames = new ArrayList<String>();
		for(String row : names) {
			String word = informationSearchService.detectSearchWord(row);
			tagForm.setName(word);
			if(!tagForm.validateWord(this, user.getId())) {
				continue;
			}
			if(userTagService.isUserRegistedWord(user, word)) {
				continue;
			}
			detectedNames.add(word);
		}
		
		List<UserTag> userTags = userTagDao.findByUserId(user.getId());
		if(detectedNames.size() + (userTags == null ? 0 : userTags.size())
				<= Integer.valueOf(getText("app.tag.regist.limit"))) {
			for(String row : detectedNames) {
				userTagService.assignUserTag(user, row);
			}
		} else {
			List<ExceedUserTag> words = new ArrayList<ExceedUserTag>();
			for(String row : detectedNames) {
				ExceedUserTag tag = new ExceedUserTag();
				tag.setName(row);
				words.add(tag);
			}
			if(!CollectionUtils.isEmpty(userTags)) {
				for(UserTag row : userTags) {
					ExceedUserTag tag = new ExceedUserTag();
					tag.setUserTagId(row.getId());
					tag.setName(row.getTag().getName());
					words.add(tag);
				}
			}
			sessionManager.putNamespace("exceedTags", words);
			return "exceed";
		}
		
		return SUCCESS;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

}
