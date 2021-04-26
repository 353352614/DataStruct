package array;

/**
 * @author dengwenqi
 */
public class FindMinimumInRotatedSortedArray {
    /**
     * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
     * 1.若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
     * 2.若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
     * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
     *
     * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public static int findMin(int[] nums) {
        int low = 0 ;
        int high = nums.length-1;
        while(low<high){
            //低位取中值
            int mid = low +((high-low)>>1);
            if(nums[mid]<nums[high]){
                //缩进右边
                //因为要求最小值,中点是符合要求
                //所以 high 不用等于 mid+1 而是mid
                high = mid;
            }else if(nums[mid]>nums[high]){
                //缩进左边
                //因为要求最小值,中点不符合要求
                //所以 low = mid +1
                low = mid +1;
            }
        }
            return nums[low];
    }

    public  static int findMin2(int[] nums){
        //左闭右开
        int low = 0 ;
        int high = nums.length;
        //为什么这里条件是  low+1 <high  而不是 log<high
        //eg: [0] 数组中只有一个数据, 要求:当只有一个数据的时候,不执行循环
        //当low = 0 ; high = 1; 这时候 low<high 满足条件的 会执行一次循环. low+1<high 反而不会执行
        while(low +1 < high){
            //低位取中值
            int mid = low +((high-low-1)>>1);
            if(nums[mid]<nums[high-1]){
                high = mid+1;
            }else{
                low= mid+1 ;
            }

        }
        return nums[low];
    }

    public static void main(String[] args) {
        int nums[] = new int[]{3,4,5,1,2};
        System.out.println(findMin2(nums));
    }
}
