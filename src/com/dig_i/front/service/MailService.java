package com.dig_i.front.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dig_i.front.dao.LanguageMailDao;
import com.dig_i.front.entity.LanguageMail;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.util.SimpleMailSender;
import com.dig_i.front.util.UrlHelper;
import com.dig_i.front.entity.Inquiry;
import com.dig_i.front.entity.Request;

@Scope("prototype")
@Service
public class MailService {

	@javax.annotation.Resource
	private LanguageMailDao languageMailDao;
	@javax.annotation.Resource
	private SimpleMailSender simpleMailSender;
	private Map<String, String> defReplaces = new HashMap<String, String>();
	private UrlHelper urlHelper = new UrlHelper();

	public void sendMailByCode(final String code, final String to,
			final String from) throws Exception {
		sendMailByCode(code, to, from, null);
	}

	public void sendMailByCode(final String code, final String to,
			final String from, final Map<String, String> replaces)
			throws Exception {

		LanguageMail languageMail = languageMailDao.findByCode(code);
		if (languageMail == null) {
			throw new Exception();
		}

		defReplaces.put("top_url",
				urlHelper.buildUrl("/", null, true, false, "/sp"));
		defReplaces.put("inquiry_url",
				urlHelper.buildUrl("/inquiry", null, true, true, "/sp"));

		String subject = replaceWord(languageMail.getSubject(), defReplaces);
		String body = replaceWord(languageMail.getBody(), defReplaces);

		simpleMailSender.connect();
		simpleMailSender.send(replaceWord(subject, replaces),
				replaceWord(body, replaces), to, from);
		simpleMailSender.disconnect();
	}

	public String replaceWord(String str, final Map<String, String> replaces) {
		if (replaces == null) {
			return str;
		}
		StringBuilder b = new StringBuilder(str);
		for (Map.Entry<String, String> e : replaces.entrySet()) {
			String key = "%%" + e.getKey() + "%%";
			int i;
			while ((i = b.indexOf(key)) > -1) {
				b.replace(i, i + key.length(), e.getValue());
			}
		}
		return b.toString();
	}

	public void sendSpUrlMail(String mailAddress) throws Exception {
		sendMailByCode("F01", mailAddress, "send-site-url@" + simpleMailSender.getFromDomain());
	}
	
	public void sendInquiryToSupport(
			String supportMailAddress, Inquiry inquiry) throws Exception {
		Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("id", String.valueOf(inquiry.getId()));
		replaces.put("type", inquiry.getTypeId());
		replaces.put("userId", String.valueOf(inquiry.getUserId()));
		replaces.put("mailAddress", inquiry.getMailAddress());
		replaces.put("description", inquiry.getDescription());
		sendMailByCode("F02", supportMailAddress, "inquiry@"
				+ simpleMailSender.getFromDomain(), replaces);
	}
	
	public void sendRequestToSupport(
			String supportMailAddress, Request request) throws Exception {
		Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("id", String.valueOf(request.getId()));
		replaces.put("userId", String.valueOf(request.getUserId()));
		replaces.put("description", request.getDescription());
		sendMailByCode("F03", supportMailAddress, "request@"
				+ simpleMailSender.getFromDomain(), replaces);
	}

	public void sendCreateTmpUserComplete(String mailAddress, String token)
			throws Exception {
		Map<String, Object> urlParams = new HashMap<String, Object>();
		urlParams.put("token", token);
		Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("complete_url", urlHelper.buildUrl(
				"/user/create-complete", urlParams, true, true));
		sendMailByCode("S01", mailAddress, "tmp-user-regist-complete@"
				+ simpleMailSender.getFromDomain(), replaces);
	}

	public void sendUpdateMailAddress(String mailAddress) throws Exception {
		Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("mailAddress", mailAddress);
		replaces.put("complete_url", urlHelper.buildUrl(
				"/user/update-mail-address-complete", null, true, true));
		sendMailByCode("S02", mailAddress, "update-user-mail-address@"
				+ simpleMailSender.getFromDomain(), replaces);
	}

	public void sendPassword(String mailAddress, String password)
			throws Exception {
		Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("mailAddress", mailAddress);
		replaces.put("password", password);
		replaces.put("change_password_url",
				urlHelper.buildUrl("/user/update-password", null, true, true));
		sendMailByCode("S03", mailAddress, "send-password@" 
				+ simpleMailSender.getFromDomain(), replaces);
	}

	public void sendNewInformationNotice(String mailAddress, List<Tag> tags)
			throws Exception {
		Map<String, String> replaces = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		for (Tag tag : tags) {
			sb.append("[ ");
			sb.append(tag.getName());
			sb.append(" ] ");
			sb.append("\r\n");
		}
		replaces.put("mailAddress", mailAddress);
		replaces.put("tags", sb.toString());
		replaces.put("change_frequency_code",
				urlHelper.buildUrl("/user/update", null, true, true));
		sendMailByCode("B01", mailAddress, "added-user-tag-information@"
				+ simpleMailSender.getFromDomain(), replaces);
	}
	
}
