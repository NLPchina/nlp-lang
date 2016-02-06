package org.nlpcn.commons.lang.tire.splitWord;

import java.io.BufferedReader;

import org.junit.Test;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.IOUtil;

public class ForestTest {

	@Test
	public void test() throws Exception {

		Forest f = new Forest();

		f.addBranch("补肾", null);
		f.addBranch("壮阳", null);
		f.addBranch("补肾壮阳F", null);
		f.addBranch("123", null);
		f.addBranch("Az", null);
		f.addBranch("5", null);
		f.addBranch("2", null);
		f.addBranch("0", null);

		GetWord word = f.getWord("　　5月20日，解放军强渡渭河");

		String temp = null;
		while ((temp = word.getAllWords()) != null) {
			System.out.println(temp);
		}

		Forest makeForest = Library.makeForest("D:\\git\\ansj_seg\\library\\default.dic");
		
		
		GetWord word2 = makeForest.getWord("10,上城区小营街道大学路,余林,330102196204011513 ,2,13456808992,大学路新村44-122-102,大学路新村44-122-102,Z2015120110302017,Z,2015-12-25") ;
		
		while ((temp = word2.getAllWords()) != null) {
			System.out.println(temp);
		}
		

//		BufferedReader reader = IOUtil.getReader("D:\\temp\\News", "GBK");

//		int j = 0;
//
//		String line = null;
//
//		while ((line = reader.readLine()) != null) {
//
//			word = makeForest.getWord(line);
//
//			while ((temp = word.getAllWords()) != null) {
//				if (temp.indexOf('�') > 0)
//					System.out.println(temp);
//			}
//		}
//
//		System.out.println(j);

	}

}
