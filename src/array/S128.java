package array;

import java.util.HashSet;
import java.util.Set;

public class S128 {
    /**
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     * 示例 2：
     * <p>
     * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
     * 输出：9
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/WhsWhI
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public static void main(String[] args) {
        int[] nums = new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1};//{100, 4, 200, 0,2, 5, 1, 3, 2};
        System.out.println(longestConsecutive(nums));
    }

    public static int longestConsecutive(int[] nums) {
        if (nums.length < 1) {
            return 0;
        }
        Set<Integer> set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }

        int cResult = 1;
        int oldResult = 1;
        for (int num : set
        ) {
            if (!set.contains(num - 1)) {
                while (set.contains(num + 1)) {
                    cResult++;
                    num++;
                }
                oldResult = oldResult > cResult ? oldResult : cResult;
                cResult = 1;
            }
        }
        return oldResult;
    }


}
