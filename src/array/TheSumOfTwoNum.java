package array;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengwenqi
 */
public class TheSumOfTwoNum {
    /**
     * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 的那两个整数，并返回它们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     *
     * 你可以按任意顺序返回答案。
     *
     * 示例1:
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     */

    /**
     * 思路一 双重for循环逐个相加等于target  | × (效率过低)
     * 思路二 根据原数组n的内容j构建一个长度为i(i为n数组最大值)的数组m,m中下标k为j,然后遍历原数组判断m[target-j]是否存在 | × (数值很大时,要求内存空间过大)
     * 思路三 构建一个散列表m,key=i(数组内容),value=k(数组下标),遍历原数组判断m.get(target-i)是否存在 √
     * 思路四 在思路三的基础上优化: 不用事先构建散列表,遍历数组的同时构建散列表,既可以防止与自己匹配还可以减少一次循环 √√
     */



    public static int[] way3(int[] nums, int target) {
        int[] result =  new int[2];
        if(nums.length == 0 ) {return nums;}
        Map<Integer,Integer> m = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
                m.put(nums[i],i);
        }
        for (int i = 0; i < nums.length; i++) {
            if(!m.containsKey(target-nums[i])){
                continue;
            }else{
                if(i == m.get(target-nums[i])){
                    continue;
                }
                result[0] = i;
                result[1] = m.get(target-nums[i]);

                break;
            }
        }
        return result;
    }

    public static int[] way4(int[] nums, int target) {
        int[] result =  new int[2];
        if(nums.length == 0 ) {return nums;}
        Map<Integer,Integer> m = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if(!m.containsKey(target-nums[i])){
                m.put(nums[i],i);
            }else{
                result[0] = m.get(target-nums[i]);
                result[1] = i;
                return result;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,2,4};
        int[] result = way4(nums,6);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

    }
}
