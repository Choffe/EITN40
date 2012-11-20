package h2;

import java.util.ArrayList;
import java.util.HashSet;

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

	private static ArrayList<HashSet<String>> disjointSets = new ArrayList<HashSet<String>>();
	private static ArrayList<HashSet<String>> learningSets = new ArrayList<HashSet<String>>();
	private static ArrayList<HashSet<String>> destSets = new ArrayList<HashSet<String>>();
	private static ArrayList<HashSet<String>> databaseSets = new ArrayList<HashSet<String>>();
	private static int counterInstance = 0;
	private static int counterOutput = 0;
	private static final int b = 5; //senders
	private static final int n = 5; //receives
	private static final int N = 26; //total users of system;
	private static final int m = 4; // Number of friends
	
	public static void main(String[] args) {
		makeSets();
		learningPhase();
		executionPhase();
	}


	private static void makeSets() {
		//DISJOINT
		for (String[] array : disjoint) {
			HashSet<String> set = new HashSet<String>();
			for (String s : array) {
				set.add(s);
			}
			disjointSets.add(set);
		}
		//DESTINATION
		for (String[] array : dest) {
			HashSet<String> set = new HashSet<String>();
			for (String s : array) {
				set.add(s);
			}
			destSets.add(set);
		}
	}


	private static void learningPhase() {
		while(learningSets.size() < m) {
			HashSet<String> A = makeInstance();
			int i = 0;
			for (HashSet<String> B : learningSets) {
				if(isDisjoint(A,B)) i++;
			}
			if(i == learningSets.size())
				learningSets.add(A);
		}
	}


	private static HashSet<String> makeInstance() {
		counterInstance++;
		return disjointSets.get(counterInstance - 1);
	}

	
	private static HashSet<String> makeNewOutput() {
		counterOutput++;
		return destSets.get(counterOutput - 1);
	}
	
	
	private static void executionPhase() {
		int dis = 0;
		int match = 0;
		while(!isDone()) {
			HashSet<String> currentSet = null;
			while(dis < learningSets.size() - 1) {
				dis = 0;
				match = -1;
				currentSet = makeNewOutput();
				
				for (int j = 0; j < learningSets.size(); j++) {
					if(isDisjoint(currentSet,learningSets.get(j)))
						dis++;
					else {
						match = j;
					}
				}
			}
			if(dis == learningSets.size() - 1) {
				reduceLearningSet(match,currentSet); //ELSE
			}
		}
	}
	
	private static void reduceLearningSet(int match, HashSet<String> currentSet) {
		System.out.println("match " + match);
		HashSet<String> badRecievers = difference(currentSet, learningSets.get(match));
		intersect(learningSets.get(match),currentSet);
		
		printSet(badRecievers);

		for (HashSet<String> set : learningSets) {
			intersect(set,badRecievers);
		}
		
	}

	private static void printSet(HashSet<String> set) {
		for (String string : set) {
			System.out.print(string + " ");
		}
		System.out.println();
	}
	
	private static void printArray(ArrayList<HashSet<String>> array) {
		for (HashSet<String> set : array) {
			for (String string : set) {
				System.out.print(string);
			}
			System.out.println();
		}
	}
	
	private static boolean isDone() {
		for (HashSet<String> set : learningSets) {
			if(set.size() != 1)	return false;
		}
		return true;
	}
	
	private static HashSet<String> union(HashSet<String> A, HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		a.addAll(B);
		return a;
	}
	
	private static HashSet<String> intersect(HashSet<String> A, HashSet<String> B) {
		A.retainAll(B);
		return A;
	}
	
	private static HashSet<String> difference(HashSet<String> A, HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		HashSet<String> u = union(A,B);
		a.retainAll(B);
		u.removeAll(a);
		return u;
	}
	
	private static boolean isDisjoint(HashSet<String> A, HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		a.retainAll(B);
		return a.size() == 0;
	}
}
