package easy;

import java.util.Arrays;
import java.util.Comparator;

public class LongestCommonPrefix_14 {
    /*
    Improvement idea: the inner while loop compares the same strings again and again redundantly
    e.g. if we had "race", "racket", "raven"
    when we do the first iteration, we would compare the substrings "race", "rack", "rave"
    When we do the comparison we would know that the last index does not match for rack and the last 2 indices don't match for "rave"

    but again, in the next iteration we compare, "rac", "rac", "rav"

    To avoid these redundant comparisons,
    for index 0, 1, ... shortest_string.length:
        get shortest_string_char
        compare char at index for all other strings
        if all strings have the same char at index:
            check for next index
        else:
            return substring up to current index as the longest prefix
    */
    public String longestCommonPrefixSolution2(String[] strs) {
        sortByLength(strs);

        StringBuilder stringBuilder = new StringBuilder();

        int n = strs.length; //n = length of array
        int m = strs[0].length(); //m = length of shortest string

        int i = 0;
        while (i < m) {
            Character charAtShortestStr = strs[0].charAt(i);

            for  (int j = 1; j < n; j++) {
                if (strs[j].charAt(i) != charAtShortestStr) {
                    return stringBuilder.toString() ;
                }
            }
            stringBuilder.append(charAtShortestStr);
            i++;
        }
        return stringBuilder.toString();
    }

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
