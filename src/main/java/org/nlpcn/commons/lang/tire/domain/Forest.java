package org.nlpcn.commons.lang.tire.domain;

import org.nlpcn.commons.lang.tire.GetWord;

public class Forest extends SmartForest<String[]> {

	public Forest() {
	};

	public Forest(char c, int status, String[] param) {
		super(c, status, param);
	}

	public SmartForest<String[]> get(char c) {
		return this.getBranch(c);
	}

	public SmartForest<String[]> getBranch(char c) {
		return super.getBranch(c);
	}

	public GetWord getWord(String str) {
		return getWord(str.toCharArray());
	}

	public GetWord getWord(char[] chars) {
		return new GetWord(this, chars);
	}

	public String[] getParams() {
		return this.getParam();
	}

}