package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import graphs.ShortestPathGraph;
import graphs.ShortestPathNode;

public class FloydWarshall {
	
	// Takes in a graph with vertices labeled 1 to n
	public static double[][] shortestPaths(ShortestPathGraph<Integer> g) {
		final Double POS_INF = Double.POSITIVE_INFINITY;
		int gLength = g.size();
		HashSet<ShortestPathNode<Integer>> nodeSet = (HashSet<ShortestPathNode<Integer>>) g.getNodes();
		double[][] dist = new double[gLength][gLength];
		
		// Initialize diagonal distances to 0 and all others to positive infinity
		for (int i = 0; i < gLength; i++) {
			for (int j = 0; j < gLength; j++) {
				if (i == j) {
					dist[i][j] = 0;
				} else {
					dist[i][j] = POS_INF;
				}
			}
		}
		
		// Put edge weights into dist[][]
		for (ShortestPathNode<Integer> node : nodeSet) {
			Integer currVal = node.getData() - 1;				// -1 for indexing
			HashMap<ShortestPathNode<Integer>, Double> edges = node.getEdgeMap();
			
			for (Map.Entry<ShortestPathNode<Integer>, Double> edge : edges.entrySet()) {
				ShortestPathNode<Integer> neighbor = edge.getKey();
				Integer neighborVal = neighbor.getData() - 1;   // -1 for indexing
				Double edgeWeight = edge.getValue();
				
				dist[currVal][neighborVal] = edgeWeight;
			}
		}
		
		// Floyd-Warshall
		for (int k = 0; k < gLength; k++) {
			for (int i = 0; i < gLength; i++) {
				for (int j = 0; j < gLength; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}
		
		return dist;
	}
	
	public static int[][] shortestPathsUsed(ShortestPathGraph<Integer> g) {
		final Double POS_INF = Double.POSITIVE_INFINITY;
		int gLength = g.size();
		HashSet<ShortestPathNode<Integer>> nodeSet = (HashSet<ShortestPathNode<Integer>>) g.getNodes();
		double[][] dist = new double[gLength][gLength];
		int [][] next = new int[gLength][gLength];
		
		// Initialize diagonal distances to 0 and all others to positive infinity
		// Initialize diagonal next values as itself, all others to -1 which acts as null
		for (int i = 0; i < gLength; i++) {
			for (int j = 0; j < gLength; j++) {
				if (i == j) {
					dist[i][j] = 0;
					next[i][j] = i + 1;
				} else {
					dist[i][j] = POS_INF;
					next[i][j] = -1;
				}
			}
		}
		
		// For each edge (u, v) with weight w:
		// Put dist[u][v] = w
		// Put next[u][v] = v
		for (ShortestPathNode<Integer> node : nodeSet) {
			Integer currValIndex = node.getData() - 1;
			HashMap<ShortestPathNode<Integer>, Double> edges = node.getEdgeMap();
			
			for (Map.Entry<ShortestPathNode<Integer>, Double> edge : edges.entrySet()) {
				ShortestPathNode<Integer> neighbor = edge.getKey();
				Integer neighborVal = neighbor.getData();
				Integer neighborValIndex = neighborVal - 1;
				Double edgeWeight = edge.getValue();
				
				dist[currValIndex][neighborValIndex] = edgeWeight;
				next[currValIndex][neighborValIndex] = neighborVal;
			}
		}
		
		// Floyd-Warshall with path reconstruction
		for (int k = 0; k < gLength; k++) {
			for (int i = 0; i < gLength; i++) {
				for (int j = 0; j < gLength; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}
		
		return next;	
	}
	
	public static ArrayList<Integer> shortestPathRecreate(int[][] next, int from, int to) {
		int fromIndex = from - 1;
		int toIndex = to - 1;
		
		if (fromIndex < 0 || fromIndex >= next.length
			|| toIndex< 0 || toIndex >= next.length
			|| next[fromIndex][toIndex] == -1) {
			return null;
		}
		
		ArrayList<Integer> path = new ArrayList<>();
		path.add(from);
		
		while (from != to) {
			from = next[fromIndex][toIndex];
			fromIndex = from - 1;
			path.add(from);
		}
		
		return path;
	}

}
