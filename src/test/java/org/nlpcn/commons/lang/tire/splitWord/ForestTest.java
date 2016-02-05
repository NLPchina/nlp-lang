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

		BufferedReader reader = IOUtil.getReader("D:\\temp\\News", "GBK");

		int j = 0;

		String line = null;

		while ((line = reader.readLine()) != null) {

			word = makeForest.getWord(line);


			while ((temp = word.getAllWords()) != null) {
System.out.println(temp);
			}
		}

		System.out.println(j);

	}

}

