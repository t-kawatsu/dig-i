package com.dig_i.front.util;

import java.util.List;
import java.util.ArrayList;

public class NGram {

	public static List<String> split(String str) {
		return split(str, 2);
	}

	public static List<String> split(String str, int n) {
		List<String> t = new ArrayList<String>();
		if (n < str.length()) {
			for (int i = 0; i <= (str.length() - n); i++) {
				t.add(str.substring(i, i + n));
			}
		} else {
			t.add(str);
		}
		return t;
	}
}
