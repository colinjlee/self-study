package coding_interview;

import java.util.HashSet;

public class Ch2_LinkedLists extends Node {
	//2.1 Remove Dups: Remove duplicates from an unsorted linked list
	public static void remDups(Node head) {
		HashSet<Integer> set = new HashSet<>();
		Node iter = head;
		Node prev = null;
		
		while (iter.getNext() != null) {
			if (set.contains(iter.getData())) {
				prev.setNext(iter.getNext());
			}
			else {
				set.add(iter.getData());
			}
			prev = iter;
			iter = iter.getNext();
		}
	}
	
	//2.2 Return Kth to Last: Return Kth Element from tail
	public static int remKth(Node head, int k) {
		Node p1 = head;
		Node p2 = head;
		int counter = 1;
		
		//Get to Kth element from head with first pointer
		while (counter < k && p1.getNext() != null) {
			p1 = p1.getNext();
			counter++;
		}
		
		//Move both pointers until first pointer hits tail
		while (p1.getNext() != null) {
			p1 = p1.getNext();
			p2 = p2.getNext();
		}
		return p2.getData();
	}
	
	//2.3 Delete Middle Node: Remove the (not necessarily middle) given node that is not head/tail
	public static void remNode(Node rem) {
		//Not head/tail so will have next node
		rem.setData(rem.getNext().getData());
		rem.setNext(rem.getNext().getNext());
	}
	
	//2.4 Partition: Partition a linked list around given value x so that all elements less than x come before it
	public static void partition(Node head, int x) {
		Node low = null; // Points to end of first "half" of list < x
		Node high = null; // Points to end of second "half" of list >= x
		Node temp = null; //Temporary storage pointer
		Node iter = head;
		
		while (iter != null) {
			int val = iter.getData();
			
			if (val < x) {
				if (low == null) {
					low = iter;
					iter = iter.getNext();
				}
				else { //Adjust pointers to put the new node on the left side
					temp = iter;
					iter = iter.getNext();
					temp.setNext(low.getNext());
					low.setNext(temp);
					high.setNext(iter);
					low = temp;
				}
			}
			else {
				high = iter;
				iter = iter.getNext();
			}
		}
	}
	
	//2.5 Sum Lists: Sum the 2 linked lists representing 2 numbers. Assume reverse order so head is ones digit
	public static Node sumLists1(Node head1, Node head2) {
		Node ans = null;
		Node temp1 = head1;
		Node temp2 = head2;
		int sum = 0;
		int carry = 0;
		
		//Sum up until one number has no more digits
		while (temp1 != null && temp2 != null) {
			sum = temp1.getData() + temp2.getData() + carry;
			carry = sum/10;
			sum = sum%10;
			
			if (ans == null) ans = new Node(sum);
			else ans.append(new Node(sum));
			temp1 = temp1.getNext();
			temp2 = temp2.getNext();
		}
		
		//Finish summing rest of digits from second number
		if (temp1 == null) {
			while (temp2 != null) {
				sum = temp2.getData() + carry;
				carry = sum/10;
				sum = sum%10;
				
				if (ans == null) ans = new Node(sum);
				else ans.append(new Node(sum));
				temp2 = temp2.getNext();
			}
		}
		
		//Finish summing rest of digits from first number
		if (temp2 == null) {
			while (temp1 != null) {
				sum = temp1.getData() + carry;
				carry = sum/10;
				sum = sum%10;
				
				if(ans == null) ans = new Node(sum);
				else ans.append(new Node(sum));
				temp1 = temp1.getNext();
			}
		}

		return ans;
	}

	//2.5 If lists were forward order (normal order)
	public static Node sumLists2(Node head1, Node head2) {
		StringBuilder num1 = new StringBuilder();
		StringBuilder num2 = new StringBuilder();
		Node temp = head1;
		
		//Turn first list of numbers into a string
		while (temp != null) {
			int val = temp.getData();
			temp = temp.getNext();
			num1.append(val);
		}
		
		//Turn second list of numbers into a string
		temp = head2;
		while (temp != null) {
			int val = temp.getData();
			temp = temp.getNext();
			num2.append(val);
		}
		
		//Turn the strings into ints and sum them
		Integer sum = Integer.valueOf(num1.toString()) + Integer.valueOf(num2.toString());
		//Return the sum into a string to put it back into a linked list
		String ssum = sum.toString();
		Node ans = null;
		for (int i = 0; i < ssum.length(); i++) {
			if (i == 0) ans = new Node(Character.getNumericValue(ssum.charAt(i)));
			else ans.append(new Node(Character.getNumericValue(ssum.charAt(i))));
		}
		return ans;
	}
	
	//2.6 Palindrome: Check if the linked list is a palindrome
	public static boolean isPali(Node head) {
		Node rev = null;
		Node temp = head;
		int length = 0;
		
		//Make a new linked list that is the reverse of the given list
		while (temp != null) {
			length++;
			Node temp2 = new Node(temp.getData());
			temp2.setNext(rev);
			rev = temp2;
			temp = temp.getNext();
		}
		
		//Compare half of the lists. If length is odd there is a definitive middle, no need to check
		temp = head;
		for (int i = 0; i < length/2; i++) {
			if (temp.getData() != rev.getData()) return false;
			temp = temp.getNext();
			rev = rev.getNext();
		}
		return true;
	}
	
	//2.7 Intersect: Return the node of intersection between 2 linked lists, by reference not value
	public static Node interNode(Node head1, Node head2) {
		Node temp1 = head1;
		Node temp2 = head2;
		int length1 = 0, length2 = 0;
		
		//Get length of first list
		while(temp1 != null) {
			length1++;
			temp1 = temp1.getNext();
		}
		
		//Get length of second list
		while(temp2 != null) {
			length2++;
			temp2 = temp2.getNext();
		}
		
		//Set pointers to be same distance from tail
		temp1 = head1;
		temp2 = head2;
		if(length1 < length2) {
			for(int i = 0; i < length2-length1; i++) {
				temp2 = temp2.getNext();
			}
		}
		else {
			for(int i = 0; i < length1-length2; i++) {
				temp1 = temp1.getNext();
			}
		}
		
		//Traverse and look for intersecting node
		while(temp1 != null && temp2 != null) {
			if(temp1 == temp2) return temp1;
			temp1 = temp1.getNext();
			temp2 = temp2.getNext();
		}
		return null;
	}
	
	//2.8 Loop Detection: Given circular linked list, return the node at the beginning of the loop
	public static Node loopDetect(Node head) {
		//Uses a set to see if node was already traversed
		HashSet<Node> set = new HashSet<>();
		Node temp = head;
		
		//While loop will terminate whether or not the list contains a loop
		while(temp != null) {
			if(set.contains(temp)) return temp;
			else set.add(temp);
			temp = temp.getNext();
		}
		return null;
	}
}
