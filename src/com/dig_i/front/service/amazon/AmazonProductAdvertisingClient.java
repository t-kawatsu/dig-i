package com.dig_i.front.service.amazon;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.dig_i.front.entity.Information;

public class AmazonProductAdvertisingClient {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String accessKeyId;

	private String secretKey;

	private CountryCode countryCode;

	private String associateTag;

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 */
	public enum CountryCode {
		US("ecs.amazonaws.com"), CA("ecs.amazonaws.ca"), UK(
				"ecs.amazonaws.co.uk"), DE("ecs.amazonaws.de"), FR(
				"ecs.amazonaws.fr"), JA("ecs.amazonaws.jp");

		private String endpoint;

		CountryCode(String endpoint) {
			this.endpoint = endpoint;
		}

		public String getEndpoint() {
			return endpoint;
		}
	}

	public AmazonProductAdvertisingClient(String accessKeyId,
			CountryCode countryCode, String secretKey, String associateTag) {
		this.accessKeyId = accessKeyId;
		this.secretKey = secretKey;
		this.countryCode = countryCode;
		this.associateTag = associateTag;
	}

	public List<Information> itemSearch(String keyword) throws Exception {
		/*
		 * Set up the signed requests helper
		 */
		SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(
					countryCode.getEndpoint(), accessKeyId, secretKey);
		} catch (Exception e) {
			throw e;
		}

		Map<String, String> params = getCommonParams();
		params.put("Operation", "ItemSearch");
		params.put("SearchIndex", "Books");
		params.put("Keywords", keyword);
		params.put("ResponseGroup", "Medium");

		String requestUrl = helper.sign(params);
		logger.debug(requestUrl);
		Document doc = fetch(requestUrl);
		return perseAsInformations(doc);
	}

	private Map<String, String> getCommonParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", "2009-03-31");
		params.put("AssociateTag", associateTag);
		return params;
	}

	private List<Information> perseAsInformations(Document doc)
			throws Exception {
		if (doc == null) {
			return null;
		}
		NodeList items = doc.getElementsByTagName("Item");
		if (items == null || items.getLength() <= 0) {
			return null;
		}

		List<Information> informations = new ArrayList<Information>();
		for (int i = 0; i < items.getLength(); i++) {
			Element item = (Element) items.item(i);
			Information row = new Information();
			row.setTitle(item.getElementsByTagName("Title").item(0)
					.getTextContent());
			row.setLink(new URL(item.getElementsByTagName("DetailPageURL").item(0)
					.getTextContent()));
			NodeList publicationDates = item
					.getElementsByTagName("PublicationDate");
			if (publicationDates != null && 0 < publicationDates.getLength()) {
				row.setPublishedAt(DateUtils.parseDate(publicationDates.item(0)
						.getTextContent(), "yyyy-MM-dd", "yyyy-MM"));
			} else {
				row.setPublishedAt(new Date()); // TODO
			}
			NodeList manufacturers = item.getElementsByTagName("Manufacturer");
			String manufact = null;
			if (manufacturers != null && 0 < manufacturers.getLength()) {
				manufact = manufacturers.item(0).getTextContent();
			} else {
				manufact = "";
			}
			row.setDescription(manufact
					+ " "
					+ item.getElementsByTagName("ProductGroup").item(0)
							.getTextContent());

			NodeList smallImages = item.getElementsByTagName("SmallImage");
			if (smallImages != null && 0 < smallImages.getLength()) {
				Element sImage = (Element) smallImages.item(0);
				row.setImageUrl(new URL(sImage.getElementsByTagName("URL").item(0)
						.getTextContent()));
			}

			informations.add(row);
		}
		return informations;
	}

	private static Object lock = new Object();

	private static Document fetch(String requestUrl) throws Exception {
		synchronized (lock) {
			// https://images-na.ssl-images-amazon.com/images/G/09/associates/paapi/dg/index.html?ErrorNumbers.html
			Thread.sleep(1 * 1000);

			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				return db.parse(requestUrl);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
