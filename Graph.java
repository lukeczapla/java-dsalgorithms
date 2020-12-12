
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

public class Graph {

	private int nodes;
	private double[][] M;
	private List<List<Integer>> adjacencyList;

	private List<Integer> bestPath = null;
	private double bestCost = -1.0;
	
	public Graph(int N) {
		this.nodes = N;
		M = new double[N][N];
		adjacencyList = new ArrayList<>(N);
		for (int i = 0; i < N; i++) adjacencyList.add(new LinkedList<Integer>());
	}
	
	public void addPath(int a, int b, double cost) {
		M[a][b] = cost;
		adjacencyList.get(a).add(b);
	}

	public void addBiPath(int a, int b, double cost) {
		M[a][b] = cost;
		M[b][a] = cost;
		adjacencyList.get(a).add(b);
		adjacencyList.get(b).add(a);
	}
	
	public double DFSmain(int source, int target, double cost, List<Integer> visited) {
		// the following conditional 'if' is an optimization, can remove!
		if (bestCost != -1.0 && cost > bestCost) return bestCost;
		if (source == target) {
			if (bestCost == -1.0 || cost < bestCost) {
				bestCost = cost;
				bestPath = new ArrayList<Integer>(visited);
				bestPath.add(target);
			}
			return cost;
		}
		visited.add(source);
		Iterator<Integer> iter = adjacencyList.get(source).iterator();
		double smallest = -1.0;
		while (iter.hasNext()) {
			Integer next = iter.next();
			if (visited.contains(next)) continue;
			double nextCost = DFSmain(next, target, cost+M[source][next], visited);
			if (nextCost != -1.0 && (smallest == -1.0 || nextCost < smallest)) {
				smallest = nextCost;
			}
		}
		visited.remove(visited.size()-1);
		
		return smallest;
	}
	
	public double DFS(int source, int target) {
		bestCost = -1.0;
		bestPath = null;
		return DFSmain(source, target, 0.0, new ArrayList<Integer>());
	}
	
	public double BFS(int source, int target) {
		bestCost = -1.0;
		bestPath = null;
		List<Information> queue = new LinkedList<>();
		queue.add(new Information(source, 0.0, new ArrayList<>()));
		while (queue.size() > 0) {
			Information value = queue.remove(0);
			int current = value.location;
			if (bestCost != -1.0 && value.cost > bestCost) {
				continue;
			}
			else if (current == target) {
				if (bestCost == -1.0 || value.cost < bestCost) {
					bestCost = value.cost;
					value.visited.add(current);
					bestPath = value.visited;
				}
			} 
			else if (!adjacencyList.get(current).isEmpty()) {
				Iterator<Integer> iter = adjacencyList.get(current).iterator();
				while (iter.hasNext()) {
					Integer next = iter.next();
					if (value.visited.contains(next)) continue;
					ArrayList<Integer> visited = new ArrayList<>(value.visited);
					visited.add(current);
					queue.add(new Information(next, value.cost+M[current][next], visited));
				}
			}
		}
		return bestCost;
	}

	/**
	 * Efficiently calculates N choose r with minimal overflow using the long datatype.
	 * @param N the N in N choose r
	 * @param r the r in N choose r
	 * @return the value of N choose r as a long (maximum 2^(63)-1 value without overflow)
	 */
	public static long C(int N, int r) {
		long result = 1L;
		for (int i = 1; i <= r; i++) {
			result *= N--;
			result /= i;
		}
		return result;
	}

	public static long nDiceResults(int sides, int N, int result) {
		int range = (result - N)/sides;
		long total = 0L;
		for (int k = 0; k <= range; k++) {
			long term;
			if (k % 2 == 0) term = 1L;
			else term = -1L;
			term *= C(N, k);
			term *= C(result-sides*k-1, N-1);
			total += term;
		}
		return total;
	}
	
	public List<Integer> getBestPath() {
		return bestPath;
	}
	
	public double getBestCost() {
		return bestCost;
	}
	
	public int getNodes() {
		return nodes;
	}
	
	
	/**
	 * Information about the current trip in the breadth first search, this
	 * data goes into a queue so that we know what current node we're at
	 * (location), how much the trip cost so far (cost), and what nodes we
	 * have visited (visited).
	 * @author luke
	 *
	 */
	private static class Information {
		public int location;
		public double cost;
		public List<Integer> visited;

		public Information(int location, double cost, List<Integer> visited) {
			this.location = location;
			this.cost = cost;
			this.visited = visited;
		}
		
	}

}
