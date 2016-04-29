import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class Execute {

	public static EV3ColorSensor sens;

	public static void main(String[] args) {
		sens = new EV3ColorSensor(SensorPort.S2);

		StaticMaze.maze = new int[][] { { 12, 5, 5, 7 }, { 9, 4, 5, 6 }, { 6, 8, 7, 10 }, { 9, 1, 5, 3 } };

		StaticMaze.initialX = 0;
		StaticMaze.initialY = 2;

		StaticMaze.initialDir = 1;

		StaticMaze.finalX = 2;
		StaticMaze.finalY = 1;

		StaticMaze.maxX = 3;
		StaticMaze.maxY = 3;

		/* INITIALIZATION COMPLETED */

		MazeState start = new MazeState();

		Stack<Node> pathBFS = Search.BFS(start, 0);

		execute(pathBFS);
	}

	private static void execute(Stack<Node> path) {
		boolean turnFlag = true;
		SensorMode temp = sens.getRGBMode();
		float[] fArray = new float[temp.sampleSize()];
		
//		while(true) {
//			Delay.msDelay(2000);
//			temp.fetchSample(fArray, 0);
//			System.out.println(Arrays.toString(fArray));
//		}

		while (true) {
			
			float[] fThresholdArray = new float[5];
			
			for(int i = 0; i < fThresholdArray.length; i++) {
				temp = sens.getRGBMode();
				temp.fetchSample(fArray, 0);
				fThresholdArray[i] = fArray[2];
				System.out.println("fArray is: " + Arrays.toString(fArray));
			}
			
			//float threshold = fThresholdArray[0];
			//float threshold = getMode(fThresholdArray);
			//float threshold = getAverage(fThresholdArray);
			float threshold = getMax(fThresholdArray);
			
			System.out.println("Threshold is: " + threshold);

			if (threshold > 0.02) {
				//0.07 with getRed				
				if(turnFlag) {
					Motor.A.rotate(60); // forward
					turnFlag = false;
				} else {
					Motor.D.rotate(60);	//forward
					turnFlag = true;
				}
			} else if (threshold > 0.015) {
				//0.04 with getRed
				changeState("Change direction");
				
				Motor.A.backward();
				Motor.D.backward();
				
				Delay.msDelay(100);
				
				Motor.A.stop();
				Motor.D.stop();
				
				Motor.A.rotate(180);
				
				Delay.msDelay(1000);
			} else {
				changeState("Stop");

//				Motor.A.backward();
//				Motor.D.backward();
//				
//				Delay.msDelay(200);
//				
//				Motor.A.stop();
//				Motor.D.stop();
//				
//				Motor.A.rotate(20);
//				
//				Motor.A.forward();
//				Motor.D.forward();
//				
//				Delay.msDelay(200);
//				
//				Motor.A.stop();
//				Motor.D.stop();
//				
//				for(int i = 0; i < 4; i++) {
//					temp = sens.getRedMode();
//					temp.fetchSample(fArray, 0);
//					fThresholdArray[i] = fArray[0];
//				}
//				
//				mode = getMode(fModeArray);
//				
//				if (mode < 0.05) {					
//					Motor.A.stop();
//					Motor.D.stop();
//					
//					Motor.D.rotate(20);
//					
//					Motor.A.forward();
//					Motor.D.forward();
//					
//					Delay.msDelay(200);
//					
//					Motor.A.stop();
//					Motor.D.stop();
//				}
								
				if(turnFlag) {
					Motor.A.rotate(20);
				} else {
					Motor.D.rotate(20);
				}
				
			}			
		}

	}
	
	private static float getMax(float[] array) {
		float max = array[0];
		for(int i = 1; i < array.length; i++) {
			if(array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
	
	private static float getAverage(float[] array) {
		float total = 0;
		for (int i = 0; i < array.length; i++) {
			total += array[i];
		}
		return (float)total/array.length;
	}

	private static float getMode(float[] array) {
		HashMap<Float, Integer> hm = new HashMap<Float, Integer>();
		float max = 1;
		float temp = array[0];
		for (int i = 0; i < array.length; i++) {
			if (hm.get(array[i]) != null) {
				int count = hm.get(array[i]);
				count = count + 1;
				hm.put(array[i], count);
				if (count > max) {
					max = count;
					temp = array[i];
				}
			} else {
				hm.put(array[i], 1);
			}
		}
		return temp;
	}

	private static String state;

	public static void changeState(String state1) {
		if (state == null || !state.equals(state1)) {
			state = state1;
			System.out.println("Current state: " + state);
		}
	}

}
