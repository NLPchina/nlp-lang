package org.nlpcn.commons.lang.viterbi;

import org.nlpcn.commons.lang.viterbi.function.Score;
import org.nlpcn.commons.lang.viterbi.function.Values;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ansj on 03/05/2017.
 */
public class Viterbi<T> {

	private Node[][] graph;

	private Node[] end = new Node[]{new Node(0, null, Values.DEFAULT)};

	public Viterbi(T[][] objGraph, Values<T> fun) {
		this.graph = graph;
		graph = new Node[objGraph.length + 1][];
		graph[graph.length - 1] = end;
		for (int i = 0; i < objGraph.length; i++) {
			T[] arr = objGraph[i];
			graph[i] = new Node[arr.length];
			for (int j = 0; j < arr.length; j++) {
				graph[i + 1][j] = new Node(i, arr[j], fun);
			}
		}
	}

	public List<T> compute(Score score) {
		Node[] arr = null;
		Node[] toArr = null;
		Node from = null;
		Node to = null ;
		for (int i = 0; i < graph.length - 1; i++) {
			arr = graph[i];
			for (int j = 0; j < arr.length; j++) {
				from = arr[j];
				toArr = graph[from.getToIndex()];
				for (int k = 0; k < toArr.length; k++) {
					to = toArr[k] ;
					double newScore = score.score(from, to)+from.getScore();
					if((newScore > to.getScore())==score.sort()){
						to.setScore(newScore);
						to.setFrom(from);
					}
				}
			}
		}

		LinkedList<T> result = new LinkedList<T>() ;

		Node<T> node = graph[graph.length - 1][0];

		while((node=node.getFrom())!=null){
			result.addFirst(node.getObj());
		}

		return result;
	}

}
