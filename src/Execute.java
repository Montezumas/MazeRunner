import java.util.Arrays;
import java.util.Stack;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class Execute {
	
	public static EV3ColorSensor sens;

	public static void main(String[] args) {
		sens = new EV3ColorSensor(SensorPort.S2);
		
		StaticMaze.maze = new int[][] {
			{12,5,5,7},
			{9,4,5,6},
			{6,8,7,10},
			{9,1,5,3}
		};
				
		StaticMaze.initialX = 0;
		StaticMaze.initialY = 2;
		
		StaticMaze.initialDir = 1;
		
		StaticMaze.finalX = 2;
		StaticMaze.finalY = 1;
		
		StaticMaze.maxX = 3;
		StaticMaze.maxY = 3;
		
		/* INITIALIZATION COMPLETED */
		
		MazeState start = new MazeState();
		
		Stack<Node> pathBFS = Search.BFS(start,0);
		
		execute(pathBFS);
	}
	
	private static void execute(Stack<Node> path) {
		
		int direction = StaticMaze.initialDir;
		int currentX = StaticMaze.initialX;
		int currentY = StaticMaze.initialY;
		
		while(true) {
			
			//Node next = path.pop();
			//int dX = ((MazeState)next.state).roboX;
			//int dY = ((MazeState)next.state).roboY;
			SensorMode temp = sens.getRedMode();
			float[] fArray = new float[temp.sampleSize()];
			temp.fetchSample(fArray, 0);
			
			System.out.println("Color is: " + Arrays.toString(fArray));
			//path.push(next);
		}
		
	}

}
