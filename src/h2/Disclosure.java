package h2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Disclosure {
	private static ArrayList<HashSet<String>> learningSets = new ArrayList<HashSet<String>>();
	private static ArrayList<HashSet<String>> databaseSets = new ArrayList<HashSet<String>>();
	private static HashSet<String> friends = new HashSet<String>();
	private static HashSet<String> badRecievers = new HashSet<String>();
	private static HashSet<String> used = new HashSet<String>();
	private static Random rnd = new Random(1337);
	private static int counterNumberNeededOutput = 0;
	
	private static final int n = 5;		// Receivers
	private static final int N = 26;	// Total users of system;
	private static final int m = 4;		// Number of friends

	public static void main(String[] args) {
		learningPhase();
		executionPhase();
		printResult();
	}

	private static HashSet<String> makeRandomInstance(int offset) {
		HashSet<String> set = new HashSet<String>();
		char a = (char) (65 + offset);
		friends.add(""+a);
		set.add("" + a);
		int j = 0;
		while (j < n-1) {
			char c = (char) (65 + rnd.nextInt(N - m) + m);
			if (!used.contains(c)) {
				set.add("" + c);
				used.add("" + c);
				j++;
			}
		}
		return set;
	}
	
	private static HashSet<String> makeRandomOutput() {
		HashSet<String> set = new HashSet<String>();
		char a = (char) (65 + rnd.nextInt(m));
		set.add("" + a);
		int j = 0;
		while (j < n-1) {
			char c = (char) (65 + rnd.nextInt(N - m) + m);
			set.add("" + c);
			j++;
		}
		return set;
	}

	private static void learningPhase() {
		while (learningSets.size() < m) {
			HashSet<String> A = makeRandomInstance(learningSets.size());
			int i = 0;
			for (HashSet<String> B : learningSets) {
				if (isDisjoint(A, B))
					i++;
			}
			if (i == learningSets.size())
				learningSets.add(A);
		}
	}

	private static void executionPhase() {
		while (!isDone()) {
			counterNumberNeededOutput++;
			HashSet<String> currentSet = makeRandomOutput();
			reduceSet(currentSet);
			databaseSets.add(currentSet);
			if (evaluate(currentSet)) 
				for (int i = 0; i < databaseSets.size(); i++) 
					if (evaluate(databaseSets.get(i)))
						i = -1;
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
			reduceLearningSet(match, currentSet);
			reduceDatabase();
			databaseSets.remove(currentSet);
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
		badRecievers.addAll(difference(currentSet, learningSets.get(match)));
		intersect(learningSets.get(match), currentSet);
	}

	private static void printSet(HashSet<String> set) {
		for (String string : set) {
			System.out.print(string + " ");
		}
		System.out.println();
	}

	private static void printResult() {
		HashSet<String> finalSet = new HashSet<String>();
		for (HashSet<String> set : learningSets)
			finalSet.addAll(set);
		
		System.out.println("Real friends");
		printSet(friends);
		System.out.println("\nCalculated friends");
		printSet(finalSet);
		System.out.println("\n" + (finalSet.equals(friends) ? "Attack Successful" : "Attack Failed"));
		System.out.println("\nOutput sets needed " + counterNumberNeededOutput);
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

	private static HashSet<String> intersect(HashSet<String> A, HashSet<String> B) {
		A.retainAll(B);
		return A;
	}

	private static HashSet<String> difference(HashSet<String> A, HashSet<String> B) {
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
