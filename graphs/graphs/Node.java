package graphs;

public abstract class Node<T> {
	private T data;
	
	public Node() {
		data = null;
	}
	
	public Node(T data) {
		this.data = data;
	}
	
	public final void setData(T data) {
		this.data = data;
	}
	
	public final T getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}

}