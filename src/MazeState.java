
public class MazeState implements Problem {

	public int roboX;
	public int roboY;

	public MazeState() {
		this.roboX = StaticMaze.initialX;
		this.roboY = StaticMaze.initialY;
	}

	public MazeState(int x, int y) {
		this.roboX = x;
		this.roboY = y;
	}

	@Override
	public boolean isGoalState() {
		return (this.roboX == StaticMaze.finalX && this.roboY == StaticMaze.finalY);
	}

	@Override
	public Problem[] succStates() {		
		// bitmap of walls
		int walls = StaticMaze.maze[roboY][roboX];

		MazeState[] succ = new MazeState[4];
		
		// left
		if (!(walls % 16 > 7) && roboX - 1 >= 0) {
			succ[0] = new MazeState(roboX-1, roboY);	
		}
		
		// up
		if (!(walls % 8 > 3) && roboY - 1 >= 0) {
			succ[1] = new MazeState(roboX, roboY-1);
		}
		
		// right
		if (!(walls % 4 > 1) && roboX + 1 <= StaticMaze.maxX) {
			succ[2] = new MazeState(roboX+1, roboY);
		}
		
		// down
		if (!(walls % 2 == 1) && roboY + 1 <= StaticMaze.maxX) {
			succ[3] = new MazeState(roboX, roboY+1);
		}

		return succ;
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
		System.out.println("Robot at: " + roboX + " " + roboY);
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		MazeState equal = (MazeState) o;
		return (this.roboX == equal.roboX && this.roboY == equal.roboY);
	}
}
