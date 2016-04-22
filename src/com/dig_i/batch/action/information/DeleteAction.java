package com.dig_i.batch.action.information;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.batch.action.AbstractAction;
import com.dig_i.front.service.InformationService;
import com.dig_i.front.service.UserTagInformationService;

public class DeleteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DELETE_INFORMATION_THROW_DAYS = 40;

	@javax.annotation.Resource
	private InformationService informationService;
	@javax.annotation.Resource
	private UserTagInformationService userTagInformationService;

	@Action(value = "information/delete")
	public String execute() throws Exception {

		// delete old information
		informationService.deleteOldInformations(DELETE_INFORMATION_THROW_DAYS);

		// clear tag information
		userTagInformationService.clearnTagInformation();

		return NONE;
	}

}
