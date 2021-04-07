package binary;

import java.util.Stack;

/**
 * @author dengwenqi
 */
public class BinaryAndDecimal {
    public static void main(String[] args) {
        BinaryAndDecimal.decimalToBinaryWithStack(127);
        BinaryAndDecimal.decimalToBinaryWithRecursion(127,0);
        BinaryAndDecimal.decimalToBinaryWithBitArithmetic(127,0);
    }

    /**
     * 使用栈来进行十进制转二进制
     * @param decimal 十进制
     */
    public static void decimalToBinaryWithStack(int decimal) {
        //如果传入的是负数的话
        //转化为补码（正数的绝对值除符号位按位取反+1）
        if (decimal < 0) {
            int complement = ((-decimal) ^ Integer.MAX_VALUE) + 1;
            decimalToBinaryWithStack(complement);
        }
        //传入的是正数
        //用一个stack代替递归/保持递归顺序。
        Stack<Integer> stack = new Stack<>();
        while (decimal >= 1) {
            //终止条件 decimal 与二的商小于1
            stack.push(decimal);
            decimal /= 2;
        }
        System.out.print("使用栈： ");
        while (!stack.isEmpty()) {
            int r = stack.pop();
            System.out.print(r % 2);
        }
        System.out.println();
    }

    /**
     * 运用递归来转二进制
     * @param decimal 十进制
     * @param floor 层数
     */
    public static void decimalToBinaryWithRecursion(int decimal,int floor){
        //定义个参数来知道递归到第几层了
        //如果传入的是负数的话
        //转化为补码（正数的绝对值除符号位按位取反+1）
        if (decimal < 0) {
            int complement = ((-decimal) ^ Integer.MAX_VALUE) + 1;
            decimalToBinaryWithRecursion(complement,floor);
        }
        //递结束条件
        if(decimal < 1){
            System.out.print("使用递归：");
            return ;
        }
        //递操作
        decimalToBinaryWithRecursion(decimal/2,++floor);
        //归操作
        System.out.print(decimal%2);
        if(1 == floor){
            System.out.println();
        }
    }

    /**
     * 使用位运算来进行十进制转二进制
     * @param decimal 十进制
     * @param floor 层数
     */
    public static void decimalToBinaryWithBitArithmetic(int decimal,int floor) {
        //定义个参数来知道递归到第几层了
        //如果传入的是负数的话
        //转化为补码（正数的绝对值除符号位按位取反+1）
        if (decimal < 0) {
            int complement = ((-decimal) ^ Integer.MAX_VALUE) + 1;
            decimalToBinaryWithBitArithmetic(complement, floor);
        }
        //结束条件
        if (decimal == 0) {
            System.out.print("使用位运算递归：");
            return;
        }
        //递
        decimalToBinaryWithBitArithmetic(decimal >> 1, ++floor);
        //归
        System.out.print(decimal & 1);
        if (1 == floor) {
            System.out.println();
        }
    }
}
