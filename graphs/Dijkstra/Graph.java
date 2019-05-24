package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
	private ArrayList<Node> nodeList;
	private HashMap<Integer, HashMap<Integer, Double>> edgeMap;
	private int numNodes;
	
	public Graph() {
		nodeList = new ArrayList<>();
		edgeMap = new HashMap<>();
		numNodes = 0;
	}
	
	public Graph(int n) {
		nodeList = new ArrayList<>(n+1);
		edgeMap = new HashMap<>();
		numNodes = n;
		
		nodeList.add(null);
		for (int i = 1; i <= n; i++) {
			nodeList.add(new Node(i));
		}
	}
	
	// Getters
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}
	
	public HashMap<Integer, HashMap<Integer, Double>> getEdgeMap() {
		return edgeMap;
	}
	
	public int size() {
		return numNodes;
	}
	
	// Setters
	public void addEdge(int from, int to, double weight) {
		HashMap<Integer, Double> map = edgeMap.get(from);
		
		if (map == null) {
			map = new HashMap<>();
			map.put(to,  weight);
			edgeMap.put(from, map);
			
		} else {
			map.put(to, weight);
		}
	}
	
	@Override
	public String toString() {
		return ("Node List: " + nodeList.toString() + "\n"
				+ "Edge Map: " + edgeMap.toString());
	}

}
