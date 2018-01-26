package rp.assignments.group.assm1;

import lejos.nxt.Button;

/**
 * @author jxc1090
 * @author ajb769
 * @author afp766
 * 
 * part 1 of Exercise 1
 */
public class HelloWorld {
	
	/**
	 * prints "Hello World" and waits for any button press
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello, world!");
		Button.waitForAnyPress();
	}

}
