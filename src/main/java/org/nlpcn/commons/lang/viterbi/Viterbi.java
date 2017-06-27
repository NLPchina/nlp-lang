package org.nlpcn.commons.lang.viterbi;

import org.nlpcn.commons.lang.viterbi.function.Score;
import org.nlpcn.commons.lang.viterbi.function.Values;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ansj on 03/05/2017.
 */
public class Viterbi<T> {

	private static final Node[] EMPTY= new Node[0] ;

	private Node[][] graph;

	public Viterbi(T[][] objGraph, Values<T> fun) {
		graph = new Node[objGraph.length][];
		for (int i = 0; i < objGraph.length; i++) {
			T[] arr = objGraph[i];
			graph[i] = new Node[arr.length];
			for (int j = 0; j < arr.length; j++) {
				graph[i][j] = new Node(i, arr[j], fun);
				if (i == 0) {
					graph[i][j].setScore(graph[i][j].getSelfScore());
				}
			}
		}
	}

	public Viterbi(List<T>[] objGraph, Values<T> fun) {
		graph = new Node[objGraph.length][];
		for (int i = 0; i < objGraph.length; i++) {
			List<T> list = objGraph[i];
			graph[i] = new Node[list.size()];
			for (int j = 0; j < list.size(); j++) {
				graph[i][j] = new Node(i, list.get(j), fun);
				if (i == 0) {
					graph[i][j].setScore(graph[i][j].getSelfScore());
				}
			}
		}
	}

	public List<T> compute(Score score) {
		Node[] arr = null;
		Node[] toArr = null;
		Node from = null;
		Node to = null;

		for (int i = 0; i < graph.length - 1; i++) {
			arr = graph[i];
			for (int j = 0; j < arr.length; j++) {
				from = arr[j];
				if(from.getToIndex()>=graph.length){
					continue;
				}
				toArr = graph[from.getToIndex()];
				for (int k = 0; k < toArr.length; k++) {
					to = toArr[k];
					double newScore = score.score(from, to);
					if (to.getScore() == null || (newScore > to.getScore()) == score.sort()) {
						to.setScore(newScore);
						to.setFrom(from);
					}
				}
			}
		}

		LinkedList<T> result = new LinkedList<T>();

		Node[] nodes = graph[graph.length - 1];

		//find max score node
		int maxIndex = 0;

		double maxScore = nodes[0].getScoreWithoutNull();
		for (int i = 1; i < nodes.length; i++) {
			if (nodes[i] == null) {
				continue;
			}
			if ((nodes[i].getScoreWithoutNull() > maxScore) == score.sort()) {
				maxScore = nodes[i].getScore();
				maxIndex = i;
			}

		}

		Node<T> node = graph[graph.length - 1][maxIndex];

		result.add(node.getObj());

		while ((node = node.getFrom()) != null) {
			result.addFirst(node.getObj());
		}

		return result;
	}

	public void printScore() {
		for (Node[] nodes : graph) {
			for (Node node : nodes) {
				System.out.print(node.getScore());
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public void print() {
		for (Node[] nodes : graph) {
			for (Node node : nodes) {
				System.out.print(node.getObj());
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public Viterbi<T> rmLittlePath(){
		for (int i = 0; i < graph.length; i++) {
			Node[] nodes = graph[i] ;
			int maxIndex = -1 ;
			Node maxNode = null ;
			for (int j = 0; j < nodes.length; j++) {
				if(maxIndex<nodes[j].getToIndex()){
					maxNode = nodes[j] ;
					maxIndex = Math.min(maxNode.getToIndex(),graph.length-1) ;
				}
			}

			boolean flag = true ;
			vd: for (int j = i+1; j < maxIndex; j++) {
				Node[] tempNodes = graph[j] ;
				for (Node node: tempNodes) {
					if(node.getToIndex()>maxIndex){
						flag = false ;
						break vd;
					}
				}

			}

			if(flag){
				for (int j = i+1; j < maxIndex; j++){
					graph[j] = EMPTY;
				}
			}

			i = Math.max(i,maxIndex) ;

		}

		return this ;
	}

	public void printSelfScore() {
		for (Node[] nodes : graph) {
			for (Node node : nodes) {
				System.out.print(node.getSelfScore());
				System.out.print("\t");
			}
			System.out.println();
		}
	}

}
