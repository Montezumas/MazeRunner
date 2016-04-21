import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Execute {

	public static void main(String[] args) {
//		LCD.drawString("Plugin Test", 0, 4);
//		Delay.msDelay(5000);
		
		StaticMaze.maze = new int[][] {
			{12,5,5,7},
			{9,4,5,6},
			{6,8,7,10},
			{9,1,5,3}
		};
		
		StaticMaze.initialX = 0;
		StaticMaze.initialY = 2;
		
		StaticMaze.finalX = 2;
		StaticMaze.finalY = 1;
		
		StaticMaze.maxX = 3;
		StaticMaze.maxY = 3;
		
		MazeState start = new MazeState();
		
		Search.BFS(start,0);
		
		Search.DFS(start);
	}

}
