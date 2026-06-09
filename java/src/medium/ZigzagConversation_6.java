package medium;

public class ZigzagConversation_6 {
    /*
    Idea: Keep track of each zigzag group (i.e. group 0, 1, ... numRows - 1)
    Concatenate group 0, 1, .. numRows in that order
    Groups:
    e.g. PAYPALISHIRING, n = 4
    group 0 : P(0)          I(6)
    group 1 : A(1)     L(5) S(7)
    group 2 : Y(2) A(4)      ....
    group 3 : P(3)

    when going down the zizag, the group number increases by 1
    when going up, the group number decreases by 1
    the direction of the zizag changes after every numRows letters
    */

    public String convert(String s, int numRows) {
        int groupNum = 0;
        boolean goingDown = true;

        StringBuilder[] groups = new StringBuilder[numRows];

        for (int i = 0; i < s.length(); i++) {
            //Initialise the group if it hasn't been initialised
            if (groups[groupNum] == null) {
                groups[groupNum] = new StringBuilder();
            }
            //Add the current letter to the appropriate group
            groups[groupNum].append(s.charAt(i));

            //If you have reached the last group, change direction to up or goingDown = false
            if (groupNum == numRows - 1) {
                goingDown = false;
            }
            //If you are in the first group by going up from the last, change direction to goingDown
            else if (groupNum == 0 && !goingDown) {
                goingDown = true;
            }
            //The next group is the current group + 1 if you are going down
            if (goingDown) {
                groupNum++;
            }
            //If you are going up, the next group is current group - 1
            //But only decrease group number if it is > 0 to avoid indexOutOfBounds error
            else if (groupNum > 0) {
                groupNum--;
            }
        }

        //Combine all the groups
        StringBuilder result = new StringBuilder();
        for (StringBuilder group : groups) {
            if (group != null) {result.append(group);}
        }

        return result.toString();
    }
}
