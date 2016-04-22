package com.dig_i.front.entity;

public enum MobileMailDomain {
	DOCOMO("docomo.ne.jp"), AU("ezweb.ne.jp"), SOFTBANK("softbank.ne.jp"), SOFTBANK_MMS(
			"i.softbank.jp");

	private String name;

	private MobileMailDomain(String name) {
		this.name = name;
	}

	/*
	 * public static MobileMailDomain nameOf(String name) { for(MobileMailDomain
	 * e : MobileMailDomain.values()) { if(e.name().equals(name)) { return e; }
	 * } return null; }
	 */
	public String toString() {
		return this.name;
	}
}
