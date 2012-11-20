package h2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Disclosure {
	private static String[][] disjoint	= {{"C","K","L","P","X"},
										   {"B","D","N","R","V"},
										   {"G","I","M","S","W"},
										   {"E","F","Q","T","Z"}};
	
	private static String[][] dest		= {{"D","P","T","X","Z"},
										   {"F","H","J","T","V"},
										   {"G","M","V","X","Y"},
										   {"F","I","L","M","X"},
										   {"C","O","U","X","Z"},
										   {"E","I","K","R","S"},
										   {"B","J","N","R","Y"},
										   {"C","D","Q","V","Z"}};

	private static ArrayList<HashSet<String>> disjointSets;
	private static ArrayList<HashSet<String>> outputSets;
	
	private final int b = 10; //senders
	private final int n = 10; //receives
	private final int N = 100; //total users of system;
	private final int m = 4; // Number of friends
	
	public static void main(String[] args) {
		makeSets();
		learningPhase();
		executionPhase();
	}


	private static void makeSets() {
		for (String[] s : disjoint) {
			HashSet<String> set = new HashSet<String>();
		}
		
	}


	private static void learningPhase() {
		makeInstance();
	}


	private static HashSet<String> makeInstance() {
		return null;
	}

	
	private static void executionPhase() {
		
	}
	
	private static boolean isDone() {
		
		return false;
	}
	
	private static HashSet<String> union(HashSet<String> A, HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		a.addAll(B);
		return a;
	}
	
	private static HashSet<String> intersect(HashSet<String> A, HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		a.retainAll(B);
		return a;
	}
	
	private static HashSet<String> difference(HashSet<String> A, HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		a.removeAll(B);
		return a;
	}
	
	private static boolean isDisjoint(HashSet<String> A, HashSet<String> B) {
		return intersect(A,B).size() == 0;
	}
}
