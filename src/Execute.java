import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Execute {

	public static void main(String[] args) {
//		LCD.drawString("Plugin Test", 0, 4);
//		Delay.msDelay(5000);
		
		StaticMaze.maze = new int[][] {
			{12,5,5,7},
			{9,6,13,6},
			{6,8,7,10},
			{9,1,5,3}
		};
		
		StaticMaze.initialY = 2;
		StaticMaze.initialX = 0;
		
		StaticMaze.finalY = 1;
		StaticMaze.finalX = 2;
		
		StaticMaze.maxY = 3;
		StaticMaze.maxX = 3;
		
		MazeState start = new MazeState();
		
		Search.DFS(start);	
	}

}
