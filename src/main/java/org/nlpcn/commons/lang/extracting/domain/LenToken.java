package org.nlpcn.commons.lang.extracting.domain;

/**
 * Created by Ansj on 29/08/2017.
 */
public class LenToken extends Token {
	private int max;
	private int min;

	public LenToken(int index, int min, int max) {
		super(index);
		this.min = min;
		this.max = max;
	}
}
