package com.dig_i.front.util;

import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MeCabDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 表層形
	private String surface;
	// 品詞
	private String speech;
	// 品詞細分類1
	private String speechDetail1;
	// 品詞細分類2
	private String speechDetail2;
	// 品詞細分類3
	private String speechDetail3;
	// 活用形
	private String conjugatedForm;
	// 活用型
	private String conjugatedType;
	// 原形
	private String bare;
	// 読み
	private String read;
	// 発音
	private String pronunciation;

	public MeCabDto(String surface, String feature) {
		this.surface = surface;
		String[] features = StringUtils.split(feature, ',');
		speech = features[0];
		speechDetail1 = features[1];
		speechDetail2 = features[2];
		speechDetail3 = features[3];
		conjugatedForm = features[4];
		conjugatedType = features[5];
		read = 8 <= features.length ? features[7] : null;
		pronunciation = 9 <= features.length ? features[8] : null;
	}

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public String getSpeechDetail1() {
		return speechDetail1;
	}

	public void setSpeechDetail1(String speechDetail1) {
		this.speechDetail1 = speechDetail1;
	}

	public String getSpeechDetail2() {
		return speechDetail2;
	}

	public void setSpeechDetail2(String speechDetail2) {
		this.speechDetail2 = speechDetail2;
	}

	public String getSpeechDetail3() {
		return speechDetail3;
	}

	public void setSpeechDetail3(String speechDetail3) {
		this.speechDetail3 = speechDetail3;
	}

	public String getConjugatedForm() {
		return conjugatedForm;
	}

	public void setConjugatedForm(String conjugatedForm) {
		this.conjugatedForm = conjugatedForm;
	}

	public String getConjugatedType() {
		return conjugatedType;
	}

	public void setConjugatedType(String conjugatedType) {
		this.conjugatedType = conjugatedType;
	}

	public String getBare() {
		return bare;
	}

	public void setBare(String bare) {
		this.bare = bare;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getPronunciation() {
		return pronunciation;
	}

	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
}
