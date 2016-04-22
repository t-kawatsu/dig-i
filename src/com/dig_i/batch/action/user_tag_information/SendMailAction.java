package com.dig_i.batch.action.user_tag_information;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;

import com.dig_i.batch.action.AbstractAction;
import com.dig_i.front.dao.UserDao;
import com.dig_i.front.dao.UserTagDao;
import com.dig_i.front.entity.Tag;
import com.dig_i.front.entity.User;
import com.dig_i.front.entity.UserStatus;
import com.dig_i.front.entity.UserTag;
import com.dig_i.front.service.MailService;
import com.dig_i.front.util.SimplePager;

public class SendMailAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PER_DETECT_TAG_NUM = 100;

	@javax.annotation.Resource
	private UserDao userDao;
	@javax.annotation.Resource
	private UserTagDao userTagDao;
	@javax.annotation.Resource
	private MailService mailService;

	@Action(value = "user-tag-information/send-mail")
	public String execute() throws Exception {
		SimplePager<User> pager;
		Integer page = 0;
		while (true) {
			page++;
			pager = userDao.paginate(PER_DETECT_TAG_NUM, page);
			List<User> users = pager.getItems();
			if (users == null || users.isEmpty()) {
				break;
			}

			for (User row : users) {
				if (row.getStatus() != UserStatus.LIVE) {
					continue;
				}
				try {
					Date rDate = new Date();
					Integer frequencyCode = row.getMailNoticeFrequencyCode();
					switch (frequencyCode.intValue()) {
					case 1: // 1日1回
						rDate = DateUtils.addDays(rDate, -1);
						break;
					case 2: // 3日1回
						rDate = DateUtils.addDays(rDate, -3);
						break;
					case 3: // 1週1回
						rDate = DateUtils.addDays(rDate, -7);
						break;
					default:
						continue;
					}

					// 前回メール送信時間からの比較
					if (0 <= DateUtils.truncatedCompareTo(
							row.getLastMailNoticedAt(), rDate, Calendar.DATE)) {
						// frequencyの日数経っていない
						continue;
					}

					// 前回ログイン時間からの比較
					if (0 <= DateUtils.truncatedCompareTo(
							row.getLastLoginedAt(), rDate, Calendar.DATE)) {
						// frequencyの日数経っていない
						continue;
					}

					// どれだけ情報が増えたか
					List<UserTag> userTags = userTagDao.findByUserId(row
							.getId());
					if (userTags == null || userTags.isEmpty()) {
						// タグ登録がない
						continue;
					}

					List<Tag> updatedTags = new ArrayList<Tag>();
					for (UserTag userTag : userTags) {
						if (0 <= DateUtils.truncatedCompareTo(
								row.getLastMailNoticedAt(),
								userTag.getInformationUpdatedAt(),
								Calendar.DATE)) {
							continue;
						}
						// 前回メール送信後に新しい情報が更新された
						updatedTags.add(userTag.getTag());
					}

					if (updatedTags.isEmpty()) {
						// 更新タグがない
						continue;
					}

					// 更新分のお知らせメール
					if (row.getMailAddress() != null) {
						mailService.sendNewInformationNotice(
								row.getMailAddress(), updatedTags);
					}

					// 送信日付を保存
					row.setLastMailNoticedAt(new Date());
					userDao.persist(row);

				} catch (Exception e) {
					logger.warn("{}", e);
					continue;
				}
			}
		}
		return NONE;
	}

}
