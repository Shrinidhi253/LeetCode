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

    public boolean isPalindromeSolution1(int x) {
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

    /*
    Idea:
    The array in solution 1 actually adds the digits in the reverse order because of the %
    e.g. 123 -> [3, 2, 1]
    So we can perform a forward num = reverse num check if we construct a reversedNum itself
    instead of a reverse array of digits

    use the digit to construct the reverse number
    e.g. 123 -> when digit = 3
    3 in the reverse number will be 3 * 10 ^ 2 = 300
    2 in the reverse number will be 2 * 10 ^ 1 (+ 300) = 320 and so on...
    the power of the digit depends on the degree of the number which would be log_10 of the number
    e.g. 321, the highest power is in 300, with 10^2. the degree is 2.
    */

    public boolean isPalindromeSolution2(int x) {
        if (x < 0) {return false;}
        if (x < 10) {return true;}

        int degree = (int) Math.floor(Math.log10(x));
        long reverseNum = 0;

        int i = x; //Use temp variable i for looping since we will be updating the value and we need the original number to compare in the end
        while (i > 0) {
            int digit = i % 10;

            reverseNum += (digit * (long) Math.pow(10, degree)); //numDigits - 1 is the remaining number of digits
            i /= 10;
            degree--;
        }
        System.out.println("Reverse num: " + reverseNum);
        return reverseNum == x;
    }

    /*
    Idea: Solution2 performed worse than Solution1 likely because of the conversions
    between float and integer and performing log and exponentiation on floats and then converting them
    to integers.

    So remove the float and log operations
    the log operation was to get the number of digits to calculate degree

    degree (power of 10) of the reversed number increases by 1 every iteration
    e.g. 123
    iteration 1 = 3 -> degree = 0
    iteration 2 = 3 * 10^1 + 2 = 32 -> degree = 1
    iteration 3 = 300 * 10^2 + 20 + 1 = 32 * 10 + 1 -> degree = 2

    so we can multiply the current reversed num by 10 every iteration and add the digit without
    having to keep track of the number of digits left
    */
    public boolean isPalindromeSolution3(int x) {
        if (x < 0) {return false;}
        if (x < 10) {return true;}

        long reversedNum = 0;
        int i = x;
        while (i > 0) {
            int digit = i % 10;
            reversedNum = reversedNum * 10 + digit;
            i /= 10;
        }
        return x == reversedNum;
    }
}
