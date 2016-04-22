package com.dig_i.front.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dig_i.front.entity.*;
import com.dig_i.front.dao.TagDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.dao.TagInformationDao;
import com.dig_i.front.dao.UserTagInformationDao;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Scope("prototype")
@Service
public class UserTagService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static Integer TAG_REGIST_ASSIGN_INFORMATIONS_NUM = 30;

	@javax.annotation.Resource
	private TagDao tagDao;
	@javax.annotation.Resource
	private UserTagDao userTagDao;
	@javax.annotation.Resource
	private TagInformationDao tagInformationDao;
	@javax.annotation.Resource
	private UserTagInformationDao userTagInformationDao;

	@Transactional
	public void assignUserTag(User user, String tagName) {
		Tag tag = tagDao.findByName(tagName);
		if (tag == null) {
			// tag登録
			tag = new Tag();
			tag.setName(tagName);
			tag = tagDao.getEntityManager().merge(tag);
			tagDao.persist(tag);
		}

		// user_tag登録
		UserTag userTag = new UserTag();
		userTag.setUserId(user.getId());
		userTag.setTagId(tag.getId());
		userTag.setInformationUpdatedAt(new Date());
		userTag = userTagDao.getEntityManager().merge(userTag);
		userTagDao.persist(userTag);

		if (tag != null) {
			// user_tag_information登録
			List<TagInformation> tagInformations = tagInformationDao
					.findByTagId(tag.getId(),
							TAG_REGIST_ASSIGN_INFORMATIONS_NUM);
			for (TagInformation row : tagInformations) {
				UserTagInformation userTagInformation = new UserTagInformation();
				UserTagInformationId userTagInformationId = new UserTagInformationId();
				userTagInformationId.setUserTagId(userTag.getId());
				userTagInformationId.setInformationId(row.getId()
						.getInformationId());
				userTagInformation.setId(userTagInformationId);
				userTagInformation = userTagInformationDao.getEntityManager()
						.merge(userTagInformation);
				userTagInformationDao.persist(userTagInformation);
			}
		}
	}

	@Transactional
	public void deleteUserTag(UserTag userTag) {
		userTagDao.remove(userTag);
		userTagInformationDao.deleteByUserTagId(userTag.getId());
	}
	
	public boolean isUserRegistedWord(User user, String word) {
		Tag tag = tagDao.findByName(word);
		if(tag == null) {
			return false;
		}
		return userTagDao.isUserRegisteredTag(user.getId(), tag.getId());
	}
	
	public List<String> extractWords(List<UserTag> userTags) {
		if(CollectionUtils.isEmpty(userTags)) {
			return null;
		}
		List<String> words = new ArrayList<String>();
		for(UserTag row : userTags) {
			words.add(row.getTag().getName());
		}
		return words;
	}
}
