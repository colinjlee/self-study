package graphs;

public class Edge<T extends Node<?> & Comparable<T>> implements Comparable<Edge<T>>{
	private final T node1;
	private final T node2;
	private Integer weight;
	
	public Edge(T node1, T node2, Integer weight) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public T getNode1() {
		return node1;
	}
	
	public T getNode2() {
		return node2;
	}
	
	public Integer getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return ("(" + node1.getData() + ")-<" + weight + ">-(" + node2.getData() + ")");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Edge)) {
			return false;
		} else {
			Edge<?> other = (Edge<?>) obj;
			return (node1.getData().equals(other.getNode1().getData())
					&& node2.getData().equals(other.getNode2().getData()));
		}
	}
	
	@Override
	public int compareTo(Edge<T> other) {
		T firstNode = other.getNode1();
		int firstCompare = node1.compareTo(firstNode);
		
		return firstCompare == 0 ? node2.compareTo(other.getNode2()) : firstCompare;
	}
}
