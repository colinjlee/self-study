package algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import graphs.ShortestPathGraph;
import graphs.ShortestPathNode;

public class Dijkstra {

	// Dijkstra's with min priority queue to return the shortest path length/weight
	public static <T extends Comparable<T>> double shortestPathWeight(ShortestPathGraph<T> g) {
		ShortestPathNode<T> source = g.getSource();
		ShortestPathNode<T> target = g.getTarget();
		
		if (source == null || target == null) {
			return -1;
		}
		
		HashSet<ShortestPathNode<T>> nodeSet = (HashSet<ShortestPathNode<T>>) g.getNodes();
		PriorityQueue<ShortestPathNode<T>> pQueue = new PriorityQueue<>(g.size(),
				(x, y) -> ((Double) x.getDistance()).compareTo((Double) y.getDistance()));
		
		// Set distances
		for (ShortestPathNode<T> node : nodeSet) {
			if (node == source) {
				node.setDistance(0);
			} else {
				node.setDistance(Double.POSITIVE_INFINITY);
			}
		}
		
		// Add all nodes to the priority queue
		pQueue.addAll(nodeSet);
		
		while (!pQueue.isEmpty()) {
			ShortestPathNode<T> currNode = pQueue.remove();
			HashMap<ShortestPathNode<T>, Double> edges = currNode.getEdgeMap();
			double currDistance = currNode.getDistance();
			
			for (ShortestPathNode<T> neighbor : edges.keySet()) {
				double edgeWeight = edges.get(neighbor);
				
				if (edgeWeight < 0) {
					throw new IllegalArgumentException("Negative edge weight found from "
								+ currNode + " to " + neighbor
								+ ". Edge weight can't be negative for dijkstra's");
				}
				
				double alt = currDistance + edgeWeight;
				
				if (alt < neighbor.getDistance()) {
					
					if (neighbor == target) {
						return alt;
					} else {
						neighbor.setDistance(alt);
						neighbor.getPrevSet().clear();
						neighbor.addPrevNode(currNode);
						
						// Reorder priority queue with new distance of toNode
						pQueue.remove(neighbor);
						pQueue.add(neighbor);
					}
				}
			}
		}
		
		return target.getDistance();
	}
	
	// Dijkstra's with min priority queue
	// Returns mapping of a node to a previous node used in path
	public static <T extends Comparable<T>> TreeMap<ShortestPathNode<T>, ShortestPathNode<T>>
			oneShortestPath(ShortestPathGraph<T> g) {
		ShortestPathNode<T> source = g.getSource();
		
		if (source == null) {
			return null;
		}
		
		TreeMap<ShortestPathNode<T>, HashSet<ShortestPathNode<T>>> all = allShortestPaths(g);
		TreeMap<ShortestPathNode<T>, ShortestPathNode<T>> ans = new TreeMap<>();
		
		for (ShortestPathNode<T> k : all.keySet()) {
			HashSet<ShortestPathNode<T>> neighborSet = all.get(k);
			
			// Put any 1 neighbor 
			for (ShortestPathNode<T> neighbor : neighborSet) {
				ans.put(k, neighbor);
				break;
			}
		}
		
		return ans;
		
	}
	
	// Dijkstra's algorithm with a min priority queue
	// Returns mapping of nodes to sets containing previous nodes used
	public static <T extends Comparable<T>> TreeMap<ShortestPathNode<T>, HashSet<ShortestPathNode<T>>>
			allShortestPaths(ShortestPathGraph<T> g) {
		ShortestPathNode<T> source = g.getSource();
		
		if (source == null) {
			return null;
		}
		
		HashSet<ShortestPathNode<T>> nodeSet = (HashSet<ShortestPathNode<T>>) g.getNodes();
		TreeMap<ShortestPathNode<T>, HashSet<ShortestPathNode<T>>> ans = new TreeMap<>();
		PriorityQueue<ShortestPathNode<T>> pQueue = new PriorityQueue<>(nodeSet.size(),
				(x, y) -> ((Double) x.getDistance()).compareTo((Double) y.getDistance()));
		source.setDistance(0);
		
		// Add all nodes to the priority queue
		pQueue.addAll(nodeSet);
		
		while (!pQueue.isEmpty()) {
			ShortestPathNode<T> currNode = pQueue.remove();
			HashMap<ShortestPathNode<T>, Double> edges = currNode.getEdgeMap();
			double currDistance = currNode.getDistance();
			
			for (ShortestPathNode<T> neighbor : edges.keySet()) {
				double edgeWeight = edges.get(neighbor);
				
				if (edgeWeight < 0) {
					throw new IllegalArgumentException("Negative edge weight found from "
							+ currNode + " to " + neighbor
							+ ". Edge weight can't be negative for dijkstra's");
				}
				
				double alt = currDistance + edgeWeight;
				
				if (alt < neighbor.getDistance()) {
					neighbor.setDistance(alt);
					neighbor.getPrevSet().clear();
					neighbor.addPrevNode(currNode);
					
					// Reorder priority queue with new distance of neighbor
					pQueue.remove(neighbor);
					pQueue.add(neighbor);
				} else if (alt == neighbor.getDistance()) {
					neighbor.addPrevNode(currNode);
				}
			}
		}
		
		for (ShortestPathNode<T> node : nodeSet) {
			ans.put(node, node.getPrevSet());
		}
		
		return ans;
		
	}
	
	// Dijkstra's algorithm with a fibonacci heap
	// Returns mapping of nodes to sets containing previous nodes used
	public static <T extends Comparable<T>> TreeMap<ShortestPathNode<T>, HashSet<ShortestPathNode<T>>>
			allWithFibHeap(ShortestPathGraph<T> g) {
		ShortestPathNode<T> source = g.getSource();
		
		if (source == null) {
			return null;
		}
		
		HashSet<ShortestPathNode<T>> nodeSet = (HashSet<ShortestPathNode<T>>) g.getNodes();
		TreeMap<ShortestPathNode<T>, HashSet<ShortestPathNode<T>>> ans = new TreeMap<>();
		FibonacciHeap<ShortestPathNode<T>> fibHeap = new FibonacciHeap<>();
		source.setDistance(0);
		
		// Add all nodes to the fibonacci heap
		for (ShortestPathNode<T> node : nodeSet) {
			FibonacciHeapNode<ShortestPathNode<T>> fibNode = new FibonacciHeapNode<>(node);
			fibHeap.insert(fibNode, node.getDistance());
		}
		
		while (!fibHeap.isEmpty()) {
			ShortestPathNode<T> currNode = fibHeap.removeMin().getData();
			HashMap<ShortestPathNode<T>, Double> edges = currNode.getEdgeMap();
			double currDistance = currNode.getDistance();
			
			for (ShortestPathNode<T> neighbor : edges.keySet()) {
				double edgeWeight = edges.get(neighbor);
				
				if (edgeWeight < 0) {
					throw new IllegalArgumentException("Negative edge weight found from "
							+ currNode + " to " + neighbor
							+ ". Edge weight can't be negative for dijkstra's");
				}
				
				double alt = currDistance + edgeWeight;
				
				if (alt < neighbor.getDistance()) {
					neighbor.setDistance(alt);
					neighbor.addPrevNode(currNode);
				} else if (alt == neighbor.getDistance()) {
					neighbor.addPrevNode(currNode);
				}
			}
		}
		
		for (ShortestPathNode<T> node : nodeSet) {
			ans.put(node, node.getPrevSet());
		}
		
		return ans;
	}
}
