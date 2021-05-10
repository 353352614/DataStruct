package linkedlist;

import linkedlist.node.ListNode;

/**
 * 给你两个非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0开头。
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * <p>
 * [0,3] [1,2,3]
 * 30+321
 */
public class addTwoNumbers {
    /**
     * 思路一 遍历获取所有链的长度,然后遍历长的链,逐个相加构成一个新链表 ×
     * 思路二 将链表转换为对应的数组,相加之后,构成新的链表 ×
     * 思路三 同时遍历两个链表,逐位计算他们的和,并与当前位置的进位相加.
     * 注意的点:
     *      1.默认短的链表末尾有无数个0(遍历时如果短链表为null那么val认为0)
     *      2.每一轮的总数记为sum,进位记为carry,初始sum为0,初始进位为0,循环一轮之后,新的总数为l1.val+l2.val+carry,
     *        新的进位为sum/10
     *      3.构成新链表节点的值为sum%10
     *      4.l1,l2都为空且进位不为空时,进位构成新的节点值
     *      5.可以生成一个dummy节点来处理head为空时候的问题
     */


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //dummy
        ListNode dummy = new ListNode(0);
        //head指向dummy
        ListNode head = dummy;
        //carry
        int carry = 0;
        int sum = 0;
        while(null != l1 || null !=l2 || carry!=0){
            //每一轮总数
            sum = (null==l1?0:l1.val)+(null==l2?0:l2.val)+carry;
            //构成新节点的值
            int newVal = sum % 10;
            //新的进位
            carry = sum/10;
            //构成新的节点
            dummy.next = new ListNode(newVal);
           //l1指针后移
            l1 = null == l1?null:l1.next;
            l2 = null == l2?null:l2.next;
            dummy = dummy.next;
        }
        return head.next;
    }

    public static void main(String[] args) {
        int[] l1arr = new int[]{9, 9, 1};
        int[] l2arr = new int[]{1};
        ListNode l1, l2;
        l1 = generateData(l1arr);
        l2 = generateData(l2arr);
        ListNode r = addTwoNumbers(l1, l2);
        while (r != null) {
            System.out.println(r.val);
            r = r.next;
        }
    }

    private static ListNode generateData(int[] arr) {
        ListNode l1 = new ListNode(-1);
        ListNode current = l1;
        for (int i = 0; i < arr.length; i++) {
            current.val = arr[i];
            if(i==arr.length-1){
                break;
            }
            current.next  = new ListNode();
            current = current.next;
        }
        return l1;
    }
}
