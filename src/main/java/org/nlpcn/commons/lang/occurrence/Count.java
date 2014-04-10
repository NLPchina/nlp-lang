package org.nlpcn.commons.lang.occurrence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ansj on 4/10/14.
 */
public class Count implements Serializable {
	int id = 0;
	int count = 1; //这个词
	int relationCount; //这个词和其他词共同出现多少次
	String nature;

	Set<Integer> relationSet = new HashSet<Integer>();

	public Count(String nature, int id) {
		this.nature = nature;
		this.id = id;
	}


	public void upCount() {
		this.count++;
	}

	public void upRelation(int rId) {
		this.relationCount++;
		this.relationSet.add(rId);
	}


	@Override
	public String toString() {
		return this.id + "\t" + this.count + "\t" + this.relationCount;
	}
}