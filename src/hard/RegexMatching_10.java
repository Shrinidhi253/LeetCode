package hard;

public class RegexMatching_10 {
    /*
    if s is empty and p is empty -> return true #they are empty strings/ or you have reached the end without returning false
    if s is not empty but p is empty -> return false #because the regex is not enough to represent the whole string
    if s is empty but p is not empty ->
        acceptable possibilities include "x*", "*****", etc
        check if next pChar is "*" AND check current sChar and next next pChar (to recursively check all the * and avoid matching cases like ****y** with empty s character)

    if s is not empty and current p char is '.' -> check next sChar and next pChar #just consider the current characters to be matched by the dot
    if s is not empty and next p char is '*' ->
        skip 2 chars to match 0 chars (e.g skip 'a*' as a whole with 'b')
        OR
        check next s char and current p char (e.g check 'a*' with 'aaaa')

    else s char and p char are plain characters ->
        if s char = p char -> check next s char and next p char
        else return false
     */
    public boolean isMatchSolution1(String s, String p) {
        return isMatchHelper1(s, p, 0, 0);
    }

    private boolean isMatchHelper1(String s, String p, int sIndex, int pIndex) {
        if (sIndex >= s.length() && pIndex >= p.length()) {return true;} //s is empty and p is empty
        if (sIndex >= s.length()) {
            return pIndex + 1 < p.length() && (p.charAt(pIndex + 1) == '*') && isMatchHelper1(s, p, sIndex, pIndex + 2);
            //recursively check if all the next characters in p are '*' for the 0 match to be valid
        }

        if (pIndex == p.length()) {return false;} //exhausted the regex without fully matching the string

        //1 char match if they are the same characters or p char is a '.'
        boolean charsMatch = s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '.';

        if (pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
            return (isMatchHelper1(s, p, sIndex, pIndex + 2) || //Assume 0 match and check next characters
                    (charsMatch && isMatchHelper1(s, p, sIndex + 1, pIndex)) //1 match and check for the next possible matches
                );
        }

        //If none of the above cases were called, then s char and p char are normal characters or p char is a dot (wild character)
        if (charsMatch) { //if they are same, check for next matches
            return isMatchHelper1(s, p, sIndex + 1, pIndex + 1);
        }
        else return false; //if they are not same, match failed. return false
    }

    /*
    The previous solution was very slow when the strings length increases because the recursive calls get more deep
    It is possible that when the recursive calls get deeper, the same call (with same parameters) is made from different stages
    Store the computed results in a 2D array
    */

    public boolean isMatchSolution2(String s, String p) {
        Boolean[][] spResults = new Boolean[s.length()][p.length()];
        return isMatchHelper2(s, p, 0, 0, spResults);
    }

    private boolean isMatchHelper2(String s, String p, int sIndex, int pIndex, Boolean[][] spResults) {
        //Check the base cases for special sIndex and pIndex values
        if (sIndex >= s.length() && pIndex >= p.length()) {return true;} //s is empty and p is empty
        if (sIndex >= s.length()) {
            return pIndex + 1 < p.length() && (p.charAt(pIndex + 1) == '*') && isMatchHelper2(s, p, sIndex, pIndex + 2, spResults);
            //recursively check if all the next characters in p are '*' for the 0 match to be valid
        }

        if (pIndex == p.length()) {return false;} //exhausted the regex without fully matching the string

        //If the base cases are not satisfies, check if (sIndex, pIndex) pair has a precomputed result
        Boolean result = spResults[sIndex][pIndex];
        if (result != null) {return result;}

        //If the precomputed result is null, compute them using the same algorithm as before (but without returning it immediately)

        //1 char match if they are the same characters or p char is a '.'
        boolean charsMatch = s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '.';

        if (pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
            result = (isMatchHelper2(s, p, sIndex, pIndex + 2, spResults) || //Assume 0 match and check next characters
                    (charsMatch && isMatchHelper2(s, p, sIndex + 1, pIndex, spResults)) //1 match and check for the next possible matches
            );
        }

        //If none of the above cases were called, then s char and p char are normal characters or p char is a dot (wild character)
        else if (charsMatch) { //if they are same, check for next matches
            result = isMatchHelper2(s, p, sIndex + 1, pIndex + 1, spResults);
        }
        else result = false; //if they are not same, match failed. return false

        //Update the result for the (sIndex, pIndex) pair in the array before returning the result.
        spResults[sIndex][pIndex] = result;
        return result;
    }
}
