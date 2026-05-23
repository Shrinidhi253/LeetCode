package medium;

public class LongestPalindromicSubstring_5 {
    /*
    Solution 1 idea:
    recursive step: string[m : n] is a palindrome if string[m + 1 : n - 1] is a palindrome and string[m] = string[n]
    e.g. for "babba",
    string[1:4] is a palindrome if:
        1. string[1] = string[4] ("b" = "b") -> true
        2. string[2:3] is a palindrome ("bb" is a palindrome) -> true
        so "abba" is a palindrome

    Construct an nxn 2d array
    array[i][j] is:
        1. true if palindrome exists from index i to index j in the string
        2. false if palindrome does not exist from index i to j in the string
        3. null if it cannot be determined

    e.g for the string "babba", the 2d array should look like this:
    [      0       1      2      3      4
      0  [true,  false, true,  false, false], <- means string[0:0] ("b") and string[0:2] ("bab") are palindromes
      1  [null,  true,  false, false, true ], <- means string[1:1] ("a") and string[1:4] ("abba") are palindromes
      2  [null,  null,  true,  true,  false], <- means string[2:2] ("b") and string[2:3] ("bb") are palindromes
      3  [null,  null,  null,  true,  false], <- means string[3:3] ("b") is a palindrome
      4  [null,  null,  null,  null,  true ]  <- means string[4:4] ("a") is a palindrome
    ]

    To check if a substring starting at index m (to index n) is a palindrome, you need some value in the (m+1)th entry (the array[m+1][n-1] value)
    So start checking palindromic strings from the back, i.e. start = 4, so that start = 3 can make use of all start = 4 entries.
    */
    public String longestPalindromeSolution1(String s) {
        int n = s.length();
        Boolean[][] palindromes = new Boolean[n][n];
        int longestLen = 0, longestStart = 0, longestEnd = 0;

        for (int start = n-1; start >= 0; start--) {
            for (int end = start; end < n; end++) {
                if (start == end) { //single character is a palindrome
                    palindromes[start][end] = true;
                }
                else {
                    boolean check1 = (s.charAt(start) == s.charAt(end)); //check if the start character = end character

                    if (palindromes[start + 1][end - 1] != null) { //cannot check palindrome for intervals where start > end e.g palindromes[4:3] will be null
                        boolean check2 = palindromes[start + 1][end - 1]; //check2 is checking if the string[start + 1 : end - 1] is a palindrome
                        palindromes[start][end] = (check1 && check2); //only a palindrome if both checks are satisfied
                    }
                    else {
                        palindromes[start][end] = check1; //if the palindrome interval is not valid, then only check1 matters
                    }
                }

                if (palindromes[start][end] && (end - start + 1) > longestLen) {
                    longestLen = end - start + 1;
                    longestStart = start;
                    longestEnd = end;
                }
            }
        }
        return s.substring(longestStart, longestEnd + 1);
    }
}
