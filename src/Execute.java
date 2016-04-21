import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Execute {

	public static void main(String[] args) {
//		LCD.drawString("Plugin Test", 0, 4);
//		Delay.msDelay(5000);
		
		StaticMaze.maze = new int[][] {
			{12,5,4,7},
			{9,6,9,6},
			{14,8,7,10},
			{9,1,5,3}
		};
		
		StaticMaze.initialX = 2;
		StaticMaze.initialY = 0;
		
		StaticMaze.finalX = 1;
		StaticMaze.finalY = 2;
		
		StaticMaze.maxX = 3;
		StaticMaze.maxY = 3;
		
		MazeState start = new MazeState();
		
		Search.BFS(start, 0);
		
	}

}
