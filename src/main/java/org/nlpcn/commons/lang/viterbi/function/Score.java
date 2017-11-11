package org.nlpcn.commons.lang.viterbi.function;

import org.nlpcn.commons.lang.viterbi.Node;

/**
 * Created by Ansj on 04/05/2017.
 */
public interface Score<T> {

	/**
	 * 打分公式,用户自己实现
	 *
	 * @param from
	 */
	Double score(Node<T> from, Node<T> to);

	/**
	 * 排序方式,true代表自然顺序,就是从小到大,false反之
	 *
	 * @return
	 */
	boolean sort();
}
