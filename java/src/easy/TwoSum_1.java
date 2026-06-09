package easy;

import java.util.Hashtable;

public class TwoSum_1 {
    public int[] twoSumSolution1(int[] nums, int target) {
        Hashtable<Integer, Integer> numIndexTable = new Hashtable<>();

        //let complement of a number = target - the number (or a number you need to add to a given number to reach the target)
        //Idea:
        //Store the numbers and it's index as key value pairs (num -> index)
        //Loop through nums:
        //If the current index number's complement exists in the table, return the indices
        //Otherwise, add the current number and its index to the table as it might be the complement of some other number

        for(int i = 0; i < nums.length; i++){ //O(n) average case
            int complement = target - nums[i];

            if(numIndexTable.containsKey(complement)){ //O(1) average case
                return new int[]{numIndexTable.get(complement), i};
            }
            else {
                numIndexTable.put(nums[i], i);
            }
        }
        return null;

        //COMPLEXITY: O(n) average case, O(n^2) worst case
    }
}
