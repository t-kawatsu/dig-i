package com.dig_i.sp.action.user_tag;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserTag;
import com.dig_i.front.service.UserTagService;
import com.dig_i.sp.action.AbstractAction;
import com.dig_i.sp.dto.ExceedUserTag;

public class ExceedAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserTagService userTagService;
	@Resource
	private UserTagDao userTagDao;
	
	List<ExceedUserTag> words;
	List<Integer> checks = new ArrayList<Integer>();
	
	@SuppressWarnings("unchecked")
	@Action(value = "user-tag/exceed", results = { 
			@Result(name = "input", location = "user-tag/exceed.ftl"),
			@Result(name = "unexceed", location = "user-tag/index", type="redirect"),
			@Result(name = "success", location = "user-tag/exceed-complete.ftl")
	})
	public String execute() throws Exception {
		words = (List<ExceedUserTag>)sessionManager.getNamespace("exceedTags");
		if(words == null) {
			return "unexceed";
		}
		
		if (!"POST".equals(this.request.getMethod())) {
			for(int i=0;i<words.size();i++) {
				if(words.get(i).getUserTagId() != null) {
					checks.add(i);
				}
			}
			return INPUT;
		}
		
		sessionManager.removeNamespace("exceedTags");
		
		if(Integer.parseInt(getText("app.tag.regist.limit")) < checks.size()) {
			return ERROR;
		}
		
		User user = getCurrentUser();
		
		// 外された登録済みを削除
		List<UserTag> userTags = userTagDao.findByUserId(user.getId());
		ut:for(UserTag row : userTags) {
			for(Integer c : checks) {
				ExceedUserTag w = words.get(c);
				if(w.getUserTagId() != null 
						&& w.getUserTagId().equals(row.getId())) {
					continue ut;
				}
			}
			userTagService.deleteUserTag(row);
		}
		
		// 登録
		for(Integer c : checks) {
			ExceedUserTag row = words.get(c);
			if(row.getUserTagId() != null) {
				// 登録済み
				continue;
			}
			userTagService.assignUserTag(user, row.getName());
		}
		
		return SUCCESS;
	}
	
	public List<ExceedUserTag> getWords() {
		return words;
	}
	
	public List<Integer> getCheckes() {
		return checks;
	}
	
	public void setCheckes(List<Integer> checkes) {
		this.checks = checkes;
	}
}
