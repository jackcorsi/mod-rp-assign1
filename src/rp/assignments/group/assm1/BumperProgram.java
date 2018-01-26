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

/**
 * @author ajb769
 * @author jxc1090
 * @author afp766
 * 
 * Part 2 of Exercise1
 */
public class BumperProgram extends Thread implements SensorPortListener {
	
	/** Configuration of Tayyab (Express Bot) */
	private static final WheeledRobotConfiguration TAYYAB_CONFIG = RobotConfigs.EXPRESS_BOT;
	/** delay constant */
	private static final int SLEEP_DURATION = 20;
	/** flag for turning
	 * @see stateChanged(SensorPort,int,int) */
	private boolean shouldTurn = false;

	/**
	 * starts robot as a thread so it may be interrupted later
	 * @param args 
	 */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		Thread thread = new BumperProgram();
		thread.setDaemon(true); //Forces the thread to stop once the main method exits
		thread.start();
		Button.waitForAnyPress();
		thread.interrupt();
	}
	
	/**
	 * Called by {@link main(String[])} via thread to be interrupted easily 
	 */
	public void run() {
		DifferentialPilot pilot = (new WheeledRobotSystem(TAYYAB_CONFIG)).getPilot();
		SensorPort.S3.addSensorPortListener(this);
		Rate rate = new Rate(SLEEP_DURATION);
		pilot.forward();
		
		while (!isInterrupted()) {
			if (shouldTurn) {
				pilot.stop();
				pilot.travel(-0.1);
				pilot.rotate(180.0);
				pilot.forward();
				shouldTurn = false;
			}
			rate.sleep();
		} 
		pilot.stop();
	}

	/**
	 * set {@link shouldTurn} flag to true for {@link run()} to implement
	 */
	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		TouchSensor sensor = new TouchSensor(SensorPort.S3);
		if (sensor.isPressed()) 
			shouldTurn = true;
	}

}
