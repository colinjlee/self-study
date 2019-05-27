package algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

import graphs.ShortestPathGraph;
import graphs.ShortestPathNode;

public class Prim {
	
	public static <T extends Comparable<T>> ShortestPathGraph<T> minSpanTree(ShortestPathGraph<T> g) {
		HashSet<ShortestPathNode<T>> nodeSet = (HashSet<ShortestPathNode<T>>) g.getNodes();
		ShortestPathGraph<T> ans = new ShortestPathGraph<>();
		final Double INF = Double.POSITIVE_INFINITY;
		
		HashMap<T, Double> cheapestWeight = new HashMap<>();
		HashMap<T, T> cheapestEdge = new HashMap<>();
		HashSet<ShortestPathNode<T>> notAdded = new HashSet<>();
		PriorityQueue<T> pQueue = new PriorityQueue<>(nodeSet.size(),
				(x, y) -> cheapestWeight.getOrDefault(x, INF).compareTo(cheapestWeight.getOrDefault(y, INF)));
		
		for (ShortestPathNode<T> node : nodeSet) {
			T value = node.getData();
			
			notAdded.add(node);
			pQueue.add(value);
			cheapestWeight.put(value, INF);
			cheapestEdge.put(value, null);
		}
		
		while (!pQueue.isEmpty()) {
			T currValue = pQueue.remove();
			T currEdgeTo = cheapestEdge.get(currValue);
			double currEdgeWeight = cheapestWeight.get(currValue);
			ShortestPathNode<T> currNode = g.getNode(currValue);
			
			ans.addNode(currValue);
			if (currEdgeTo != null) {
				ans.addEdge(currValue, currEdgeTo, currEdgeWeight);
			}
			
			HashMap<ShortestPathNode<T>, Double> currEdges = currNode.getEdgeMap();
			for (Map.Entry<ShortestPathNode<T>, Double> neighbor : currEdges.entrySet()) {
				T edgeTo = neighbor.getKey().getData();
				double edgeWeight = neighbor.getValue();
				
				if (ans.getNode(edgeTo) == null && edgeWeight < cheapestWeight.get(edgeTo)) {
					cheapestWeight.put(edgeTo, edgeWeight);
					cheapestEdge.put(edgeTo, currValue);
					pQueue.remove(edgeTo);
					pQueue.add(edgeTo);
				}
			}
		}
		
		return ans;
	}
	
	public static <T extends Comparable<T>> double minSpanTreeWeight(ShortestPathGraph<T> g) {
		return minSpanTree(g).getWeight();
	}

}
