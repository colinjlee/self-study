package dijkstra;

public class Node implements Comparable<Node> {
	private int value;
	private double distance;
	
	public Node() {
		value = 0;
		distance = Double.POSITIVE_INFINITY;
	}
	
	public Node(int val) {
		value = val;
		distance = Double.POSITIVE_INFINITY;
	}
	
	// Getters
	public int getValue() {
		return value;
	}
	
	public double getDistance() {
		return distance;
	}
	
	// Setters
	public void setDistance(double newDistance) {
		distance = newDistance;
	}

	@Override
	public int compareTo(Node node) {
		return ((Double) distance).compareTo((Double) node.getDistance());
	}
	
	public String toString() {
		return Integer.toString(value);
	}

}
