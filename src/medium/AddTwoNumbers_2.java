package medium;

import helper.ListNode;

public class AddTwoNumbers_2 {
    //342 = 2->4->3
    //564 = 4->6->5
    //add corresponding nodes of l1 and l2
    //e.g. 2+4, 4+6...
    //if the sum is >= 10, the carry over = sum / 10, and the sum node value = sum % 10

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbersHelper(l1, l2, new ListNode(), 0);
    }

    public ListNode addTwoNumbersHelper(ListNode l1, ListNode l2, ListNode resultNode, int carry) {
        if (l1 == null && l2 == null) {
            if (carry > 0) { //if you have reached the end of l1 and l2, the next node in the result will point to the carry if any
                resultNode = new ListNode(carry); //create a new node only if there is a carry over. otherwise, resultNode will remain null to avoid leading zeroes
            }
        }

        else {
            resultNode = new ListNode(); //if there are still numbers to add, create a new result node to store the values

            if (l1 == null) { //if we have reached the end of l1 but there are still values for l2, add the values of l2 to the result after accounting for the carry over
                resultNode.val = (l2.val + carry) % 10;
                carry = (l2.val + carry) / 10;

                resultNode.next = addTwoNumbersHelper(l1, l2.next, resultNode.next, carry);
            }

            else if (l2 == null) {//similarly, if we reached end of l2 but not l1, add values of l1 to the result
                resultNode.val = (l1.val + carry) % 10;
                carry = (l1.val + carry) / 10;

                resultNode.next = addTwoNumbersHelper(l1.next, l2, resultNode.next, carry);
            }

            else {//when there are still values to from both l1 and l2
                resultNode.val = (l1.val + l2.val + carry) % 10;
                carry = (l1.val + l2.val + carry) / 10;

                resultNode.next = addTwoNumbersHelper(l1.next, l2.next, resultNode.next, carry);
                //updates the next node with the sum of the values + carry over
            }
        }

        return resultNode; //returns the result node with the updated next reference.
    }
}
