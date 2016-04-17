
public interface Problem {

	/**
	 * Check if the object is a goal state
	 * @return	if this is goal state
	 */
	boolean isGoalState();
	
	/**
	 * This will generate the successor states
	 * that can result from all possible actions
	 * @return	Successor states
	 */
	Problem[] succStates();
	
	/**
	 * Calculates the cost to take an action from state
	 * f to the state of this object
	 * @param f		the state to travel from
	 * @return		cost to get to this state
	 */
	int calcCost(Problem f);
	
	/**
	 * get heuristic based on flag number
	 * 
	 * @param flag
	 * @return heuristic value
	 */
	int getHeuristic(int flag);
	
	/**
	 * Print the goal state inside the method
	 */
	void printState();
	
}