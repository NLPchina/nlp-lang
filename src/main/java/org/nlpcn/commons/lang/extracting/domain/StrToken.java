package org.nlpcn.commons.lang.extracting.domain;

/**
 * Created by Ansj on 29/08/2017.
 */
public class StrToken extends  Token{
	private String str ;

	public StrToken(int index ,String str) {
		super(index);
		this.str = str;
	}
}
