package com.dig_i.front.util;

import java.util.ArrayList;
import java.util.List;

import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.impl.StandardTagger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeCab {

	protected static Logger logger = LoggerFactory.getLogger(MeCab.class);

	private static final String[][] WHITE_LIST = { 
		{"名詞"}, 
		{"動詞"}
	};
	
	private static final String[][] BLACK_LIST = { 
		{"副詞"}, 
		{"連体詞"}, 
		{"接続詞"}, 
		{"感動詞"},
		{"助動詞"}, 
		{"助詞"}, 
		{"記号"}
	};
	
	public static String detectMainWord(String str) {
		List<MeCabDto> res = exec(str);
		if (res == null || res.isEmpty()) {
			return null;
		}
		List<String> ws = new ArrayList<String>();
		boolean detected = false;
		for(MeCabDto row : res) {
			// 名詞のみ抜き出す
			if(row.getSpeech().equals("名詞")) {
				detected = true;
				ws.add(row.getSurface());
			} else {
				if(detected) {
					// 「・」は人名の区切りで使われるので名詞のうちにする
					if(row.getSurface().equals("・")) {
						continue;
					}
					break;
				}
			}
		}
		return StringUtils.join(ws, "");
	}
	
	public static String[] splitWithWhitelist(String str, String[][] whitelist) {
		List<MeCabDto> res = exec(str);
		if (res == null || res.isEmpty()) {
			return null;
		}
		res = detectWithWhitelist(res, whitelist);
		return detectWords(res);
	}

	public static String[] splitWithBlacklist(String str, String[][] blacklist) {
		List<MeCabDto> res = exec(str);
		if (res == null || res.isEmpty()) {
			return null;
		}
		res = detectWithBlacklist(res, blacklist);
		return detectWords(res);
	}
	
	public static String[][] basicWhitelist() {
		return WHITE_LIST;
	}
	
	public static String[][] basicBlacklist() {
		return BLACK_LIST;
	}
	
	private static String[] detectWords(List<MeCabDto> res) {
		List<String> ret = new ArrayList<String>();
		for (MeCabDto row : res) {
			ret.add(row.getSurface());
		}
		return ret.toArray(new String[0]);
	}
	
	private static List<MeCabDto> detectWithWhitelist(
			List<MeCabDto> res, String[][] whitelist) {
		if (res == null || res.isEmpty()) {
			return null;
		}
		List<MeCabDto> ret = new ArrayList<MeCabDto>();
		for (MeCabDto row : res) {
			if (StringUtils.isEmpty(row.getSurface())) {
				continue;
			}
			if(!isMatchCondition(row, whitelist)) {
				continue;
			}
			ret.add(row);
		}
		return ret;
	}

	private static List<MeCabDto> detectWithBlacklist(
			List<MeCabDto> res, String[][] blacklist) {
		if (res == null || res.isEmpty()) {
			return null;
		}
		List<MeCabDto> ret = new ArrayList<MeCabDto>();
		for (MeCabDto row : res) {
			if (StringUtils.isEmpty(row.getSurface())) {
				continue;
			}
			if(isMatchCondition(row, blacklist)) {
				continue;
			}
			ret.add(row);
		}
		return ret;
	}
	
	private static boolean isMatchCondition(MeCabDto row, String[][] conditions) {
		for(String[] except : conditions) {
			switch(except.length) {
				case 2:
					if(row.getSpeech().equals(except[0]) 
							&& row.getSpeechDetail1().equals(except[0])) {
						return true;
					}
				case 1:
				default:
					if(row.getSpeech().equals(except[0])) {
						return true;
					}
			}
		}
		return false;
	}

	/*
	 * private static String exec(String str) throws Exception { StringBuffer
	 * cmd = new StringBuffer(); str = StringUtils.replace(str, "'", "\'");
	 * cmd.append("echo ") .append("'" + str + "'") .append(" | mecab");
	 * ExecCommand ec = new ExecCommand(cmd.toString());
	 * if(!StringUtils.isEmpty(new String(ec.getStderr()))) { throw new
	 * Exception(new String(ec.getStderr())); } return new
	 * String(ec.getStdout()); }
	 */
	/**
	 * @see http://code.google.com/p/cmecab-java/wiki/HowToUse
	 */
	public static List<MeCabDto> exec(String str) {
		// Taggerを構築。
		// 引数には、MeCabのcreateTagger()関数に与える引数を与える。
		StandardTagger tagger = new StandardTagger("");

		// Lattice（形態素解析に必要な実行時情報が格納されるオブジェクト）を構築
		Lattice lattice = tagger.createLattice();
		// 解析対象文字列をセット
		lattice.setSentence(str);

		// tagger.parse()を呼び出して、文字列を形態素解析する。
		tagger.parse(lattice);

		// 一つずつ形態素をたどりながら、表層形と素性を出力
		List<MeCabDto> meCabDtos = new ArrayList<MeCabDto>();
		Node node = lattice.bosNode();
		while (node != null) {
			MeCabDto row = new MeCabDto(node.surface(), node.feature());
			// http://d.hatena.ne.jp/yuboolike/20121114/1352876001
			if(row.getSpeech().equals("名詞") 
					&& row.getSpeechDetail1().equals("サ変接続")) {
				row.setSpeech("記号");
				row.setSpeechDetail1("一般");
			}
			meCabDtos.add(row);
			node = node.next();
		}

		// lattice, taggerを破壊
		lattice.destroy();
		tagger.destroy();
		return meCabDtos;
	}

}
