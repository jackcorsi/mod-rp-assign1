package rp.assignments.group.assm1;

import rp.systems.ControllerWithTouchSensor;

class ControllerTest extends Thread {
	
	private ControllerWithTouchSensor controller;
	
	public ControllerTest(ControllerWithTouchSensor controller) {
		this.controller = controller;
	}
	
	@Override 
	public void run() {
		Thread thread = new Thread(controller);
		thread.start();
		while (!Thread.interrupted()) ;
		controller.stop();
	}
	
}