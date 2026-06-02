package easy;

import java.util.ArrayList;

public class PalindromeNumber_9 {
    /*
    Idea: array-ify the number
    check if the 1st and last digit match, 2nd and 2nd last match and so on
    e.g. 1231 --- array-ify --> [1, 2, 3, 1]
    check index 0 and index 3 -> 1 = 1
    check index 1 and index 2 -> 2 not = 3 -> not a palindrome

    But it is very similar to converting an integer into a string which we were asked not
    to do in the problem .... T-T
    */
    public boolean isPalindrome(int x) {
        if (x < 0) {return false;}
        if (x < 10) {return true;}

        ArrayList<Integer> digits = new ArrayList<>();

        while (x > 0) {
            digits.add(x % 10);
            x /= 10;
        }

        int left = 0, right = digits.size() - 1;
        while (left < right) {
            if (digits.get(left) != digits.get(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
