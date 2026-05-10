package medium;

import java.util.Hashtable;

public class LongestSubstring_3 {
    public int lengthOfLongestSubstringSolution1(String s) {
        int startInd = 0, n = s.length();
        int longestSeqLen = 0;

        //find a sequence for every (reasonable) startInd as long as there are more characters left than the length of the longest sequence
        while (startInd < n && (n - startInd > longestSeqLen)) {
            Hashtable<Character, Integer> currentSequence = new Hashtable<>(); //keep track of every unique character in a sequence and it's corresponding index
            int currentSeqLen = 0; //keep track of the length of the current sequence (which is 1 including the current start character)

            int currentInd = startInd; //keep track of the index of the characters in the sequence

            while (currentInd < n &&
                    !currentSequence.containsKey((s.charAt(currentInd)))
            ) { //loop through the next indices as long as the characters are unique (and does not already exist in the sequence)
                currentSequence.put((s.charAt(currentInd)), currentInd); //add the current character to the sequence
                currentSeqLen++;
                currentInd++;
            }

            //if the previous while loop exited because a repeated character was found:
            if (currentInd < n) {
                Character matchedChar = s.charAt(currentInd);
                int matchedCharInd = currentSequence.get(matchedChar); //get the first index of the character that matched with the current index

                startInd = matchedCharInd + 1; //start checking the next sequence from after the matchedChar because we knwo if we start before or at the matchedCharInd, the sequence will always find a repeated value at the current index
            }

            //update the longest sequence length if required
            if (currentSeqLen > longestSeqLen) {
                longestSeqLen = currentSeqLen;
            }

            //the outer loop will terminate if there are less character than the current longestSeqLen because there is no way to find a longer sequence.
        }
        return longestSeqLen;
    }
}
