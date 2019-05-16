package coding_interview;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ch1_ArraysAndStrings {
	
	//Sort the chars of a string in alphabetical order
	private static String sortString(String s) {
		char[] arr = s.toCharArray();
		Arrays.sort(arr);
		return new String(arr);
	}
	
	//1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters.
	public static boolean isUnique1(String s) {
		int length = s.length();
		Set<Character> set = new HashSet<Character>();
		
		//Use a set to check for char uniqueness
		for (int i = 0; i < length; i++) {
			char next = s.charAt(i);
			if (set.contains(next)) return false;
			set.add(next);
		}
		return true;
	}
	
	//1.1 without using additional data structure
	public static boolean isUnique2(String s) {
		//O(n*logn) runtime by sorting the string then checking for uniqueness of chars
		String sNew = sortString(s);
		for (int i = 0; i < s.length() - 1; i++) {
			if (sNew.charAt(i) == sNew.charAt(i+1)) return false;
		}
		return true;
		
		/* O(n^2) runtime by checking a char with all future chars
		int length = s.length();
		for (int i = 0; i < length; i++) {
			char curr = s.charAt(i);
			
			for (int j = i; j < length; j++) {
				if (curr == s.charAt(j)) return false;
			}
		}
		return true; */
	}

	//1.2 Check Permutation: Given two strings, check if one is a permutation of the other
	public static boolean isPermutation(String s1, String s2) {
		//Sort the two strings then compare them directly
		if (s1.length() != s2.length()) return false;
		
		String s1New = sortString(s1);
		String s2New = sortString(s2);
		
		for (int i = 0; i < s1.length(); i++) {
			if (s1New.charAt(i) != s2New.charAt(i)) return false;
		}
		return true;
	}
	
	//1.3 Replace all spaces with '%20' given a char[] and its "true" length (in place)
	//Assume it has enough space to hold the new chars
	public static void urlify(char[] arr, int length) {
		int i = 0;
		//Traverse the "string"
		while (i < length) {
			//If a space is found, move chars after the space 2 spots to the right
			//Insert '%20' and adjust index and true length
			if (arr[i] == ' ') {
				for (int j = length + 1; j > i + 2; j--) {
					arr[j] = arr[j-2];
				}
				arr[i] = '%';
				arr[i+1] = '2';
				arr[i+2] = '0';
				i = i + 3;
				length = length + 2;
			}
			else i++;
		}
	}
	
	//1.4 Palindrome Permutation: Given a string, check if it is a permutation of a palindrome
	public static boolean palinPerm(String s) {
		s = s.toLowerCase(); //Make all chars lowercase
		s = s.replaceAll("\\s+", ""); //Remove spaces
		
		int counter = 0;
		int length = s.length();
		boolean isEven = length % 2 == 0;
		
		if (length == 1 || length == 0) return true; //Special case
		char[] arr = s.toCharArray();
		Arrays.sort(arr);
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] != arr[i+1]) counter++;
			else i++;
		}
		
		//Even length palindromes have no odd occurring letter
		//Odd length palindromes have exactly one letter occurring odd number of times
		return isEven ? counter == 0 : counter == 1;
	}
	
	//1.5 One Away: Given two strings check if they are one or zero edits away
	public static boolean oneAway(String s1, String s2) {
		int length1 = s1.length();
		int length2 = s2.length();
		if (Math.abs(length1 - length2) > 1) return false;
		
		String shorter = s2;
		String longer = s1;
		boolean oneOff = false;
		boolean diffLength = length1 != length2;
		if (length1 < length2) {
			shorter = s1;
			longer = s2;
		}
		
		//Compare strings side by side and toggle oneOff if chars don't match
		//If chars didn't match, it must match next char or else it is more than one edit away
		for (int i = 0; i < shorter.length(); i++) {
			int j = i;
			if (oneOff && diffLength) j = i+1;
			if (shorter.charAt(i) != longer.charAt(j)) {
				if (oneOff) return false; //More than one edit away
				if (diffLength && shorter.charAt(i) != longer.charAt(j+1)) return false;
				oneOff = true;
			}
		}
		return true;
	}
	
	//1.6 String Compression: Given a string of (A-Z, a-z) 
	//Compress it by replacing consecutive chars with a single char and the number of occurrence
	public static String strCompress(String s) {
		//More efficient implementation uses StringBuilder
		/* StringBuilder compressed = new StringBuilder();
		 * compressed.append(); compressed.toString(); compressed.length(); */
		int length = s.length();
		String ans = "";
		int counter = 1;
		
		if (length == 0) return ans; //Empty string
		
		ans = ans + s.charAt(0);
		for (int i = 0; i < length-1; i++) {
			if (s.charAt(i) == s.charAt(i+1)) {
				counter++;
			}
			else {
				ans = ans + Integer.toString(counter);
				ans = ans + s.charAt(i+1);
				counter = 1;
			}
		}
		ans = ans + Integer.toString(counter);
		//If the string actually compressed return ans, else return original string
		return (ans.length() < length) ? ans : s;
	}
	
	//1.7 Rotate Matrix: Given nxn matrix, rotate it 90 degrees clockwise.
	public static int[][] rotateMat1(int[][] mat) {
		int length = mat.length; //Square matrix, rows = cols
		int[][] newMat = new int[length][length];
		
		//Starts filling in the new matrix from the rightmost column
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < length; col++) {
				newMat[length-1-col][length-1-row] = mat[row][col];
			}
		}
		return newMat;
	}
	
	//1.7 without new matrix
	public static void rotateMat2(int[][] mat) {
		int length = mat.length;
		if (length <= 1) return;
		
		int temp;
		for (int row = 0; row < length; row++) {
			//rotate 
			for (int col = row; col < length-row-1; col++) {
				temp = mat[row][col];
				mat[row][col] = mat[length-col-1][row];
				mat[length-col-1][row] = mat[length-row-1][length-col-1];
				mat[length-row-1][length-col-1] = mat[col][length-row-1];
				mat[col][length-row-1] = temp;
			}
		}
	}
	
	//1.8 Zero Matrix: If an element is 0, set its entire row and column to 0s
	public static void zeroMat(int[][] mat) {
		//Could use first row and col as storage, first checking if those themselves have 0s
		int numRows = mat.length;
		int numCols = mat[0].length;
		Set<Integer> indexRows = new HashSet<>();
		Set<Integer> indexCols = new HashSet<>();
		
		//Get indices where matrix is 0
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				if(mat[row][col] == 0) {
					indexRows.add(row);
					indexCols.add(col);
				}
			}
		}
		
		//Go through rows and columns to set to 0
		for(int row : indexRows) {
			for(int col = 0; col < numCols; col++) {
				mat[row][col] = 0;
			}
		}
		
		for(int col : indexCols) {
			for(int row = 0; row < numRows; row++) {
				mat[row][col] = 0;
			}
		}
	}
}
