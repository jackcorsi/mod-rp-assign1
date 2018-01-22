package rp.assignments.group.assm1;

import lejos.robotics.navigation.DifferentialPilot;
import rp.robotics.DifferentialDriveRobot;
import rp.robotics.TouchSensorEvent;
import rp.robotics.TouchSensorListener;
import rp.systems.ControllerWithTouchSensor;

/**
 * 
 * @author jxc1090
 *
 */

public class JackBumperController implements ControllerWithTouchSensor, TouchSensorListener {
	
	private DifferentialPilot pilot;
	private boolean running = false;
	
	public JackBumperController(DifferentialDriveRobot robot) {
		pilot = robot.getDifferentialPilot();
	}

	@Override
	public void stop() {
		pilot.stop();
		running = false;
	}

	@Override
	public void run() {
		pilot.forward();
		running = true;
		while (running) ;
	}

	@Override
	public void sensorPressed(TouchSensorEvent _e) {
		pilot.stop();
		pilot.travel(-0.1);
		pilot.rotate(180.0);
		pilot.forward();
	}

	@Override
	public void sensorReleased(TouchSensorEvent _e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sensorBumped(TouchSensorEvent _e) {
		// TODO Auto-generated method stub
		
	}
	
}
