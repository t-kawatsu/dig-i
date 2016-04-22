package com.dig_i.sp.action.user_tag;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.TagDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.entity.User;
import com.dig_i.front.service.UserTagService;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.form.TagForm;

public class CreateAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private TagDao tagDao;
	@Resource
	private UserTagDao userTagDao;
	@Resource
	private UserTagService userTagService;
	@Resource
	private TagForm tagForm;

	private String word;
	private List<Tag> proxTags;

	@Action(value = "user-tag/create", results = {
			@Result(name = "input", location = "user-tag/create.ftl"),
			@Result(name = "confirm", location = "user-tag/create-confirm", type = "redirect"),
			@Result(name = "success", location = "user-tag/index", type = "redirect") })
	public String execute() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		if (!"POST".equals(request.getMethod())) {
			if (word != null) {
				tagForm.setName(word);
			}
			return INPUT;
		}

		User user = getCurrentUser();

		if (!tagForm.validate(this, user.getId())) {
			return INPUT;
		}

		proxTags = tagDao.findProximity(tagForm.getFilteredName(), 3);
		if (!tagForm.getName().equals(tagForm.getFilteredName())
				|| (proxTags != null && !proxTags.isEmpty())) {
			sessionManager.putNamespace("tagForm", tagForm);
			sessionManager.putNamespace("proxTags", proxTags);
			return "confirm";
		}

		userTagService.assignUserTag(user, tagForm.getFilteredName());

		return SUCCESS;
	}

	@Action(value = "user-tag/create-confirm", results = { @Result(name = "success", location = "user-tag/create-confirm.ftl") })
	@SuppressWarnings("unchecked")
	public String confirmAction() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}
		tagForm = (TagForm) sessionManager.getNamespace("tagForm");
		proxTags = (List<Tag>) sessionManager.getNamespace("proxTags");
		return SUCCESS;
	}

	@Action(value = "user-tag/create-complete", results = {
			@Result(name = "input", location = "user-tag/create", type = "redirect"),
			@Result(name = "success", location = "user-tag/index", type = "redirect") })
	public String completeAction() throws Exception {
		if (!getIsLogined()) {
			return LOGIN;
		}

		tagForm = (TagForm) sessionManager.getNamespace("tagForm");
		if (tagForm == null) {
			return INPUT;
		}
		sessionManager.removeNamespace("tagForm");
		sessionManager.removeNamespace("proxTags");

		User user = getCurrentUser();
		if (!tagForm.validate(this, user.getId())) {
			return INPUT;
		}

		userTagService.assignUserTag(user, tagForm.getFilteredName());

		return SUCCESS;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setTagForm(TagForm tagForm) {
		this.tagForm = tagForm;
	}

	public TagForm getTagForm() {
		return tagForm;
	}

	public List<Tag> getProxTags() {
		return proxTags;
	}
}
