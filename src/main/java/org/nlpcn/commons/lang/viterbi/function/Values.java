package org.nlpcn.commons.lang.viterbi.function;

/**
 * Created by Ansj on 03/05/2017.
 */
public interface Values<T> {

	public Values DEFAULT = new Values() {

		@Override
		public int step(Object o) {
			return 1;
		}
	};

	/**
	 * 到达的位置,对于图结构.可能此节点的下一个节点是索引位3个以后并非按照顺序,如果按照顺序设置为1即可,必须大于0
	 * @param t
	 * @return
	 */
	public int step(T t) ;

}
