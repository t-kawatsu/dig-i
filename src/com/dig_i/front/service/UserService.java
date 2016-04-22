package com.dig_i.front.service;

import java.util.List;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;

import com.dig_i.front.entity.*;
import com.dig_i.front.dao.UserDao;
import com.dig_i.front.dao.FbUserDao;
import com.dig_i.front.dao.TwUserDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.dao.UserTagInformationDao;
import com.dig_i.front.dao.UserFavoriteDao;
import com.dig_i.front.dao.UserFavoriteResourceDao;
import com.dig_i.front.util.crypt.BlowfishCrypt;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Scope("prototype")
@Service
public class UserService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
    
	private static final String KEEP_LOGIN_PARAM_NAME = "kl";
	private static final String TOKEN_PARAM_NAME = "t";
	private static final String IDENTIFIER_PARAM_NAME = "uid";
	
	private static final int keepLoginExpired = 60 * 60 * 24 * 7;

	@javax.annotation.Resource
	private UserDao userDao;
	@javax.annotation.Resource
	private FbUserDao fbUserDao;
	@javax.annotation.Resource
	private TwUserDao twUserDao;
	@javax.annotation.Resource
	private UserTagDao userTagDao;
	@javax.annotation.Resource
	private UserTagInformationDao userTagInformationDao;
	@javax.annotation.Resource
	private UserFavoriteDao userFavoriteDao;
	@javax.annotation.Resource
	private UserFavoriteResourceDao userFavoriteResourceDao;

	public User create(TmpUser tmpUser, LanguageCode languageCode,
			String userAgent, UserAccountType userAccountType) throws Exception {
		return create(tmpUser, languageCode, userAgent, userAccountType, false);
	}

	public User create(TmpUser tmpUser, LanguageCode languageCode,
			String userAgent, UserAccountType userAccountType,
			boolean isTestUser) throws Exception {
		User user = new User();

		Date defaultValue = null;
		ConvertUtils.register(new DateConverter(defaultValue), Date.class);
		BeanUtils.copyProperties(user, tmpUser);
		ConvertUtils.deregister();

		user.setLastLoginedAt(new Date());
		user.setLastMailNoticedAt(new Date());
		user.setMailNoticeFrequencyCode(2);
		user.setStatus(UserStatus.LIVE);
		user.setIsTestUser(false);
		user.setLanguageCode(new Locale(languageCode.toString()));
		user.setUseragent(userAgent);
		user.addAccountType(userAccountType);
		user = userDao.getEntityManager().merge(user);
		userDao.persist(user);
		return user;
	}

	public FbUser createFbUser(String fbId, String mailAddress,
			LanguageCode languageCode, String userAgent) throws Exception {
		TmpUser tmpUser = new TmpUser();
		tmpUser.setMailAddress(mailAddress);
		User user = create(tmpUser, languageCode, userAgent,
				UserAccountType.FACEBOOK);

		FbUser fbUser = new FbUser();
		fbUser.setId(user.getId());
		fbUser.setFbId(fbId);
		fbUser = userDao.getEntityManager().merge(fbUser);
		fbUserDao.persist(fbUser);

		fbUser.setUser(user);
		return fbUser;
	}

	public TwUser createTwUser(String twId, String mailAddress,
			LanguageCode languageCode, String userAgent) throws Exception {
		TmpUser tmpUser = new TmpUser();
		tmpUser.setMailAddress(mailAddress);
		User user = create(tmpUser, languageCode, userAgent,
				UserAccountType.TWITTER);

		TwUser twUser = new TwUser();
		twUser.setId(user.getId());
		twUser.setTwId(twId);
		twUser = userDao.getEntityManager().merge(twUser);
		twUserDao.persist(twUser);

		twUser.setUser(user);
		return twUser;
	}

	public void secession(User user, Integer reasonCode, String description) {
		user.setStatus(UserStatus.SECESSIONED);
		user = userDao.getEntityManager().merge(user);
		userDao.persist(user);

		UserSecession userSecession = new UserSecession();
		userSecession.setId(user.getId());
		userSecession.setReasonCode(reasonCode);
		userSecession.setDescription(description);
		// userSecession = userDao.getEntityManager().merge(userSecession);
		userDao.persist(userSecession);

		deleteUserRegistData(user.getId());
	}

	public void deleteUserRegistData(Integer userId) {
		// delete user tags
		List<UserTag> userTags = userTagDao.findByUserId(userId);
		for (UserTag ut : userTags) {
			userTagInformationDao.deleteByUserTagId(ut.getId());
		}
		userTagDao.deleteByUserId(userId);
		// delete user favorites
		userFavoriteDao.deleteByUserId(userId);
		// delete user favorite resources
		userFavoriteResourceDao.deleteByUserId(userId);
	}

	public User updateLoginState(User user, String userAgent) {
		user.setLastLoginedAt(new Date());
		user.setUseragent(userAgent);
		user = userDao.getEntityManager().merge(user);
		userDao.persist(user);
		return user;
	}

	public FbUser mergeFbUser(User user, String fbId) {
		FbUser fbUser = fbUserDao.findByFbIdAndState(fbId, UserStatus.LIVE);
		if (fbUser == null) {
			// create fb_user data
			fbUser = new FbUser();
			fbUser.setId(user.getId());
			fbUser.setFbId(fbId);
			fbUser = fbUserDao.getEntityManager().merge(fbUser);
			fbUserDao.persist(fbUser);

		} else {
			// already exists.
			deleteUserRegistData(fbUser.getUser().getId());

			fbUserDao
					.getEntityManager()
					.createQuery(
							"UPDATE FbUser fu set fu.id = :toUserId WHERE fu.id = :fromUserId")
					.setParameter("toUserId", user.getId())
					.setParameter("fromUserId", fbUser.getUser().getId())
					.executeUpdate();

			fbUser.getUser().setStatus(UserStatus.LIVE);
			userDao.getEntityManager().merge(fbUser.getUser());
			userDao.persist(fbUser.getUser());
		}

		user.addAccountType(UserAccountType.FACEBOOK);
		user = userDao.getEntityManager().merge(user);
		userDao.persist(user);

		fbUser.setUser(user);
		return fbUser;
	}
	
    public User login(User user, boolean keep) throws Exception {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	updateLoginState(user, request.getHeader("User-Agent"));
    	
    	if(!keep) {
    		return user;
    	}
    	
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String token = BlowfishCrypt.encrypt(RandomStringUtils.random(10));
		user.setToken(token);
		userDao.update(user);
		
		Cookie klc = new Cookie(KEEP_LOGIN_PARAM_NAME, String.valueOf(keep));
		klc.setMaxAge(keepLoginExpired);
		klc.setPath("/sp");
		response.addCookie(klc);
		 
		Cookie idc = new Cookie(IDENTIFIER_PARAM_NAME, 
			 BlowfishCrypt.encrypt(user.getId().toString()));
		idc.setMaxAge(keepLoginExpired);
		idc.setPath("/sp");
		response.addCookie(idc);
		 
		Cookie tc = new Cookie(TOKEN_PARAM_NAME, user.getToken());
		tc.setMaxAge(keepLoginExpired);
		tc.setPath("/sp");
		response.addCookie(tc);
		 
		return user;
    }
}
