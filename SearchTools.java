package testSortingAlg;
import java.lang.reflect.Array;

/**
 * 
 * @author SanaSheikh
 * This class keeps all the wrapper search methods that will be called from the TestSearchTools benchmark
 * and it also keeps the private recursive search methods. Everything is static because they do not depend
 * on an instance of this class. 
 *
 */

public class SearchTools {	
	//We will not be instantiating an instance of SearchTools, so this will be private 
	private SearchTools () {
	}
	
	/**
	 * @param list: this is the array to be sorted
	 * This wrapper method calls the private recMergeSort, which takes the array, first index, and last index
	 * as parameters 
	 */
	public static <E extends Comparable <E>> void mergeSort(E [] list) {
		recMergeSort (list, 0, list.length-1);
	}
	
	/**
	 * @param list: array to be sorted
	 * This wrapper method calls the private recQuickSort, which takes the array, first index, and last index
	 * as parameters 
	 */
	public static <E extends Comparable <E>> void quickSort(E[] list) {
		recQuickSort (list, 0, list.length-1);
	}
	
	/**
	 * @param list: array to be sorted
	 * This method looks for the largest element in the list, puts it at the end of the array, and then finds the
	 * next largest and swaps it with the second to last element, etc. 
	 */
	public static <E extends Comparable <E>> void selectionSort(E [] list) {
		for (int i = 0; i < list.length-1; i++) {
			int bigIndex = 0;
			for (int j = 1; j < list.length-i; j++) {
				if (list[j].compareTo(list[bigIndex]) > 0) {
					bigIndex = j;	
				}
			}
			swap (list, bigIndex, list.length-i-1);
			
		}
	}
	/**
	 * @param list: array to be sorted
	 * @param firstIndex: first index of left subarray 
	 * @param rightIndex: first index of right subarray 
	 * This method is designed to recursively "split" the parameter list by its midpoint. if firstIndex and 
	 * right index are the same, then it cannot be split further. Else, continue splitting and then merge 
	 */
	private static <E extends Comparable <E>> void recMergeSort (E[] list, int firstIndex, int rightIndex) {
		
		//base case: the size of the subarray is 1 
		if (firstIndex == rightIndex) {
			return;
		}
		
		//improvement: call insertion sort when the total size subarrays in question is less than or equal
		//to 50 and then return back to previous method 
		if (rightIndex-firstIndex <= 50) {
			insertionSort (list, firstIndex, rightIndex);
			return;
		}
		
		//divide two subarrays by the mid point, split them recursively and then merge them 
		int mid = (firstIndex + rightIndex)/2;
		recMergeSort (list, firstIndex, mid);
		recMergeSort (list, mid+1, rightIndex);
		merge (list, firstIndex, mid, mid+1, rightIndex);
	}
	
	/**
	 * @param list: array being sorted
	 * @param leftFirst: first element of left subarray
	 * @param leftLast: last element of left subarray
	 * @param rightFirst: first element of right subarray
	 * @param rightLast: last element of right subarray 
	 * This method compares elements in each left and right subarray and places the smaller of the two in a 
	 * temporary array. Once all the elements have been placed, the sorted temporary array is copied back to the 
	 * original list 
	 */
	private static <E extends Comparable <E>> void merge (E [] list, int leftFirst,
			int leftLast, int rightFirst, int rightLast) {

		//improvement: if the last element of the left subarray is less than the first element of the right index
		//then they are already sorted and you do not need to waste time merging them into a sorted array 
		if (list[leftLast].compareTo (list[rightFirst]) <= 0) {
			return;
		}
		
		//create temp array and index variables 
		E [] temp = (E[]) Array.newInstance (list.getClass().getComponentType(), rightLast-leftFirst+1);
		int indexLeft = leftFirst;
		int indexRight = rightFirst;
		int index = 0;
		
		//while we have not fully traversed the left or right subarray then compare them to one antoher
		while (indexLeft <= leftLast && indexRight <= rightLast) {
			//if left > right, then put left in temp
			if (list[indexLeft].compareTo(list[indexRight]) < 0) {
				temp[index] = list[indexLeft];
				indexLeft++;
			}
			//then right>left or they are equal, then put right in temp
			else {
				temp[index] = list[indexRight];
				indexRight++;
			}
			index++;
		}
		//when left side has remaining elements, copy them to temp
		while (indexLeft <= leftLast) {
			temp[index] = list[indexLeft];
			indexLeft++;
			index++;
		}
		//when right side has remaining elements, copy them to temp
		while (indexRight <= rightLast) {
			temp [index] = list[indexRight];
			index++;
			indexRight++;
		}
		//now copy temp elements to original array starting at leftFirst 
		for (int i = 0; i < temp.length; i++) {
			list[leftFirst] = temp[i];
			leftFirst++;
		}
	}
	
