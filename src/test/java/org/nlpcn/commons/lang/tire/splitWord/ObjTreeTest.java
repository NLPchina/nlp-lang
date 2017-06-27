package org.nlpcn.commons.lang.tire.splitWord;

import org.junit.Test;
import org.nlpcn.commons.lang.tire.ObjTree;

/**
 * Created by Ansj on 25/05/2017.
 */
public class ObjTreeTest {
	@Test
	public void test() throws Exception {
		ObjTree tree = new ObjTree();

		tree.add("孙健", "中国", "佑佑", 123);
		tree.add("孙健", 11);
		tree.add("孙健", "中国", "佑佑", 321);
		tree.add("孙健", "中国", "蔡晴", 456);
		tree.add("孙健", "中国", "佑佑", "蔡晴", 789);


		System.out.println(tree.get("孙健"));
		System.out.println(tree.get("孙健", "中国", "佑佑"));
		System.out.println(tree.get("孙健", "中国", "佑佑", "蔡晴"));
		System.out.println(tree.get("孙健", "中国"));
		System.out.println(tree.get("孙健", "中国", "蔡晴"));

	}
}
