package org.nlpcn.commons.lang.extracting.domain;

/**
 * Created by Ansj on 29/08/2017.
 */
public abstract class Token {
	private int index;

	public Token(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
