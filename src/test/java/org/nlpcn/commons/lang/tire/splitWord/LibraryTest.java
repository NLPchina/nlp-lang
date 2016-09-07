package org.nlpcn.commons.lang.tire.splitWord;

import org.junit.Test;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

public class LibraryTest {

	@Test
	public void test() throws Exception {
		Forest makeForest = Library.makeForest("/Users/sunjian/Downloads/default.dic","gbk") ;
		
		System.out.println(makeForest.getBranch("上访"));
	}

}
