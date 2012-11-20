package h2;

public class Disclosure {
	private String[][] disjointSets	= {{"C","K","L","P","X"},
									   {"B","D","N","R","V"},
									   {"G","I","M","S","W"},
									   {"E","F","Q","T","Z"}};
	private String[][] destSets		= {{"D","P","T","X","Z"},
									   {"F","H","J","T","V"},
									   {"G","M","V","X","Y"},
									   {"F","I","L","M","X"},
									   {"C","O","U","X","Z"},
									   {"E","I","K","R","S"},
									   {"B","J","N","R","Y"},
									   {"C","D","Q","V","Z"}};
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
