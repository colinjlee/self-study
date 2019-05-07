package coding_interview;

import java.util.ArrayList;

public class Ch8_Recursion {
	
	//8.1 Triple Step: Person can either step up 1, 2, or 3 stairs at a time
	//Count the number of ways the person can go up the n stairs
	public static int countStair(int n) {
		if (n == 0) return 1;
		if (n < 0) return 0;
		
		return countStair(n-1) + countStair(n-2) + countStair(n-3);
	}

	//8.1 With memoization
	public static int countStair2(int n) {
		int[] arr = new int[n+1];
		//Fill in array with -1 to distinguish from default 0
		for (int i = 0; i < n+1; i++) {
			arr[i] = -1;
		}
		return countStair2(n, arr);
	}
	
	//8.1 Memoization helper
	public static int countStair2(int n, int[] arr) {
		if (n < 0) return 0;
		else if (n == 0) return 1;
		else if (arr[n] > -1) return arr[n];
		else {
			arr[n] = countStair2(n-1, arr) + countStair2(n-2, arr) + countStair2(n-3,arr);
			return arr[n];
		}
	}
	
	//8.3 Magic Index: Find an index where A[i] = i, if one exists, in a sorted array with distinct ints
	public static int magicIndex(int[] arr) {
		//Linear search
		int length = arr.length;
		
		for (int i = 0; i < length; i++) {
			if (arr[i] == i) return i;
			if (arr[i] > length-1) return -1;
		}
		
		return -1;
	}
	
	//8.3 With recursion and binary search
	public static int magicIndex(int[] arr, int low, int high) {
		int mid = (low + high) / 2;
		if (low > high) return -1;
		
		if (arr[mid] == mid) return mid;
		else if (arr[mid] > mid) return magicIndex(arr, low, mid-1);
		else return magicIndex(arr, mid+1, high);
	}

	//8.5 Recursive Multiply: Multiply two positive ints recursively without using * or /
	public static int recursiveMult(int a, int b) {
		int smaller;
		int ans;
		smaller = (a < b) ? a : b;
		int[] memo = new int[smaller+1];
		ans = (a < b) ? recursiveMult(a, b, a, memo) : recursiveMult(b, a, b, memo);
		
		return ans;
	}
	
	//8.5 Memoization helper
	public static int recursiveMult(int low, int high, int curr, int[] arr) {
		//Add "high" to itself "low" amount of times to reduce operation count
		//Curr is the amount of additions left to do
		if (low == 0) return 0;
		else if (low == 1) return high;
		else if (curr == 0) return arr[low];
		else if (curr == 1) return arr[low-1] + high;
		else if (curr == low) {
			arr[2] = high + high;
			return recursiveMult(low, high, curr-2, arr);
		}
		//Get highest available known sum and add it
		else {
			int diff = low-curr;
			for (int i = 0; i < curr; i++) {
				if (arr[curr-i] > 0) {
					if (i == 0) return arr[diff] + arr[curr];
					else {
						arr[low-i] = arr[diff] + arr[curr-i];
						return recursiveMult(low, high, i, arr);
					}
				}
			}
			arr[diff+2] = arr[diff] + arr[2];
			return recursiveMult(low, high, curr-2, arr);
		}
	}

	//8.7 Permutations without Dups: Compute all permutations of a string with unique chars
	public static ArrayList<String> permNoDups(String s) {
		ArrayList<String> ans = new ArrayList<>();
		if (s == null) return null;
		if (s.length() == 0) {
			ans.add("");
			return ans;
		}
		
		//Recursively get the permutations of the string without the first char
		char first = s.charAt(0);
		String remainder = s.substring(1);
		ArrayList<String> rest = permNoDups(remainder);
		for (String word : rest) {
			for (int i = 0; i <= word.length(); i++) {
				//Insert the first char in every position of the remaining permutations
				String start = word.substring(0, i);
				String end = word.substring(i);
				ans.add(start + first + end);
			}
		}
		return ans;
	}
	
	//8.9 Parens: Print all valid combinations of n pairs of parentheses
	public static void parentheses(int n) {
		if (n <= 0) return;
		else {
			String s = "(";
			parentheses(n, 1, 0, s);
		}
	}
	
	//8.9 Recursive helper
	public static void parentheses(int n, int open, int close, String curr) {
		//Opened n parentheses so rest have to be closed
		if (n == open) {
			System.out.print(curr);
			for (int i = close; i < n; i++) {
				System.out.print(")");
			}
			System.out.println();
			return;
		}
		//Recursively call with 1 more open and close parenthesis if any to properly close
		else {
			parentheses(n, open+1, close, curr+"(");
			if (close < open) parentheses(n, open, close+1, curr+")");
		}
	}
}
