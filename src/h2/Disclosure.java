package h2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Disclosure {
	private static String[][] disjoint = { { "C", "K", "L", "P", "X" },
			{ "B", "D", "N", "R", "V" }, { "G", "I", "M", "S", "W" },
			{ "E", "F", "Q", "T", "Z" } };

	private static String[][] dest = { { "D", "P", "T", "X", "Z" },
			{ "F", "H", "J", "T", "V" }, { "G", "M", "V", "X", "Y" },
			{ "F", "I", "L", "M", "X" }, { "C", "O", "U", "X", "Z" },
			{ "E", "I", "K", "R", "S" }, { "B", "J", "N", "R", "Y" },
			{ "C", "D", "Q", "V", "Z" } };

	private static ArrayList<HashSet<String>> disjointSets = new ArrayList<HashSet<String>>();
	private static ArrayList<HashSet<String>> learningSets = new ArrayList<HashSet<String>>();
	private static ArrayList<HashSet<String>> destSets = new ArrayList<HashSet<String>>();
	private static ArrayList<HashSet<String>> databaseSets = new ArrayList<HashSet<String>>();
	private static HashSet<String> badRecievers = new HashSet<String>();
	private static int counterInstance = 0;
	private static int counterOutput = 0;
	private static final int b = 5; // senders
	private static final int n = 5; // receives
	private static final int N = 26; // total users of system;
	private static final int m = 4; // Number of friends

	public static void main(String[] args) {
		testRandom();
		makeSets();
		learningPhase();
		executionPhase();
	}

	private static void testRandom() { //SPARA I SET ISTÄLLET MED STRING SÅ ÄR SAKEN BIFF
		Random rnd = new Random(1337);
		String[] s = new String[m];
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < m; i++) {
			String line = "";
			char a  = (char) (65 + i);
			line += a;
			set.add(a);
			int j = 0;
			while(j < m) {
				char c = (char) (65 + rnd.nextInt(26-m) + m);
				if(!set.contains(c)) {
					line += c;
					set.add(c);
					j++;
				}
			}
			s[i] = line;
			System.out.println(s[i]);
		}
	}

	private static void makeSets() {
		// DISJOINT
		for (String[] array : disjoint) {
			HashSet<String> set = new HashSet<String>();
			for (String s : array) {
				set.add(s);
			}
			disjointSets.add(set);
		}
		// DESTINATION
		for (String[] array : dest) {
			HashSet<String> set = new HashSet<String>();
			for (String s : array) {
				set.add(s);
			}
			destSets.add(set);
		}
	}

	private static void learningPhase() {
		while (learningSets.size() < m) {
			HashSet<String> A = makeInstance();
			int i = 0;
			for (HashSet<String> B : learningSets) {
				if (isDisjoint(A, B))
					i++;
			}
			if (i == learningSets.size())
				learningSets.add(A);
		}
	}

	private static HashSet<String> makeInstance() {
		counterInstance++;
		return disjointSets.get(counterInstance - 1);
	}

	private static HashSet<String> makeNewOutput() {
		if(counterOutput == 8)
			printArray(databaseSets);
		counterOutput++;
		return destSets.get(counterOutput - 1);
	}

	private static void executionPhase() {
		while (!isDone()) {
			HashSet<String> currentSet = makeNewOutput();
			reduceSet(currentSet);
			databaseSets.add(currentSet);
			if (evaluate(currentSet)) {
				for (int i = 0; i < databaseSets.size(); i++) {
					if (evaluate(databaseSets.get(i))){
						System.out.println("wrap " + i);
						i = -1;
					}
				}
			}
			
//			printArray(databaseSets);
			printArray(learningSets);
			System.out.println();
			
		}
	}

	private static boolean evaluate(HashSet<String> currentSet) {
		int dis = 0;
		int match = 0;
		for (int j = 0; j < learningSets.size(); j++) {
			if (isDisjoint(currentSet, learningSets.get(j)))
				dis++;
			else
				match = j;
		}
		if (dis == learningSets.size() - 1) {
			System.out.println("/////");
			printSet(learningSets.get(match));
			printSet(currentSet);
			if(match == 2 && databaseSets.indexOf(currentSet) == 1)
				printSet(databaseSets.get(0));
			reduceLearningSet(match, currentSet);
			reduceDatabase();
			databaseSets.remove(currentSet);
			printSet(learningSets.get(match));
			System.out.println("------");
			return true;
		}
		return false;
	}

	private static void reduceDatabase() {
		for (HashSet<String> set : databaseSets) {
			set.removeAll(badRecievers);
		}
	}

	private static void reduceSet(HashSet<String> set) {
		set.removeAll(badRecievers);
	}

	private static void reduceLearningSet(int match, HashSet<String> currentSet) {
		System.out.println("a" + databaseSets.indexOf(currentSet) + " d" + match);
		System.out.println();
		badRecievers.addAll(difference(currentSet, learningSets.get(match)));

		intersect(learningSets.get(match), currentSet);
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
			if (set.size() != 1)
				return false;
		}
		return true;
	}

	private static HashSet<String> union(HashSet<String> A, HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		a.addAll(B);
		return a;
	}

	private static HashSet<String> intersect(HashSet<String> A,
			HashSet<String> B) {
		A.retainAll(B);
		return A;
	}

	private static HashSet<String> difference(HashSet<String> A,
			HashSet<String> B) {
		HashSet<String> a = new HashSet<String>(A);
		HashSet<String> u = union(A, B);
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
