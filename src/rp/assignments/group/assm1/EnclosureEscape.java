package rp.assignments.group.assm1;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class EnclosureEscape extends Thread implements SensorPortListener {
	
	private static final WheeledRobotConfiguration TAYYAB_CONFIG = RobotConfigs.EXPRESS_BOT;
	private static final int SLEEP_DURATION = 20;
	private static final int RANGE = 20;
	private static final int COURSE_ADJUST_ANGLE = 20;
	
	private boolean shouldTurn = false;

	public static void main(String[] args) {
		Button.waitForAnyPress();
		Thread thread = new BumperProgram();
		thread.setDaemon(true); //Forces the thread to stop once the main method exits
		thread.start();
		Button.waitForAnyPress();
		thread.interrupt();
	}
	
	public void run() {
		DifferentialPilot pilot = (new WheeledRobotSystem(TAYYAB_CONFIG)).getPilot();
		SensorPort.S3.addSensorPortListener(this);
		Rate rate = new Rate(SLEEP_DURATION);
		UltrasonicSensor proxSensor = new UltrasonicSensor(SensorPort.S2);
		
		pilot.forward();
		
		
		
		while (!isInterrupted()) {
			
			if (shouldTurn) {
				pilot.stop();
				pilot.travel(-0.1);
				
				pilot.rotate(45.0);
				
				pilot.forward();
				
				shouldTurn = false;
			}
			if (proxSensor.getDistance() < RANGE) {
				pilot.rotate(COURSE_ADJUST_ANGLE);
			} else {
				pilot.rotate(-COURSE_ADJUST_ANGLE);
			}
			rate.sleep();
		} 
		pilot.stop();
	}

	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		TouchSensor sensor = new TouchSensor(SensorPort.S3);
		if (sensor.isPressed()) 
			shouldTurn = true;
	}

}