package com.dig_i.front.dao;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.dig_i.front.entity.ImageSize;
import com.dig_i.front.entity.Information;
import com.dig_i.front.entity.InformationImageSize;
import com.dig_i.front.entity.ResourceType;
import com.dig_i.front.entity.UserTag;

@Repository
public class InformationDao extends AbstractDao<Information> implements
		ImageStore {

	public Information findById(final Integer id) {
		if (id == null) {
			return null;
		}
		return returnNullIfNoResult(getEntityManager()
				.createQuery(
						"SELECT i FROM Information i JOIN fetch i.resource r WHERE i.id = :id",
						entityClass).setParameter("id", id));
	}

	public int countByLink(final URL link) {
		if (link == null) {
			return 0;
		}
		return ((Long) getEntityManager()
				.createQuery(
						"SELECT count(*) FROM Information i WHERE i.link = :link")
				.setParameter("link", link).getSingleResult()).intValue();
	}

	public List<Information> findNewInformationsByTagIds(
			final List<Integer> tagIds, int limit) {
		if (tagIds == null || tagIds.size() <= 0) {
			return null;
		}

		return getEntityManager()
				.createQuery(
						"SELECT i FROM TagInformation ti JOIN ti.information i JOIN fetch i.resource WHERE ti.id.tagId IN (:ids) ORDER BY ti.id.informationId DESC",
						Information.class).setParameter("ids", tagIds)
				.setMaxResults(limit).getResultList();
	}

	public List<Information> findByTagId(final Integer tagId, int limit) {
		if (tagId == null) {
			return null;
		}

		return getEntityManager()
				.createQuery(
						"SELECT i FROM TagInformation ti JOIN ti.information i JOIN fetch i.resource WHERE ti.id.tagId = (:tagId) ORDER BY ti.id.informationId DESC",
						Information.class).setParameter("tagId", tagId)
				.setMaxResults(limit).getResultList();
	}

	public List<Information> findByTagIdAndResourceType(final Integer tagId,
			ResourceType resourceType, int limit, List<Integer> excludes) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i FROM TagInformation ti JOIN ti.information i JOIN fetch i.resource WHERE ti.id.tagId = (:tagId) ");
		if (excludes != null && !excludes.isEmpty()) {
			sb.append("AND ti.id.informationId < :excludes ");
		}
		if (resourceType != null) {
			sb.append("AND i.resource.type in (:resourceType) ");
		}
		sb.append("ORDER BY ti.id.informationId DESC");

		TypedQuery<Information> tq = getEntityManager()
				.createQuery(sb.toString(), Information.class)
				.setParameter("tagId", tagId).setMaxResults(limit);

		if (excludes != null && !excludes.isEmpty()) {
			tq.setParameter("excludes", Collections.min(excludes));
		}
		if (resourceType != null) {
			List<ResourceType> resourceTypes = new ArrayList<ResourceType>();
			if (resourceType == ResourceType.RSS) {
				resourceTypes.add(ResourceType.RSS1_0);
				resourceTypes.add(ResourceType.RSS2_0);
			} else {
				resourceTypes.add(resourceType);
			}
			tq.setParameter("resourceType", resourceTypes);
		}

		return tq.getResultList();
	}

	public List<Information> findByUserTagIdAndResourceType(
			final Integer userTagId, ResourceType resourceType, int limit,
			List<Integer> excludes) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i FROM UserTagInformation uti JOIN uti.information i JOIN fetch i.resource WHERE uti.id.userTagId = (:userTagId) ");
		if (excludes != null && !excludes.isEmpty()) {
			sb.append("AND uti.id.informationId < :excludes ");
		}
		if (resourceType != null) {
			sb.append("AND i.resource.type in (:resourceType) ");
		}
		sb.append("ORDER BY uti.id.informationId DESC");

		TypedQuery<Information> tq = getEntityManager()
				.createQuery(sb.toString(), Information.class)
				.setParameter("userTagId", userTagId).setMaxResults(limit);

		if (excludes != null && !excludes.isEmpty()) {
			tq.setParameter("excludes", Collections.min(excludes));
		}
		if (resourceType != null) {
			List<ResourceType> resourceTypes = new ArrayList<ResourceType>();
			if (resourceType == ResourceType.RSS) {
				resourceTypes.add(ResourceType.RSS1_0);
				resourceTypes.add(ResourceType.RSS2_0);
			} else {
				resourceTypes.add(resourceType);
			}
			tq.setParameter("resourceType", resourceTypes);
		}

		return tq.getResultList();
	}

	public List<Information> findNewInformationsByUserTags(
			final List<UserTag> userTags, int limit) {
		if (userTags == null || userTags.size() <= 0) {
			return null;
		}
		List<Integer> tagIds = detectIds(userTags);
		return findNewInformationsByTagIds(tagIds, limit);
	}

	public List<Information> findByResourceIdOrderByNew(
			final Integer resourceId, int limit) {
		return findByResourceIdOrderByNew(resourceId, limit, null);
	}

	public List<Information> findByResourceIdOrderByNew(
			final Integer resourceId, int limit, List<Integer> excludes) {
		if (resourceId == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT i FROM Information i JOIN fetch i.resource r WHERE r.id = (:resourceId) ");
		if (excludes != null && !excludes.isEmpty()) {
			sb.append("AND i.id < :excludes ");
		}
		sb.append("ORDER BY i.publishedAt DESC");

		TypedQuery<Information> tq = getEntityManager()
				.createQuery(sb.toString(), Information.class)
				.setParameter("resourceId", resourceId).setMaxResults(limit);

		if (excludes != null && !excludes.isEmpty()) {
			tq.setParameter("excludes", Collections.min(excludes));
		}

		return tq.getResultList();
	}

	@Override
	public ImageSize[] getImageSizes() {
		return InformationImageSize.values();
	}

	@Override
	public String getTmpImageDir(Integer id) {
		return "/tmp-informations/" + id;
	}

	@Override
	public String getTmpImageFile(String fileId, ImageSize is, Integer id) {
		return getTmpImageDir(id) + "/" + fileId + "-" + is.getName() + ".jpg";
	}

	@Override
	public String getImageDir(Integer id) {
		int uid1000 = (int) Math.floor(id / 1000) * 1000;
		int uid100 = (int) Math.floor((id - uid1000) / 100) * 100 + uid1000;

		return "/informations/" + uid1000 + "/" + uid100 + "/" + id;
	}

	@Override
	public String getImageFile(Integer id, ImageSize is) {
		return getImageDir(id) + "/" + is.getName() + ".jpg";
	}
}
