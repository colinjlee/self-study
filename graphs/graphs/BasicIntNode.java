package graphs;

public final class BasicIntNode extends Node<Integer> implements Comparable<BasicIntNode>{
	public BasicIntNode(Integer value) {
		super(value);
	}
	
	@Override
	public int compareTo(BasicIntNode other) {
		return this.getData().compareTo(other.getData());
	}
}
