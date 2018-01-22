package rp.assignments.group.assm1;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.robotics.DifferentialDriveRobot;
import rp.robotics.TouchSensorEvent;
import rp.systems.ControllerWithTouchSensor;

/**
 * 
 * @author ajb769
 *
 */
public class AJB769Bumper implements ControllerWithTouchSensor {

	private boolean m_run = false;
	private DifferentialDriveRobot robot;
	private DifferentialPilot pilot;

	public AJB769Bumper(DifferentialDriveRobot robot) {
		this.robot = robot;
		this.pilot = this.robot.getDifferentialPilot();

	}

	@Override
	public void run() {
		m_run = true;
		pilot.forward();
		while (m_run) {
		}
	}

	@Override
	public void stop() {
		m_run = false;
		pilot.stop();
	}

	@Override
	public void sensorPressed(TouchSensorEvent _e) {
		pilot.stop();
		pilot.backward();
		Delay.msDelay(10);
		pilot.stop();
		pilot.rotate(180);
		pilot.stop();
		pilot.forward();
	}

	@Override
	public void sensorReleased(TouchSensorEvent _e) {
	}

	@Override
	public void sensorBumped(TouchSensorEvent _e) {
	}
}
