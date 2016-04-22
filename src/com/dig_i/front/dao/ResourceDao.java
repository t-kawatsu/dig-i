package com.dig_i.front.dao;

import java.util.EnumSet;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dig_i.front.entity.ImageSize;
import com.dig_i.front.entity.Resource;
import com.dig_i.front.entity.ResourceImageSize;
import com.dig_i.front.entity.ResourceType;
import com.dig_i.front.util.SimplePager;

@Repository
public class ResourceDao extends AbstractDao<Resource> implements ImageStore {

	public SimplePager<Resource> paginateByType(int limit, final Integer page,
			EnumSet<ResourceType> types) {
		int _page = page == null ? 1 : (int) page;
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Long> cql = cb.createQuery(Long.class);
		Root<Resource> rootl = cql.from(Resource.class);
		cql.select(cb.count(rootl)).where(rootl.get("type").in(types));
		int total = ((Long) getEntityManager().createQuery(cql)
				.getSingleResult()).intValue();
		if (total == 0) {
			return new SimplePager<Resource>(limit, _page, null, total);
		}
		CriteriaQuery<Resource> cq = cb.createQuery(Resource.class);
		Root<Resource> root = cq.from(Resource.class);
		cq.select(root).where(root.get("type").in(types));
		List<Resource> rows = getEntityManager().createQuery(cq)
				.setFirstResult((_page - 1) * limit).setMaxResults(limit)
				.getResultList();
		return new SimplePager<Resource>(limit, _page, rows, total);
	}

	public List<Resource> findByType(ResourceType type) {
		return getEntityManager()
				.createQuery("SELECT r FROM Resource r WHERE r.type = :type",
						Resource.class).setParameter("type", type)
				// .setMaxResults(limit)
				.getResultList();
	}

	public List<Resource> findByTypes(EnumSet<ResourceType> types) {
		return getEntityManager()
				.createQuery(
						"SELECT r FROM Resource r WHERE r.type in (:types)",
						Resource.class).setParameter("types", types)
				// .setMaxResults(limit)
				.getResultList();
	}

	@Override
	public ImageSize[] getImageSizes() {
		return ResourceImageSize.values();
	}

	@Override
	public String getTmpImageDir(Integer id) {
		return "/tmp-resources/" + id;
	}

	@Override
	public String getTmpImageFile(String fileId, ImageSize is, Integer id) {
		return getTmpImageDir(id) + "/" + fileId + "-" + is.getName() + ".jpg";
	}

	@Override
	public String getImageDir(Integer id) {
		int uid1000 = (int) Math.floor(id / 1000) * 1000;
		int uid100 = (int) Math.floor((id - uid1000) / 100) * 100 + uid1000;

		return "/resources/" + uid1000 + "/" + uid100 + "/" + id;
	}

	@Override
	public String getImageFile(Integer id, ImageSize is) {
		return getImageDir(id) + "/" + is.getName() + ".jpg";
	}
}
