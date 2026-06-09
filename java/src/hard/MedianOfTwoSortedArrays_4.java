package hard;

public class MedianOfTwoSortedArrays_4 {
    public double medianOfTwoSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        if ((m + n) % 2 != 0) { //if there are odd number
            int medianInd = (m + n) / 2;
            return getValueInMergedArray(nums1, nums2, medianInd);
        }
        else {
            int mid1 = (m + n) / 2;
            int mid2 = mid1 - 1;

            return (double) (getValueInMergedArray(nums1, nums2, mid1) + getValueInMergedArray(nums1, nums2, mid2)) / 2;
        }
    }

    private int getValueInMergedArray(int[] nums1, int[] nums2, int indexInMergedArr) { //O(log(nums1.length) x log(nums2.length))
        int nums1Left = 0, nums1Right = nums1.length - 1;

        while (nums1Left <= nums1Right) { //O(log(nums1.length))
            int nums1Mid = (nums1Left + nums1Right)/2;

            int midValMergedIndex = getIndexInMergedArray(nums1, nums2, nums1Mid); //find the position of a value in nums1 in the merged array

            if (midValMergedIndex == indexInMergedArr) {
                return nums1[nums1Mid]; //found the value in nums1 which exists at the required index in the merged array
            }
            else if (midValMergedIndex < indexInMergedArr) {
                nums1Left = nums1Mid + 1;
                //if the current mid value has less elements than the required index before it,
                //then the required index value occurs after the mid val. so check the right half.
            }
            else {//check the left half if the current mid val has too many elements before it in the merged array
                nums1Right = nums1Mid - 1;
            }
        }
        //if the loop terminates without returning, then all values in nums1 until the current left pointer occurs before the reqd index in the merged arr
        //so this means the reqd val exists in nums2 and not nums1. so we find the remaining number of positions until the reqd index
        //and get the value which has that many numbers before it in nums2
        return nums2[indexInMergedArr - nums1Left];
    }

    private int getIndexInMergedArray(int[] nums1, int[] nums2, int indexInNums1) {//COMPLEXITY: O(log(nums2.length))
        //For a number at position indexInNums1, there will be at least that many numbers before it in the merged array, coz the array is sorted
        //From Nums2, the no. of numbers before it will be the position where the number will be inserted (or the first occurence) in nums2
        //the final index of the number will be the number of nos. before it in nums1 + no. of numbers before it in nums2
        int nums2Contribution = findInsertPos(nums2, nums1[indexInNums1]);

        return indexInNums1 + nums2Contribution;
    }

    private int findInsertPos(int[] array, int val) { //COMPLEXITY: O(log(nums2.length))
        int left = 0, right = array.length - 1;

        while (left <= right) {
            int mid = (left + right)/2;

            if (array[mid] == val) {
                if (mid > 0 && array[mid-1] == val) { //look for the first index where val occurs in the array
                    right = mid-1;
                }
                else {
                    return mid; //return the index of the first occurrence if the element already exists in the array
                }
            }

            else if (val < array[mid]) {
                right = mid - 1; //check the left half
            }
            else {
                left = mid + 1; //check the right half
            }
        }
        //if the val was not found in the array, and the while loop completed without returning,
        //then the current left value will be the index of the val in this array
        //left tracks how many elements are smaller than val. so left index will be the index of the val
        return left;
    }
}
