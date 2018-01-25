package rp.assignments.group.assm1;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class BumperProgram extends Thread implements SensorPortListener {
	
	private static final WheeledRobotConfiguration TAYYAB_CONFIG = RobotConfigs.EXPRESS_BOT;
	private static final int SLEEP_DURATION = 20;
	
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
		pilot.forward();
		
		while (!isInterrupted()) {
			if (shouldTurn) {
				System.out.println(isInterrupted() + "0");
				pilot.stop();
				System.out.println(isInterrupted() + "1");
				pilot.travel(-0.1);
				System.out.println(isInterrupted() + "2");
				pilot.rotate(180.0);
				System.out.println(isInterrupted() + "3");
				pilot.forward();
				System.out.println(isInterrupted() + "4");
				shouldTurn = false;
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
