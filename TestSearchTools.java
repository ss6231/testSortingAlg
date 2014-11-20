package testSortingAlg;
import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @author SanaSheikh
 * This main method starts creating arrays of 10,000 elements to 1,000,000 elements and prints the 
 * time it takes to sort using merge sort, quick sort, and selection sort
 *
 */

public class TestSearchTools {
	public static void main(String[] args) {
		
		//start incrementing at 10,000 elements
		int inc = 10000;
		System.out.printf("%5s %20s %18s %20s\n", "N", "Merge Sort", "Quick Sort", "Selection Sort");
		
		//while inc is less than or equal to 1,000,000 keep generating results 
		while (inc <= 1000000) {
			
			//1. MERGE SORT: create initial array and its copy 
			Integer [] mergeList = new Integer [inc];
			Integer [] mergeCopyList = new Integer [inc];
			
			//send array to be instantiated with random integers and copy to mergeCopyList
			randomInt(mergeList, inc);
			System.arraycopy(mergeList, 0, mergeCopyList, 0, mergeCopyList.length);
			
			//start time 
			long startMerge = System.nanoTime ();
			SearchTools.mergeSort(mergeCopyList);
			long endMerge = System.nanoTime ();
			double timeMerge = (double) (endMerge-startMerge)/1000000;
			System.out.printf ("%7d %16.2f", inc, timeMerge);
			
			//2.QUICK SORT: create initial array and its copy 
			Integer [] quickList = new Integer [inc];
			Integer [] quickCopyList = new Integer [inc];
			
			//send array to be instantiated with random integers and copy to quickCopyList
			randomInt(quickList, inc);
			System.arraycopy(quickList, 0, quickCopyList, 0, quickCopyList.length);
			
			//start time 
			long startQuick1 = System.nanoTime();
			SearchTools.quickSort(quickCopyList);
			long endQuick = System.nanoTime();
			double timeQuick = (double) (endQuick-startQuick1)/1000000;
			System.out.printf ("%19.2f", timeQuick);
			
			//only use selection sort if array size is less than or equal to 100,000
			if (inc <= 100000) {
				
				//3. SELECTION SORT: create initial array and its copy 
				Integer [] selectionList = new Integer [inc];
				Integer [] selectionCopyList = new Integer [inc];
				
				//send array to be instantiated with random integers and copy to quickCopyList
				randomInt(selectionList, inc);
				System.arraycopy(selectionList, 0, selectionCopyList, 0, selectionCopyList.length);
				
				//start time 
				long startSelection = System.nanoTime();
				SearchTools.selectionSort(selectionCopyList);
				long endSelection = System.nanoTime();
				double timeSelection = (double) (endSelection-startSelection)/1000000;
				System.out.printf ("%20.2f", timeSelection);
			}
			
			//if size of array is greater than 100,000 incremement by 50,000
			//else incremement by 10,000
			if (inc >= 100000) {
				inc += 50000;
			}

			else {
				inc += 10000;
			}
			System.out.println();
			
			}
		}
	
	/**
	 * This method fills a given array with random integers. The size of the give array will determine
	 * the size of the pool of numbers to choose from
	 * @param list: array to be initialized with random values 
	 * @param size: size of array, which will determine the size of the pool of numbers to choose from 
	 */
	public static void randomInt (Integer [] list, int size) {
		Random randomGenerator = new Random();
		for (int i = 0; i < list.length; i++) {
			int randomInt = randomGenerator.nextInt(size);
			list[i] = randomInt;
		}
	}

}
