package easy;

import java.util.Arrays;
import java.util.Comparator;

public class LongestCommonPrefix_14 {
    /*
    e.g. [racecar, racer, race]
    The longest possible prefix is the shortest string "race"
    Other possible suffixes are parts of the shortest string, like "rac", "ra", "r"

    Idea: sort the strings array based on the length of the strings
    Check if the shortest string is a prefix for every other string
    If not continue checking for smaller parts of the shortest string
    i.e. "race" does not match as a common prefix, check for "rac" and so on
    */
    public String longestCommonPrefixSolution1(String[] strs) {
        sortByLength(strs);

        //Keep track of where the "part" of the shortest string ends using the longestPrefixEnd pointer
        int longestPrefixEnd = strs[0].length();

        while (longestPrefixEnd >= 0) {
            String currentPrefix = strs[0].substring(0, longestPrefixEnd);

            int i = 1;
            while (i < strs.length &&
                    currentPrefix.equals(strs[i].substring(0, longestPrefixEnd))) { //continue the loop as long as the prefixes match
                i++;
            }

            if (i == strs.length) { //if you had gone through the whole array, all elements have the same prefix. So return the prefix
                return currentPrefix;
            }
            longestPrefixEnd--; //else, check for a smaller part of the shorter string
        }
        return "";
    }

    private void sortByLength(String[] strs) {
        Arrays.sort(strs, Comparator.comparingInt(String::length));
    }
}
