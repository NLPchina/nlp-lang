package org.nlpcn.commons.lang.tire.splitWord;

import org.junit.Test;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;

public class ForestTest {

	@Test
	public void test() {

		
		Forest f = new Forest() ;
		
		f.addBranch("补肾壮阳F",null);
		
		f.addBranch("补肾",null);
		f.addBranch("壮阳",null);
		
		GetWord word = f.getWord("补肾壮阳");
		
		String temp = null ;
		while((temp=word.getFrontWords())!=null){
			System.out.println(temp);
		}
		
	}

}
