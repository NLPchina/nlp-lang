package org.nlpcn.commons.lang.viterbi;


import org.nlpcn.commons.lang.viterbi.function.Values;

/**
 * 包装类节点
 * Created by Ansj on 03/05/2017.
 */
public class Node<T> {

	/**
	 * 节点所持有的对象
	 */
	private T t;

	/**
	 * 用以累计的分数
	 */
	private Double score;

	/**
	 * 自身的分数
	 */
	private Double selfScore ;


	/**
	 * 当前节点的索引位置
	 */
	private int index;


	/**
	 * 可以到达的数组位置
	 */
	private int toIndex;

	/**
	 * 起始位置
	 */
	private Node from;


	public Node(int index, T t, Values<T> fun) {
		this.index = index;
		this.t = t;
		this.toIndex = index + fun.step(this);
		this.selfScore = fun.selfSscore(this) ;
	}


	public Double getScore() {
		return score;
	}


	public void setScore(Double score) {
		this.score = score;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getToIndex() {
		return toIndex;
	}

	public void setToIndex(int toIndex) {
		this.toIndex = toIndex;
	}

	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public T getObj() {
		return t;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public Double getSelfScore() {
		return selfScore;
	}

	public void setSelfScore(Double selfScore) {
		this.selfScore = selfScore;
	}

}
