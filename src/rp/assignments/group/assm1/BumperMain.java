package rp.assignments.group.assm1;

import lejos.nxt.Button;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.DifferentialDriveRobot;
import rp.systems.ControllerWithTouchSensor;
import rp.util.Rate;

public class BumperMain {
	
	//Tayyab's physical configuration
	private static final WheeledRobotConfiguration config =  RobotConfigs.EXPRESS_BOT;
	

	public static void main(String[] args) {
		Button.waitForAnyPress();
		DifferentialDriveRobot robot = new DifferentialDriveRobot(config);
		ControllerWithTouchSensor controller;
		ControllerTest testSlave;
		Rate rate = new Rate(2000);
		
		//AJB769
		controller = new AJB769Bumper(robot);
		testSlave = new ControllerTest(controller);
		testSlave.start();
		rate.sleep();
		testSlave.interrupt();
	}

}
