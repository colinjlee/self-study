package coding_interview;

public class Ch10_Sorting {
	
	//Binary search assuming array is already sorted
	public static int binSearch(int[]arr, int x) {
		int low = 0;
		int high = arr.length - 1;
		int mid = (low + high) / 2;
		
		while (low <= high) {
			if (x == arr[mid]) return mid;
			else if (x < arr[mid]) high = mid - 1;
			else low = mid + 1;
			mid = (low + high) / 2;
		}
		return -1;
	}
	
	//Recursive binary search
	public static int binSearch(int[]arr, int low, int high, int x) {
		if (low > high) return -1;
		else {
			int mid = (low + high) / 2;
			if (x == arr[mid]) return mid;
			else if (x < arr[mid]) return binSearch(arr, low, mid-1, x);
			else return binSearch(arr, mid+1, high, x);
		}
	}
	
	//10.1 Sorted Merge: Given two sorted arrays where the first has enough space,
	//Merge the two arrays into the first in sorted order
	public static void sortedMerge(int[] arr1, int[] arr2, int length1, int length2) {
		//Indices to last non-empty element
		int index1 = length1 - 1;
		int index2 = length2 - 1;
		
		//Sort starting from the back
		for (int i = length1 + length2 - 1; i >= 0; i--) {
			if (index1 == -1) {
				arr1[i] = arr2[index2];
				index2--;
			}
			else if (index2 == -1) {
				arr1[i] = arr1[index1];
				index1--;
			}
			else {
				if (arr1[index1] >= arr2[index2]) {
					arr1[i] = arr1[index1];
					index1--;
				}
				else {
					arr1[i] = arr2[index2];
					index2--;
				}
			}
		}
	}

	//10.3 Search in Rotated Array: Given a previously sorted array that was rotated
	//Find an element in the array
	public static int searchRot(int[] arr, int x) {
		int low = 0;
		int high = arr.length - 1;
		
		while(low <= high) {
			int mid = (low + high) / 2;
			if (x == arr[mid]) return mid;
			
			//Left side is in increasing order
			if(arr[low] < arr[mid]) {
				//x is in left side, find using binary search
				if (x < arr[mid] && x >= arr[low]) return binSearch(arr, low, mid-1, x);
				//x is in right side, which is not sorted
				else low = mid + 1;
			}
			//Right side is in increasing order
			else {
				//x is in right side, find using binary search
				if (x > arr[mid] && x <= arr[high]) return binSearch(arr, mid+1, high, x);
				//x is in left side, which is not sorted
				else high = mid - 1;
			}
		}
		return -1;
	}
	
	//10.5 Sparse Search: Given sorted array of strings interspersed with empty strings
	//Find the location of given string
	public static int sparseSearch(String[] arr, String x) {
		int low = 0;
		int high = arr.length - 1;
		int mid = (low + high) / 2;
		int midL, midR;
		
		//Look for a non-empty string starting from mid then do binary search
		while (low <= high) {
			midL = mid - 1;
			midR = mid + 1;
			while (arr[mid].isEmpty()) {
				if (!arr[midL].isEmpty() && midL >= low) mid = midL;
				else if (!arr[midR].isEmpty() && midR <= high) mid = midR;
				else {
					if (midL < low && midR > high) return -1;
					midL--;
					midR++;
				}
			}
			if (x.equals(arr[mid])) return mid;
			else if (x.compareTo(arr[mid]) < 0) high = mid - 1;
			else low = mid + 1;
			mid = (low + high) / 2;
		}
		return -1;
	}
	
	//10.9 Sorted Matrix Search: Given MxN matrix where each row and col is sorted
	//Returns array of size 2 acting as a pair for coordinates
	public static int[] searchMat(int[][] mat, int x) {
		//Start from last row and first col
		int[] ans = {-1, -1};
		int row = mat.length - 1;
		int col = 0;
		
		//Tighten the range where x could be
		//x needs to be less than or equal to mat[r][c] to be in row r, similar for col
		while (row >= 0 && col < mat.length) {
			if (x == mat[row][col]) {
				ans[0] = row;
				ans[1] = col;
				return ans;
			}
			else if (x < mat[row][col]) row--;
			else col++;
		}
		return ans;
	}
}
