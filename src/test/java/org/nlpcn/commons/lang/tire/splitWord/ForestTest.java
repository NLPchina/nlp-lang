package org.nlpcn.commons.lang.tire.splitWord;

import org.junit.Test;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;

import junit.framework.Assert;

public class ForestTest {

	@Test
	public void test() throws Exception {

		Forest f = new Forest();

		f.addBranch("补肾", null);
		f.addBranch("壮阳", null);
		f.addBranch("补肾壮阳F", null);
		f.addBranch("Az", null);
		f.addBranch("5", null);
		f.addBranch("2", null);
		f.addBranch("0", null);
		f.addBranch("12", null);
		f.addBranch("23", null);

		GetWord word = f.getWord("　　5月20日，解放军强渡渭河123");

		String temp = null;
		while ((temp = word.getFrontWords()) != null) {
			Assert.assertEquals(temp, "5");
		}

	}

}
