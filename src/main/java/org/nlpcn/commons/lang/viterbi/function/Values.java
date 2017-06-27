package org.nlpcn.commons.lang.viterbi.function;

import org.nlpcn.commons.lang.viterbi.Node;

/**
 * Created by Ansj on 03/05/2017.
 */
public interface Values<T> {


	/**
	 * 到达的位置,对于图结构.可能此节点的下一个节点是索引位3个以后并非按照顺序,如果按照顺序设置为1即可,必须大于0
	 * @param node
	 * @return
	 */
	public int step(Node<T> node) ;

	/**
	 * 节点上的
	 * @param node
	 */
	public double selfSscore(Node<T> node) ;

}
