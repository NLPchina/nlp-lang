package org.nlpcn.commons.lang.extracting.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ansj on 29/08/2017.
 */
public class TermToken extends Token{

	private Set<String> terms = new HashSet<String>();

	public TermToken(int index) {
		super(index);
	}

	public void add(int i , String term) {

 		terms.add(term);
	}

	public void add(String[] termArr) {
		for (String term : termArr) {
			terms.add(term);
		}
	}



}
