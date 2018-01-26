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

/**
 * @author afp766
 * @author jxc1090
 * @author ajb769
 * 
 * Controls Tayyab to escape an enclosure
 * Assumes proximity sensor on the left of the robot (port S2) and two bumpers at front (ports S1 and S4)
 */
public class EnclosureEscape extends Thread implements SensorPortListener {
	
	/** Configuration of Tayyab (Express Bot) */
	private static final WheeledRobotConfiguration TAYYAB_CONFIG = RobotConfigs.EXPRESS_BOT;
	/** delay constant */
	private static final int SLEEP_DURATION = 20;
	/** ideal distance tayyab should be from the wall */
	private static final int RANGE = 20;
	/** rate of rotation to/from wall*/
	private static final double COURSE_ADJUST_RATE = 20.0; //TODO adapt to dynamic rate based on sensor reading
	/** angle to rotate upon bump event*/
	private static final double BUMP_ANGLE = -45.0;
	
	/** flag for turning
	 * @see stateChanged(SensorPort,int,int) */
	private boolean shouldTurn = false;

	/**
	 * starts robot as a thread so it may be interrupted later
	 * @param args 
	 */
	public static void main(String[] args) {
		System.out.println("Enclosure escape!");
		Button.waitForAnyPress();
		Thread thread = new EnclosureEscape();
		thread.setDaemon(true); //Forces the thread to stop once the main method exits
		thread.start();
		Button.waitForAnyPress();
		thread.interrupt();
	}
	
	/**
	 * adjusts course so as to remain parallel to wall a set distance away
	 * turns if it receives a bumper event
	 * Called by {@link main(String[])} via thread to be interrupted easily 
	 */
	public void run() {
		DifferentialPilot pilot = (new WheeledRobotSystem(TAYYAB_CONFIG)).getPilot();
		SensorPort.S1.addSensorPortListener(this);
		SensorPort.S4.addSensorPortListener(this);
		Rate rate = new Rate(SLEEP_DURATION);
		UltrasonicSensor proxSensor = new UltrasonicSensor(SensorPort.S2);
		
		pilot.forward();
		
		
		
		while (!isInterrupted()) {
			
			if (shouldTurn) {
				pilot.stop();
				pilot.travel(-0.1);
				
				pilot.rotate(BUMP_ANGLE);
				pilot.forward();
				
				shouldTurn = false;
			}
			if (proxSensor.getDistance() < RANGE) {
				pilot.steer(-COURSE_ADJUST_RATE);
				System.out.println("Turning right");
			} else {
				pilot.steer(COURSE_ADJUST_RATE);
				System.out.println("Turning left");
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
		TouchSensor sensor1 = new TouchSensor(SensorPort.S1);
		TouchSensor sensor2 = new TouchSensor(SensorPort.S4);
		if (sensor1.isPressed() || sensor2.isPressed())  
			shouldTurn = true;
	}

}