
public class MazeState implements Problem {

	int roboX;
	int roboY;

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
		MazeState[] succ = new MazeState[4];

		int walls = StaticMaze.maze[roboX][roboY];

		boolean[] wallMap = new boolean[4];

		wallMap[0] = (walls % 16 > 7);
		wallMap[1] = (walls % 8 > 3);
		wallMap[2] = (walls % 4 > 1);
		wallMap[3] = (walls % 2 == 1);

		for (int i = 0; i < wallMap.length; i++) {
			if (!wallMap[i]) {
				MazeState temp = null;

				switch (i) {
				case 0:
					if(roboX - 1 >= 0) {
						temp = new MazeState(roboX-1, roboY);
					}
					break;
				case 1:
					if(roboY + 1 <= StaticMaze.maxY) {
						temp = new MazeState(roboX, roboY+1);
					}
					break;
				case 2:
					if(roboX + 1 <= StaticMaze.maxX) {
						temp = new MazeState(roboX+1, roboY);
					}
					break;
				case 3:
					if(roboY - 1 >= 0) {
						temp = new MazeState(roboX, roboY-11);
					}
					break;
				default:
					System.out.println("Error shizz in succState switch");
					break;
				}
				
				succ[i] = temp;
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

	}

}
