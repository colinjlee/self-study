package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

public class Algorithms extends Graph {
	
	// Dijkstra's with min priority queue to return the shortest path length/weight
	public static double dijkstraLength(Graph g, int source, int target) {
		
		if (source < 1 || source > g.size() || target < 1 || target > g.size()) {
			return -1;
		}
		
		PriorityQueue<Node> pQueue = new PriorityQueue<>(g.size());
		ArrayList<Node> prev = new ArrayList<>();
		ArrayList<Node> nodes = g.getNodeList();
		nodes.get(source).setDistance(0);
		
		// Initialize prev list to all null
		for (int i = 0; i < g.size()+1; i++) {
			prev.add(i, null);
		}
		
		// Add all nodes to the priority queue
		for (int i = 1; i < nodes.size(); i++) {
			pQueue.add(nodes.get(i));
		}
		
		while (!pQueue.isEmpty()) {
			Node currNode = pQueue.remove();
			HashMap<Integer, Double> edges = g.getEdgeMap().get(currNode.getValue());
			double currDistance = currNode.getDistance();
			
			for (int k : edges.keySet()) {
				double alt = currDistance + edges.get(k);
				Node toNode = nodes.get(k);
				
				if (alt < toNode.getDistance()) {
					
					if (toNode.getValue() == target) {
						return alt;
					} else {
						toNode.setDistance(alt);
						prev.set(toNode.getValue(), currNode);
						
						// Reorder priority queue with new distance of toNode
						pQueue.remove(toNode);
						pQueue.add(toNode);
					}
				}
			}
		}
		
		return nodes.get(target).getDistance();
	}
	
	// Dijkstra's with min priority queue
	// Returns list of single previous node used
	public static ArrayList<Node> dijkstraMinPQOne(Graph g, int source) {
		
		if (source < 1 || source > g.size()) {
			return null;
		}
		
		ArrayList<HashSet<Node>> all = dijkstraMinPQAll(g, source);
		ArrayList<Node> one = new ArrayList<>();
		
		for (int i = 0; i < all.size(); i++) {
			HashSet<Node> currSet = all.get(i);
			
			if (currSet == null || currSet.size() == 0) {
				one.add(i, null);
			} else {
				
				// Just add any one element from set
				for (Node node : currSet) {
					one.add(i, node);
					break;
				}
			}
		}
		
		return one;
	}
	
	// Dijkstra's algorithm with a min priority queue
	// Returns list of sets containing previous nodes used
	public static ArrayList<HashSet<Node>> dijkstraMinPQAll(Graph g, int source) {
		
		if (source < 1 || source > g.size()) {
			return null;
		}
		
		PriorityQueue<Node> pQueue = new PriorityQueue<>(g.size());
		ArrayList<HashSet<Node>> prev = new ArrayList<>();
		ArrayList<Node> nodes = g.getNodeList();
		nodes.get(source).setDistance(0);
		
		// Initialize prev list to all empty sets
		for (int i = 0; i < g.size()+1; i++) {
			HashSet<Node> initSet = new HashSet<>();
			prev.add(i, initSet);
		}
		
		// Add all nodes to the priority queue
		for (int i = 1; i < nodes.size(); i++) {
			pQueue.add(nodes.get(i));
		}
		
		while (!pQueue.isEmpty()) {
			Node currNode = pQueue.remove();
			HashMap<Integer, Double> edges = g.getEdgeMap().get(currNode.getValue());
			double currDistance = currNode.getDistance();
			
			for (int k : edges.keySet()) {
				double alt = currDistance + edges.get(k);
				Node toNode = nodes.get(k);
				
				if (alt < toNode.getDistance()) {
					toNode.setDistance(alt);
					HashSet<Node> prevNodes = new HashSet<>();
					
					prevNodes.add(currNode);
					prev.set(toNode.getValue(), prevNodes);
					
					// Reorder priority queue with new distance of toNode
					pQueue.remove(toNode);
					pQueue.add(toNode);
				} else if (alt == toNode.getDistance()) {
					prev.get(toNode.getValue()).add(currNode);
				}
			}
		}
		
		return prev;
	}
	
	// Dijkstra's algorithm with a fibonacci heap
	// Returns list of sets containing previous nodes used
	public static ArrayList<HashSet<Node>> dijkstraFibHeapAll(Graph g, int source) {
		
		if (source < 1 || source > g.size()) {
			return null;
		}
		
		FibonacciHeap<Node> fibHeap = new FibonacciHeap<>();
		ArrayList<HashSet<Node>> prev = new ArrayList<>();
		ArrayList<Node> nodes = g.getNodeList();
		nodes.get(source).setDistance(0);
		
		// Initialize prev list to all empty sets
		for (int i = 0; i < g.size()+1; i++) {
			HashSet<Node> initSet = new HashSet<>();
			prev.add(i, initSet);
		}
		
		// Add all nodes to the fibonacci heap
		for (int i = 1; i < nodes.size(); i++) {
			FibonacciHeapNode<Node> node = new FibonacciHeapNode<>(nodes.get(i));
			
			fibHeap.insert(node, nodes.get(i).getDistance());
		}
		
		while (!fibHeap.isEmpty()) {
			Node currNode = fibHeap.removeMin().getData();
			HashMap<Integer, Double> edges = g.getEdgeMap().get(currNode.getValue());
			double currDistance = currNode.getDistance();
			
			for (int k : edges.keySet()) {
				double alt = currDistance + edges.get(k);
				Node toNode = nodes.get(k);
				
				if (alt < toNode.getDistance()) {
					toNode.setDistance(alt);
					HashSet<Node> prevNodes = new HashSet<>();
					
					prevNodes.add(currNode);
					prev.set(toNode.getValue(), prevNodes);
				} else if (alt == toNode.getDistance()) {
					prev.get(toNode.getValue()).add(currNode);
				}
			}
		}
		
		return prev;
	}

}
