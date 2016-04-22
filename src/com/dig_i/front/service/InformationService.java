package com.dig_i.front.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.entity.Information;

@Scope("prototype")
@Service
public class InformationService {

	protected static final Logger logger = LoggerFactory.getLogger(InformationService.class);

	@javax.annotation.Resource
	protected InformationDao informationDao;
	@javax.annotation.Resource
	protected InformationSearchService searchService;
	@javax.annotation.Resource
	protected ImageService imageService;

	@Transactional
	public void bulkInsert(List<Information> informations) {
		if (informations == null || informations.isEmpty()) {
			return;
		}

		EntityManager em = informationDao.getEntityManager();
		List<Information> ret = new ArrayList<Information>();
		for (Information row : informations) {
		  try {
			// TODO bulk insert
			row = em.merge(row);
			// save information image.
			if (row.getImageUrl() != null) {
				try {
					imageService.storeImageFromUrl(informationDao,
							row.getImageUrl(), row.getId());
				} catch (Exception e) {
					logger.error("{}", e.getMessage());
					row.setImageUrl(null);
					em.persist(row);
				}
			}
			em.persist(row);
			ret.add(row);
		  } catch (Exception e) {
			logger.error("{}", e.getMessage());
		  }
		}
		  
		em.flush();
		searchService.bulkInsert(ret);
	}

	@Transactional
	public void deleteOldInformations(int deleteInformationThrowDays) {
		Date borderDate = DateUtils.truncate(
				DateUtils.addDays(new Date(), -1 * deleteInformationThrowDays),
				Calendar.DATE);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// store old informations
		StringBuffer sb = new StringBuffer();
		sb.append("REPLACE INTO old_informations SELECT i.* FROM informations i INNER JOIN resources r ON (i.resource_id = r.id) ");
		sb.append("WHERE i.created_at <= '" + sdf.format(borderDate)
				+ "' and r.type in ('RSS1_0','RSS2_0');");

		informationDao.getEntityManager().createNativeQuery(sb.toString())
				.executeUpdate();

		// delete informations
		informationDao
				.getEntityManager()
				.createNativeQuery(
						"DELETE FROM informations WHERE id in (SELECT id FROM old_informations);")
				.executeUpdate();

		// delete information_searches
		informationDao
				.getEntityManager()
				.createNativeQuery(
						"DELETE FROM information_searches WHERE information_id in (SELECT id FROM old_informations);")
				.executeUpdate();
	}
}
