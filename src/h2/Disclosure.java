package h2;

import java.util.ArrayList;
import java.util.HashMap;

public class Disclosure {
	private String[][] disjointSets = {{"C","K","L","P","X"},
									   {"B","D","N","R","V"},
									   {"G","I","M","S","W"},
									   {"E","F","Q","T","Z"}}; 
	private final int b = 10; //senders
	private final int n = 10; //receives
	private final int N = 100; //total users of system;
	private final int m = 4; // Number of friends
	
	
	public static void main(String[] args) {
		learningPhase();
		executionPhase();
	}


	private static void learningPhase() {
		makeInstance();
	}


	private static void makeInstance() {
		
	}

	
	private static void executionPhase() {
		// TODO Auto-generated method stub
		
	}
}
