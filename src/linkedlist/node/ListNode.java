package linkedlist.node;

/**
 * @author dengwenqi
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int value) {
        this.val = value;
    }

    ListNode(int value, ListNode next) {
        this.val = value;
        this.next = next;
    }
}
