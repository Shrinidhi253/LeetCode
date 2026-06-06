package medium;

public class ContainerWithMostWater_11 {
    /*
    Idea: Check every combination of right and left x
    Get the min height out of the 2
    Calculate the area and update the max area if the current calculated is larger
    */
    public int maxArea(int[] height) {
        return maxAreaHelper(height, 0, height.length - 1, 0);
    }

    private int maxAreaHelper(int[] height, int left, int right, int maxArea) {
        if (left >= right) { //Stop when we have checked all combinations for a left/ right index
            return maxArea;
        }
        //x1 = left index , x2 = right index
        //The length of the rectangle = x2 - x1
        //The height of the container = minimum of the 2 heights (because beyond that the water would overflow from the shorter bar)
        int currentArea = (right - left) * Math.min(height[left], height[right]);
        if (currentArea > maxArea) {
            maxArea = currentArea; //Update maxArea if needed
        }

        maxArea = maxAreaHelper(height, left + 1, right, maxArea); //Calculate maxArea for the next left index (recursively)
        maxArea = maxAreaHelper(height, left, right - 1, maxArea); //Calculate maxArea for the next right index (recursively)

        return maxArea; //Return the final calculated maxArea from all the recursive calls
    }
}
