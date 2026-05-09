package medium;

import helper.ListNode;

public class AddTwoNumbers_2 {
    //342 = 2->4->3
    //564 = 4->6->5
    //add corresponding nodes of l1 and l2
    //e.g. 2+4, 4+6...
    //if the sum is >= 10, the carry over = sum / 10, and the sum node value = sum % 10

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resultHead = new ListNode();
        addTwoNumbersHelper(l1, l2, resultHead, 0);

        return resultHead;
    }

    public ListNode addTwoNumbersHelper(ListNode l1, ListNode l2, ListNode resultNode, int carry) {
        if (l1 == null && l2 == null) {
            resultNode.val = carry;
            return resultNode;
        }

        else if (l1 == null) {
            resultNode.val = (l2.val + carry) % 10;
            carry = (l2.val + carry) / 10;

            resultNode.next = new ListNode();
            return addTwoNumbersHelper(l1, l2.next, resultNode.next, carry);
        }

        else if (l2 == null) {
            resultNode.val = (l1.val + carry) % 10;
            carry = (l1.val + carry) / 10;

            resultNode.next = new ListNode();
            return addTwoNumbersHelper(l1.next, l2, resultNode.next, carry);
        }

        else {
            resultNode.val = (l1.val + l2.val + carry) % 10;
            carry = (l1.val + l2.val + carry) / 10;

            resultNode.next = new ListNode();
            return addTwoNumbersHelper(l1.next, l2.next, resultNode.next, carry);
        }
    }
}
