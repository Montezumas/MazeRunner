
public class Maze implements Problem {
	
	
	
	public Maze() {
		
	}

	@Override
	public boolean isGoalState() {
		return false;
	}

	@Override
	public Problem[] succStates() {
		return null;
	}

	@Override
	public int calcCost(Problem f) {
		return 0;
	}

	@Override
	public int getHeuristic(int flag) {
		return 0;
	}

	@Override
	public void printState() {
		
	}

}
