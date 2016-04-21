
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
		int walls = StaticMaze.maze[roboY][roboX];
		boolean[] wallMap = new boolean[4];

		wallMap[0] = (walls % 16 > 7); // left
		wallMap[1] = (walls % 8 > 3); // up
		wallMap[2] = (walls % 4 > 1); // right
		wallMap[3] = (walls % 2 == 1); // down

		MazeState[] succ = new MazeState[4];
		
		if (!wallMap[0]) {
			if (roboX - 1 >= 0) {
				succ[0] = new MazeState(roboX-1, roboY);
			}
		}
		
		if (!wallMap[1]) {
			if (roboY - 1 >= 0) {
				succ[1] = new MazeState(roboX, roboY-1);
			}
		}
		
		if (!wallMap[2]) {
			if (roboX + 1 <= StaticMaze.maxX) {
				succ[2] = new MazeState(roboX+1, roboY);
			}
		}
		
		if (!wallMap[3]) {
			if (roboY + 1 <= StaticMaze.maxX) {
				succ[3] = new MazeState(roboX, roboY+1);
			}
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
