import java.util.Stack;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.internal.ev3.EV3LED;

public class Execute {

	private static EV3ColorSensor sens;

	/*
	 * This factor is for: white = .24 brown/white = .17 (.14-.2) brown = .14
	 * 
	 * DEFAULT: white = .32 brown/white = .25 (.2-.28) brown = .19
	 * 
	 * 1.17f or less in darkness
	 * 1.47f in light
	 */
	private static float lightFactor = 1.47f;
	
	private static int counter = 0;
	private static int action = -1;


	public static void main(String[] args) {
		sens = new EV3ColorSensor(SensorPort.S2);

		StaticMaze.maze = new int[][] { { 12, 5, 5, 7 }, { 9, 4, 5, 6 }, { 6, 8, 7, 10 }, { 9, 1, 5, 3 } };

		StaticMaze.initialX = 0;
		StaticMaze.initialY = 2;

		StaticMaze.initialDir = 2;

		StaticMaze.finalX = 2;
		StaticMaze.finalY = 1;

		StaticMaze.maxX = 3;
		StaticMaze.maxY = 3;

		/* INITIALIZATION COMPLETED */

		MazeState start = new MazeState();

		Stack<Node> path = Search.BFS(start, 0);

		execute(path);
	}

	private static void execute(Stack<Node> path) {
		path.pop(); // pop the initial state
		int cX = StaticMaze.initialX;
		int cY = StaticMaze.initialY;
		int cD = StaticMaze.initialDir;

		boolean atNode = false;
		SensorMode temp = sens.getRGBMode();
		float[] fArray = new float[temp.sampleSize()];

		Node nextNode = path.pop();

		int nX = ((MazeState) nextNode.state).roboX;
		int nY = ((MazeState) nextNode.state).roboY;
		int nD = nodeChange(cX, cY, nX, nY, cD);
		System.out
				.println("At: [" + cX + "," + cY + "] dir=" + cD + " need to go to: [" + nX + "," + nY + "] dir=" + nD);

		// while(true) {
		// Delay.msDelay(2000);
		// temp.fetchSample(fArray, 0);
		// System.out.println(Arrays.toString(fArray));
		// }

		while (true) {

			float[] fThresholdArray = new float[5];

			for (int i = 0; i < fThresholdArray.length; i++) {
				temp = sens.getRGBMode();
				temp.fetchSample(fArray, 0);
				fThresholdArray[i] = fArray[0];
				// System.out.println("fArray is: " + Arrays.toString(fArray));
			}

			float threshold = getAverage(fThresholdArray);

			threshold *= lightFactor;

			// System.out.println("Threshold is: " + threshold);

			if (threshold > 0.12) { // between brown and white
				counter++;
				//System.out.println(counter);
				// System.out.println("Between threshold is: " + threshold);
				float correction = 100 - (400 * threshold);

				if (Math.abs(correction) < 5) {
					Motor.A.rotate(40);
					Motor.D.rotate(40);
				} else if (correction < 0) {
					correction *= (-1);
					Motor.D.rotate((int) correction);
				} else {
					Motor.A.rotate((int) correction);
				}

				if (atNode) {
					counter = 0;
					//System.out.println("Counter reset");
				}
				atNode = false;
			} else { // on something black
				if (!atNode && counter > 20) {
					//System.out.println("Threshold was: " + threshold);
					cX = nX;
					cY = nY;
					cD = nD;

					if (!path.isEmpty()) {
						nextNode = path.pop();
					} else {
						System.out.println("At the goal!");
						Sound.systemSound(false, 3);
						LocalEV3.ev3.getLED().setPattern(EV3LED.COLOR_GREEN);
						return;
					}

					atNode = true;

					nX = ((MazeState) nextNode.state).roboX;
					nY = ((MazeState) nextNode.state).roboY;
					nD = nodeChange(cX, cY, nX, nY, cD);
					System.out.println("At: [" + cX + "," + cY + "] dir=" + cD + " need to go to: [" + nX + "," + nY
							+ "] dir=" + nD);

				} else if (counter < 20) {
					//System.out.println("Black but counter not high enough");
					counter = 0;
					switch (action) {
					case 0:
						turnLeft();
						break;
					case 1:
						turnRight();
						break;
					case 2:
						straight();
						break;
					default:
						System.out.println("Action error");
						break;
					}
				}
			}

		}

	}

