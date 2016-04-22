package com.dig_i.front.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dig_i.front.dao.InformationDao;
import com.dig_i.front.dao.ResourceDao;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.ResourceStatus;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.XmlReaderException;

public abstract class TraversalResource {
	
	protected final Logger logger = LoggerFactory.getLogger(TraversalResource.class);
	
	public static final int CONNECT_STOP_EXCEPTION_LIMIT = 10;
	
	@javax.annotation.Resource
	protected ResourceDao resourceDao;
	@javax.annotation.Resource
	protected InformationDao informationDao;
	@javax.annotation.Resource
	private InformationService informationService;
	
	public void update(Resource res) {
		if (res.getStatus() != ResourceStatus.LIVE) {
			return;
		}
		
		Resource resource = null;
		try {
			try {
				resource = traversal(res);
			} catch (IOException e) {
				// io error
				throw e;
			} catch (FeedException e) {
				// parse error
				throw e;
			}
			
			List<Information> informations = extractNewInformations(res, resource);
			
			informationService.bulkInsert(informations);
			
		} catch (Exception e) {
			logger.error("{}", e.getMessage());
			
			res.setCauseConnectCnt(res.getCauseConnectCnt() + 1);
			if (CONNECT_STOP_EXCEPTION_LIMIT <= res.getCauseConnectCnt()) {
				res.setStatus(ResourceStatus.STOP);
			}
			resourceDao.update(res);
			return;
		}

		// update resource
		res.setCauseConnectCnt(0);
		resourceDao.update(resource);
	}
	
	public abstract Resource traversal(Resource resource)
			throws IOException, XmlReaderException, FeedException, Exception;

	public abstract List<Information> extractNewInformations(Resource oldOne,
			Resource newOne);

}
