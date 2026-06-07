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
    public boolean isMatch(String s, String p) {
        return isMatchHelper(s, p, 0, 0);
    }

    private boolean isMatchHelper(String s, String p, int sIndex, int pIndex) {
        if (sIndex >= s.length() && pIndex >= p.length()) {return true;} //s is empty and p is empty
        if (sIndex >= s.length()) {
            return pIndex + 1 < p.length() && (p.charAt(pIndex + 1) == '*') && isMatchHelper(s, p, sIndex, pIndex + 2);
            //recursively check if all the next characters in p are '*' for the 0 match to be valid
        }

        if (pIndex == p.length()) {return false;} //exhausted the regex without fully matching the string

        if (pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
            return (isMatchHelper(s, p, sIndex, pIndex + 2) || //Assume 0 match and check next characters
                    ((s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '.') && //1 char match if they are the same characters or p char is a '.'
                            isMatchHelper(s, p, sIndex + 1, pIndex)) //1 match and check for the next possible matches
                );
        }

        //If none of the above cases were called, then s char and p char are normal characters or p char is a dot (wild character)
        if (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '.') { //if they are same, check for next matches
            return isMatchHelper(s, p, sIndex + 1, pIndex + 1);
        }
        else return false; //if they are not same, match failed. return false
    }
}