	private static int nodeChange(int cX, int cY, int nX, int nY, int cD) {
		int nD = -1;

		if (cX - nX < 0 && cY - nY == 0) { // go right
			switch (cD) {
			case 0:
				// turn right
				turnRight();
				break;
			case 1:
				// straight
				straight();
				break;
			case 2:
				// left
				turnLeft();
				break;
			case 3:
				// turnaround
				break;
			}
			nD = 1;
		}

		if (nX - cX < 0 && cY - nY == 0) { // go left
			switch (cD) {
			case 0:
				// turn left
				turnLeft();
				break;
			case 1:
				// turnaround
				break;
			case 2:
				// right
				turnRight();
				break;
			case 3:
				// straight
				straight();
				break;
			}
			nD = 3;
		}

		if (cX - nX == 0 && cY - nY < 0) { // go down
			switch (cD) {
			case 0:
				// turnaround
				break;
			case 1:
				// right
				turnRight();
				break;
			case 2:
				// straight
				straight();
				break;
			case 3:
				// left
				turnLeft();
				break;
			}
			nD = 2;
		}

		if (cX - nX == 0 && nY - cY < 0) { // go up
			switch (cD) {
			case 0:
				// straight
				straight();
				break;
			case 1:
				// left
				turnLeft();
				break;
			case 2:
				// turnaround
				break;
			case 3:
				// right
				turnRight();
				break;
			}
			nD = 0;
		}

		return nD;
	}

	private static void turnLeft() {
		SensorMode temp = sens.getRGBMode();
		float[] fArray = new float[temp.sampleSize()];
		action = 0;

		while (true) {

			float[] fThresholdArray = new float[3];

			for (int i = 0; i < fThresholdArray.length; i++) {
				temp = sens.getRGBMode();
				temp.fetchSample(fArray, 0);
				fThresholdArray[i] = fArray[0];
			}

			float threshold = getAverage(fThresholdArray);

			if (threshold < 0.12) { // on black
				Motor.D.rotate(10);

			} else {
				return;
			}
		}
	}

	private static void turnRight() {
		SensorMode temp = sens.getRGBMode();
		float[] fArray = new float[temp.sampleSize()];
		action = 1;

		while (true) {

			float[] fThresholdArray = new float[3];

			for (int i = 0; i < fThresholdArray.length; i++) {
				temp = sens.getRGBMode();
				temp.fetchSample(fArray, 0);
				fThresholdArray[i] = fArray[0];
			}

			float threshold = getAverage(fThresholdArray);

			if (threshold < 0.12) { // on black
				Motor.A.rotate(10);

			} else {
				return;
			}
		}
	}

	private static void straight() {
		SensorMode temp = sens.getRGBMode();
		float[] fArray = new float[temp.sampleSize()];
		action = 2;

		while (true) {

			float[] fThresholdArray = new float[3];

			for (int i = 0; i < fThresholdArray.length; i++) {
				temp = sens.getRGBMode();
				temp.fetchSample(fArray, 0);
				fThresholdArray[i] = fArray[0];
			}

			float threshold = getAverage(fThresholdArray);

			if (threshold < 0.12) { // on black
				Motor.A.rotate(10);
				Motor.D.rotate(10);

			} else {
				return;
			}
		}
	}

	private static float getAverage(float[] array) {
		float total = 0;
		for (int i = 0; i < array.length; i++) {
			total += array[i];
		}
		return (float) total / array.length;
	}

}
