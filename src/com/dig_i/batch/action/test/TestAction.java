package com.dig_i.batch.action.test;

import org.apache.struts2.convention.annotation.Action;

import com.dig_i.batch.action.AbstractAction;
import com.dig_i.front.util.MeCab;

public class TestAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value = "test/test")
	public String execute() throws Exception {
		logger.info("execute() start");
		Thread.sleep(10 * 1000);
		logger.info("execute() end");
		return NONE;
	}

	@Action(value = "test/mecab")
	public String mecabAction() throws Exception {
		try {
			System.loadLibrary("mecab"); // MeCabを読み込む
		} catch (UnsatisfiedLinkError e) {
			// MeCabが読み込めなかったときの処理
			logger.info("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains\n"
					+ e);

		}
		// MeCab mecab = new MeCab();
		MeCab.splitWithWhitelist("テスト", MeCab.basicWhitelist());
		return NONE;
	}
}
