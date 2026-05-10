package medium;

import java.util.Hashtable;

public class LongestSubstring_3 {
    public static int lengthOfLongestSubstringSolution2(String s) {
        int seqStart = 0, seqEnd = 0; //Pointers for the beginning and end of the sequence being considered
        int n = s.length();

        Hashtable<Character, Integer> sequence = new Hashtable<>(); //build a single table with the latest index of the elements

        int longestSeqLen = 0;
        int currentSeqLen = 0;

        while (seqStart < n && seqEnd < n && (n - seqStart) > longestSeqLen) {
            if (sequence.containsKey(s.charAt(seqEnd))) { //if the current element has already occurred in the string at some point
                int matchedInd = sequence.get(s.charAt(seqEnd)); //check if the element is within the current sequence range (i.e. between seqStart and seqEnd)

                if (matchedInd >= seqStart) { //the detected duplicate element is within the sequence being considered
                    seqStart = matchedInd + 1; //start the next sequence after the matched index (like in solution1)
                }
            }

            sequence.put(s.charAt(seqEnd), seqEnd); //add the element/ or update its latest index
            //updating latest index means that we uniquely consider the element in context of a particular sequence
            //we don't have to worry about the element outside the current sequence, since we have already removed that from consideration
            //by updating the latest index

            currentSeqLen = seqEnd - seqStart + 1; //the current sequence length is determined by the end index and start index of the sequence since we are not redoing the loop like in solution1
            seqEnd++; //we are not resetting the seqEnd to startInd (or seqStart) like in solution1 because we already know that the values between seqStart and seqEnd are unique.
            //we can avoid redundant/ repeated comparisons this way.

            if (currentSeqLen > longestSeqLen) { //update longest sequence length accordingly
                longestSeqLen = currentSeqLen;
            }
        }
        return longestSeqLen;
    }

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
