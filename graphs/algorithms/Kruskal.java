package algorithms;

import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.alg.util.UnionFind;

import graphs.BasicGraph;
import graphs.BasicIntNode;
import graphs.Edge;

public class Kruskal {
	
	// Return a new graph that is the minimum spanning tree of g
	public static BasicGraph minSpanTree(BasicGraph g) {
		BasicGraph minTree = new BasicGraph(g.size());
		PriorityQueue<Edge<BasicIntNode>> pQueue 
			= new PriorityQueue<>(g.getEdges().size(), (x, y) -> x.getWeight().compareTo(y.getWeight())); 
		UnionFind<BasicIntNode> disjointSet 
			= new UnionFind<BasicIntNode>((Set<BasicIntNode>) g.getNodes());
		
		pQueue.addAll(g.getEdges());
		
		while (!pQueue.isEmpty() && disjointSet.numberOfSets() != 1) {
			Edge<BasicIntNode> minEdge = pQueue.remove();
			BasicIntNode node1 = minEdge.getNode1();
			BasicIntNode node2 = minEdge.getNode2();
			
			if (!(disjointSet.inSameSet(node1, node2))) {
				disjointSet.union(node1, node2);
				Edge<BasicIntNode> copyEdge = new Edge<>(minTree.get(node1.getData()),
														 minTree.get(node2.getData()),
														 minEdge.getWeight());
				minTree.addEdge(copyEdge);
			}
		}
		
		return minTree;
	}
	
	// Return the total weight of the minimum spanning tree of g
	public static long minSpanTreeWeight(BasicGraph g) {
		return minSpanTree(g).getWeight();
	}
}
