import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Defines static search methods that can be used on problems
 * that are extensions of the Problem class
 * 
 * @author Robby
 *
 */
public class Search {

	/**
	 * Breadth-first search of a problem
	 * This will be A* search if flag > 0
	 * 
	 * @param init the initial state of a problem
	 * @param flag defines the heuristic function to use
	 * @return the number of nodes expanded during search
	 */
	public static int BFS(Problem init, int flag) {
		// initializing required data structures
		Queue<Node> queue = new PriorityQueue<Node>();
		Node start = new Node(init, null, 0, 0, 0);
		queue.add(start);

		// variables needed for search
		Node current;
		Problem[] succ;
		Node succNode;
		int expanded = 0;

		// search algorithm
		while (!queue.isEmpty()) {
			current = queue.poll();

			if (current.state.isGoalState()) {
				// goal reached, time to print
				print(current);
				return expanded;
			}

			// not the goal state
			
			succ = current.state.succStates();
			for (int i = 0; i < succ.length; i++) {
				if (succ[i] != null && !checkToRoot(current, succ[i])) {
					succNode = new Node(succ[i], current, current.pathCost + succ[i].calcCost(current.state), succ[i].getHeuristic(flag), current.depth + 1);
					queue.add(succNode);
				}
			}

			// node has been fully expanded
			expanded++;
		}

		// goal not reached in while loop
		System.out.println("Goal state not found");
		return expanded;
	}

	/**
	 * Depth-first search of a problem
	 * 
	 * @param init the initial state of a problem
	 * @returns the number of nodes expanded during search
	 */
	public static int DFS(Problem init) {
		// Initializing required data structures
		Stack<Node> stack = new Stack<Node>();
		Node start = new Node(init, null, 0, 0, 0);
		stack.push(start);

		// variables for search
		Node current;
		Problem[] succ;
		Node succNode;
		int expanded = 0;

		// search algorithm
		while (!stack.isEmpty()) {
			current = stack.pop();

			if (current.state.isGoalState()) {
				// goal reached
				print(current);
				return expanded;
			}

			// not the goal state

			succ = current.state.succStates();
			for (int i = 0; i < succ.length; i++) {
				if (succ[i] != null && !checkToRoot(current, succ[i])) {
					succNode = new Node(succ[i], current, current.pathCost + succ[i].calcCost(current.state), 0, current.depth + 1);
					stack.push(succNode);
				}
			}

			// node has been fully expanded
			expanded++;
		}

		// goal not reached in while loop
		System.out.println("Goal state not found");
		return expanded;
	}
	
	/**
	 * Check from iter to the root of the tree to see if
	 * the state has already been searched in the tree
	 * 
	 * @param iter the start of the search
	 * @param state the state to look for
	 * @return true = in tree, false = not in tree
	 */
	private static boolean checkToRoot(Node iter, Problem state) {		
		while(iter != null) {
			if(iter.state.equals(state)){
				return true;
			}
			iter = iter.parent;
		}
		
		return false;
	}

	/**
	 * prints the path from the passed in node to the head of the tree in
	 * reverse order
	 * 
	 * @param finalState the state to be printed last
	 */
	private static void print(Node finalState) {
		Stack<Node> st = new Stack<Node>();

		while (finalState != null) {
			// push the nodes
			st.push(finalState);
			finalState = finalState.parent;
		}

		while (!st.isEmpty()) {
			// print the nodes
			st.pop().state.printState();
		}

		System.out.println("Goal state has been reached\n");
	}
	
}

/**
 * Node to wrap problem classes
 * 
 * @author Robby
 */
class Node implements Comparable<Node> {
	
	public Problem state;
	public Node parent;
	public int pathCost;
	public int heuristic;
	public int depth;

	public Node(Problem s, Node p, int c, int h, int d) {
		state = s;
		parent = p;
		pathCost = c;
		heuristic = h;
		depth = d;
	}

	@Override
	public int compareTo(Node o) {
		return (heuristic + pathCost) - (o.heuristic + o.pathCost) ;
	}

}