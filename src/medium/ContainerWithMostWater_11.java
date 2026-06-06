package medium;

public class ContainerWithMostWater_11 {
    /*
    Idea: Checking all combinations is redundant
    If left height = 1, right = 9
    It is almost certain that any combination of area with 1, like 1x11, 1x10, 1x2, etc is not likely to be the max area
    when 9x11, 9x10, 9x2 exists
    So check for other heights
    i.e. if the left height is shorter -> check area for current right and next left
    if the right height is shorter -> check area for current left and next right
    */
    public int maxAreaSolution2(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            if (area > maxArea) {
                maxArea = area;
            }

            //try the next left value to hopefully get a larger area
            if (height[left] < height[right]) { left++; }

            //same. try the next right to hopefully get a larger area
            else { right--; }
        }
        return maxArea;
    }

    /*
    Idea: Check every combination of right and left x
    Get the min height out of the 2
    Calculate the area and update the max area if the current calculated is larger
    */
    public int maxAreaSolution1(int[] height) {
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
