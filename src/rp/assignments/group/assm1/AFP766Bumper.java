package rp.assignments.group.assm1;

import rp.robotics.DifferentialDriveRobot;
import rp.robotics.TouchSensorEvent;
import rp.systems.ControllerWithTouchSensor;

public class AFP766Bumper implements ControllerWithTouchSensor {

	DifferentialDriveRobot robot;
	private boolean isPressed = false;
	private boolean m_run = true;

	public AFP766Bumper(DifferentialDriveRobot robot, boolean m_run) {
		this.robot = robot;
		this.m_run = m_run;
	}

	@Override
	public void stop() {
		m_run = false;
	}

	@Override
	public void run() {
		m_run = true;
		while (m_run) {
			while (isPressed == false
					&& !robot.getDifferentialPilot().isMoving()) {
				robot.getDifferentialPilot().travel(4.0f);
			}
		}
	}

	@Override
	public void sensorPressed(TouchSensorEvent event) {
		if (m_run){
			isPressed = true;
			robot.getDifferentialPilot().stop();
			robot.getDifferentialPilot().travel(-0.2);
			robot.getDifferentialPilot().rotate(180);
			isPressed = false;
			run();
		}
	}

	@Override
	public void sensorReleased(TouchSensorEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sensorBumped(TouchSensorEvent event) {
		// TODO Auto-generated method stub
	}

}
