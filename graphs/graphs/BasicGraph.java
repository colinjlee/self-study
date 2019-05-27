package graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public final class BasicGraph implements Graph<BasicIntNode> {
	private HashMap<Integer, BasicIntNode> nodeMap;
	private HashSet<BasicIntNode> nodeSet;
	private HashSet<Edge<BasicIntNode>> edgeSet;
	private long totalWeight;

	// Empty graph
	public BasicGraph() {
		nodeMap = new HashMap<>();
		nodeSet = new HashSet<>();
		edgeSet = new HashSet<>();
		totalWeight = 0;
	}
	
	// Graph with n vertices labeled 1-n and no edges
	public BasicGraph(int n) {
		nodeMap = new HashMap<>();
		nodeSet = new HashSet<>();
		edgeSet = new HashSet<>();
		totalWeight = 0;
		
		for (int i = 1; i <= n; i++) {
			BasicIntNode node = new BasicIntNode(i);
			nodeMap.put(i, node);
			nodeSet.add(node);
		}
	}
	
	// Various simple graphs with undirected edge weights of 1
	public BasicGraph(char c, int n) {
		this((Character.toUpperCase(c) == 'B') ? 2*n : n);
		
		switch (Character.toUpperCase(c)) {
			case 'K': // Complete graph
				for (int i = 1; i <= n; i++) {
					BasicIntNode node1 = nodeMap.get(i);
					
					for (int j = i+1; j <= n; j++) {
						BasicIntNode node2 = nodeMap.get(j);
						
						this.addEdge(new Edge<BasicIntNode>(node1, node2, 1));
					}
				}
				totalWeight = (n * (n - 1)) / 2;
				break;
			case 'C': // Cycle graph
				for (int i = 1; i < n; i++) {
					BasicIntNode node1 = nodeMap.get(i);
					BasicIntNode node2 = nodeMap.get(i+1);
					
					this.addEdge(new Edge<BasicIntNode>(node1, node2, 1));
				}
				BasicIntNode lastNode = nodeMap.get(n);
				BasicIntNode firstNode = nodeMap.get(1);
				
				this.addEdge(new Edge<BasicIntNode>(lastNode, firstNode, 1));
				totalWeight = n;
				break;
			case 'B': // Complete bipartite graph (K_n,n)
				for (int i = 1; i <= n; i++) {
					BasicIntNode node1 = nodeMap.get(i);
					
					for (int j = n+1; j <= 2*n; j++) {
						BasicIntNode node2 = nodeMap.get(j);
						
						this.addEdge(new Edge<BasicIntNode>(node1, node2, 1));
					}
				}
				totalWeight = n * n;
				break;
			default:  // No edges
				break;
		}
	}
	
	@Override
	public int size() {
		return nodeSet.size();
	}
	
	public int numEdges() {
		return edgeSet.size();
	}

	@Override
	public Collection<BasicIntNode> getNodes() {
		return nodeSet;
	}
	
	public BasicIntNode get(Integer i) {
		return nodeMap.get(i);
	}
	
	public HashSet<Edge<BasicIntNode>> getEdges() {
		return edgeSet;
	}
	
	public long getWeight() {
		return totalWeight;
	}

	@Override
	public void addNode(BasicIntNode node) {
		if (!nodeSet.contains(node)) {
			nodeMap.put(node.getData(), node);
			nodeSet.add(node);
		}
	}
	
	public void addNode(Integer i) {
		if (!nodeMap.containsKey(i)) {
			BasicIntNode node = new BasicIntNode(i);
			
			nodeMap.put(i, node);
			nodeSet.add(node);
		}
	}

	@Override
	public void removeNode(BasicIntNode node) {
		removeNodesEdges(node.getData());
		nodeMap.remove(node.getData());
		nodeSet.remove(node);
	}
	
	public void removeNode(Integer i) {
		removeNodesEdges(i);
		nodeSet.remove(nodeMap.get(i));
		nodeMap.remove(i);
	}
	
	// Helper to update graph edges when node is removed
	// Could store mapping of ints to edges they are an endpoint to for quicker removal
	// but more memory
	private void removeNodesEdges(Integer i) {
		BasicIntNode node = nodeMap.get(i);
		HashSet<Edge<BasicIntNode>> toRemove = new HashSet<>();
		
		for (Edge<BasicIntNode> edge : edgeSet) {
			if (edge.getNode1() == node || edge.getNode2() == node) {
				toRemove.add(edge);
			}
		}
		edgeSet.removeAll(toRemove);
	}

	public void addEdge(Edge<BasicIntNode> edge) {
		if (!edgeSet.contains(edge)) {
			edgeSet.add(edge);
			totalWeight += edge.getWeight();
		}
	}

	public void removeEdge(Edge<BasicIntNode> edge) {
		if (edgeSet.contains(edge)) {
			edgeSet.remove(edge);
			totalWeight -= edge.getWeight();
		}
	}
	
	@Override
	public String toString() {
		TreeSet<Edge<BasicIntNode>> orderedEdges = new TreeSet<>();
		orderedEdges.addAll(edgeSet);
		
		return ("Node set: " + nodeMap.keySet()
				+ "\nEdge set: " + orderedEdges
				+ "\nTotal weight: " + totalWeight);
	}

}
