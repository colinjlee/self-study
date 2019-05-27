package graphs;

import java.util.HashSet;
import java.util.HashMap;

public final class ShortestPathNode<T extends Comparable<T>>
							extends Node<T>
							implements Comparable<ShortestPathNode<T>> {
	private double distance;
	private HashSet<ShortestPathNode<T>> prevSet;
	private HashMap<ShortestPathNode<T>, Double> edgeMap; // <edgeTo, edgeWeight>
	
	public ShortestPathNode() {
		super();
		distance = Double.POSITIVE_INFINITY;
		prevSet = new HashSet<>();
		edgeMap = new HashMap<>();
	}
	
	public ShortestPathNode(T data) {
		super(data);
		distance = Double.POSITIVE_INFINITY;
		prevSet = new HashSet<>();
		edgeMap = new HashMap<>();
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public void setPrev(HashSet<ShortestPathNode<T>> set) {
		prevSet = set;
	}
	
	public void addPrevNode(ShortestPathNode<T> node) {
		prevSet.add(node);
	}
	
	protected void addEdge(ShortestPathNode<T> node, double weight) {
		edgeMap.put(node, weight);
	}
	
	protected void removeEdge(ShortestPathNode<T> node) {
		edgeMap.remove(node);
	}
	
	public HashSet<ShortestPathNode<T>> getPrevSet() {
		return prevSet;
	}
	
	public HashMap<ShortestPathNode<T>, Double> getEdgeMap() {
		return edgeMap;
	}
	
	public double getDistance() {
		return distance;
	}

	@Override
	public int compareTo(ShortestPathNode<T> other) {
		return (super.getData()).compareTo(other.getData());
	}
	
	@Override
	public int hashCode() {
		return super.getData().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ShortestPathNode<?>)) {
			return false;
		} else {
			return (super.getData()).equals(((ShortestPathNode<?>) obj).getData());
		}
	}
	
}
