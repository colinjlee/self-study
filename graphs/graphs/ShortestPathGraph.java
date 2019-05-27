package graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

// Note that this is a simple graph with unique vertices and no directed multi-edges
// Note that the nodes contain the edges
// Both nodes hold data for an undirected edge (i.e. stored as 2 directed edges)
public final class ShortestPathGraph<T extends Comparable<T>>
							implements Graph<ShortestPathNode<T>> {
	private HashSet<ShortestPathNode<T>> nodeSet;
	private HashMap<T, ShortestPathNode<T>> nodeMap;
	private ShortestPathNode<T> source;
	private ShortestPathNode<T> target;

	public ShortestPathGraph() {
		nodeSet = new HashSet<>();
		nodeMap = new HashMap<>();
		source = null;
		target = null;
	}
	
	public void setSource(ShortestPathNode<T> node) {
		source = node;
	}
	
	public void setSource(T value) {
		source = nodeMap.get(value);
	}
	
	public void setTarget(ShortestPathNode<T> node) {
		target = node;
	}
	
	public void setTarget(T value) {
		target = nodeMap.get(value);
	}
	
	public ShortestPathNode<T> getSource() {
		return source;
	}
	
	public ShortestPathNode<T> getTarget() {
		return target;
	}
	
	// Note that the nodes hold the edges and edges can be added through nodes
	// Added for testing purposes
	public double getWeight() {
		double weight = 0;
		
		for (ShortestPathNode<T> node : nodeSet) {
			HashMap<ShortestPathNode<T>, Double> currNodeEdges = node.getEdgeMap();
			T currNodeValue = node.getData();
			
			for (ShortestPathNode<T> neighbor : currNodeEdges.keySet()) {
				HashMap<ShortestPathNode<T>, Double> neighborEdges = neighbor.getEdgeMap();
				Double edgeWeight = currNodeEdges.get(neighbor);
				
				// Undirected edge, only add once
				if (currNodeValue.compareTo(neighbor.getData()) < 0
						&& neighborEdges.containsKey(node)
						&& neighborEdges.get(node).equals(edgeWeight)) {
					weight += edgeWeight;
				
				// Directed edge, add if weight differs
				} else if (!neighborEdges.containsKey(node)
						|| !neighborEdges.get(node).equals(edgeWeight)) {
					weight += edgeWeight;
				}
			}
		}
		
		return weight;
	}

	@Override
	public int size() {
		return nodeSet.size();
	}

	@Override
	public Collection<ShortestPathNode<T>> getNodes() {
		return nodeSet;
	}
	
	public ShortestPathNode<T> getNode(T value) {
		return nodeMap.get(value);
	}

	// Nodes must be unique
	@Override
	public void addNode(ShortestPathNode<T> node) {
		if (!nodeSet.contains(node)) {
			nodeSet.add(node);
			nodeMap.put(node.getData(), node);
		}
	}
	
	public void addNode(T value) {
		if (!nodeMap.containsKey(value)) {
			ShortestPathNode<T> node = new ShortestPathNode<>(value);
			nodeSet.add(node);
			nodeMap.put(value, node);
		}
	}

	// If an edge already exists between the two nodes, updates the weight
	public void addEdge(ShortestPathNode<T> node1, ShortestPathNode<T> node2) {
		addEdge(node1, node2, 0);
	}
	
	public void addEdge(ShortestPathNode<T> node1, ShortestPathNode<T> node2, double weight) {
		node1.addEdge(node2, weight);
		node2.addEdge(node1, weight);
	}
	
	public void addEdge(T value1, T value2, double weight) {
		ShortestPathNode<T> node1 = nodeMap.get(value1);
		ShortestPathNode<T> node2 = nodeMap.get(value2);
		
		addEdge(node1, node2, weight);
	}
	
	public void addDirectedEdge(ShortestPathNode<T> fromNode, ShortestPathNode<T> toNode, double weight) {
		fromNode.addEdge(toNode, weight);
	}
	
	public void addDirectedEdge(T fromValue, T toValue, double weight) {
		ShortestPathNode<T> node1 = nodeMap.get(fromValue);
		ShortestPathNode<T> node2 = nodeMap.get(toValue);
		
		addDirectedEdge(node1, node2, weight);
	}
	
	@Override
	public void removeNode(ShortestPathNode<T> node) {
		removeNodesEdges(node.getData());
		nodeSet.remove(node);
		nodeMap.remove(node.getData());
	}
	
	public void removeNode(T value) {
		removeNodesEdges(value);
		ShortestPathNode<T> node = nodeMap.get(value);
		nodeSet.remove(node);
		nodeMap.remove(value);
	}

	public void removeEdge(ShortestPathNode<T> node1, ShortestPathNode<T> node2) {
		node1.removeEdge(node2);
		node2.removeEdge(node1);
	}
	
	public void removeEdge(T value1, T value2) {
		ShortestPathNode<T> node1 = nodeMap.get(value1);
		ShortestPathNode<T> node2 = nodeMap.get(value2);
		
		removeEdge(node1, node2);
	}
	
	public void removeDirectedEdge(ShortestPathNode<T> fromNode, ShortestPathNode<T> toNode) {
		fromNode.removeEdge(toNode);
	}
	
	public void removeDirectedEdge(T fromValue, T toValue) {
		ShortestPathNode<T> node1 = nodeMap.get(fromValue);
		ShortestPathNode<T> node2 = nodeMap.get(toValue);
		
		removeDirectedEdge(node1, node2);
	}
	
	// Helper to update other nodes' edges when a node is removed
	private void removeNodesEdges(T value) {
		ShortestPathNode<T> node = nodeMap.get(value);
		
		for (ShortestPathNode<T> otherNode : nodeSet) {
			HashMap<ShortestPathNode<T>, Double> edges = otherNode.getEdgeMap();
			if (edges.containsKey(node)) {
				edges.remove(node);
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Node Set: ");
		str.append(nodeSet);

		str.append("\nEdges:");
		for (T nodeValue : nodeMap.keySet()) {
			str.append(" {");
			str.append(nodeValue);
			str.append("=");
			str.append(nodeMap.get(nodeValue).getEdgeMap());
			str.append("},");
		}
		str.deleteCharAt(str.length()-1);
		
		str.append("\nSource: ");
		str.append(source);
		str.append("\nTarget: ");
		str.append(target);
		
		return (str.toString());
	}

}