	/**
	 * @param A: list to be sorted
	 * @param start: starting index of the array
	 * @param end: ending index of the array
	 * This method looks at the current element and compares it to a sorted subarray. It places current 
	 * in its proper sorted position as compared to the subarray 
	 */
	private static <E extends Comparable <E>>void insertionSort (E [] A, int start, int end) {
		for (int i = start; i <= end; i++) {
			E current = A[i];
			int k;
			for (k = i-1; k >= start && A[k].compareTo(current) > 0; k--) {
				//shift the elements to the right so long as current is less than A[k]
				A[k+1] = A[k];
			}
			//insert current in the sublist
			A[k+1] = current; 
		}
	}
	/**
	 * 
	 * @param list: array to be sorted
	 * @param left: first index of the left subarray
	 * @param right: last index of the right subarray 
	 */
	private static <E extends Comparable<E>> void recQuickSort (E [] list, int left, int right) {
		//when left exceeds right, you have traversed the list so return to previous method 
		if (left >= right) {
			return;
		}
		
		//improvement: call insertion sort when the size is less than or equal to 50
		if (right-left <= 50) {
			insertionSort (list, left, right);
			return;
		}
		//else, call the partition method to determine where the split of the left and right subarray
		//should occur
		else {
			int pivotIndex = partition (list, left, right);
			recQuickSort (list, left, pivotIndex-1);
			recQuickSort (list, pivotIndex+1, right);
		}
	}
	
	/**
	 * 
	 * @param list: array to be sorted
	 * @param leftIndex: first index of the list
	 * @param rightIndex: last index of the list 
	 * @return: return the final index position of the pivot. This will indicate where the splitting of the
	 * left and right subarray should occur 
	 */
	private static <E extends Comparable <E>> int partition (E [] list, int leftIndex, int rightIndex) {
		
		//find midpoint and swap it with the rightmost index
		int midIndex = (leftIndex+rightIndex)/2;
		E pivot = list[midIndex];
		swap (list, midIndex, rightIndex);
		
		//set new pivotIndex, left index, and right index to be compared to pivot 
		int pivotIndex = rightIndex;
		int left = leftIndex;
		int right = rightIndex-1;
		
		//while the list has not fully traversed, continue comparing
		while (left <= right) {
			
			//incremement while list[left] is smaller than pivot
			while (list[left].compareTo (pivot) < 0) {
				left++;
			}
			//incrememnt while list[right] is greater than pivot AND while the list has not fully been
			//traversed
			while (right >= left && list[right].compareTo (pivot) >= 0) {
				right--;
			}
			//swap if the elements are out of places 
			if (right > left) {
				swap (list, right, left);
				left++;
				right--;
			}
		}
		//now that we have traversed the list, swap pivot with its final sorted position in the list
		swap (list, left, pivotIndex);
		//left is the final position that will determine where splitting of left and right subarrays will
		//occur
		return left;
	}
	
	/**
	 * 
	 * @param list: array that contains the indexes 
	 * @param firstIndex
	 * @param rightIndex
	 * This method swaps the elements at the firstIndex and rightIndex. 
	 */
	private static <E extends Comparable<E>> void swap (E [] list, int firstIndex, int rightIndex) {
		E temp = list[rightIndex];
		list[rightIndex] = list[firstIndex];
		list[firstIndex] = temp;
	}

}
