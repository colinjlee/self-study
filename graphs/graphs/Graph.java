package graphs;

import java.util.Collection;

public interface Graph<T extends Node<?>> {
	
	public int size();
	
	public Collection<T> getNodes();
	
	public void addNode(T node);

	public void removeNode(T node);

}
