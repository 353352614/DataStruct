package binary;

import java.util.Stack;

/**
 * @author dengwenqi
 */
public class BinaryAndDecimal {
    static String[] templates = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public static void main(String[] args) {
        BinaryAndDecimal.decimalToBinaryWithStack(127);
        BinaryAndDecimal.decimalToBinaryWithRecursion(127,0);
        BinaryAndDecimal.decimalToBinaryWithBitArithmetic(127,0);
        System.out.println(BinaryAndDecimal.binaryToOther("1111",binaryType.OCTAL));
    }

    public enum binaryType{
        BINARY("二进制",2),
        OCTAL("八进制",8),
        DECIMAL("十进制",10),
        HEXADECIMAL("十六进制",16);
        binaryType(String name, int i) {
        }
    }
    public enum binaryUnit{
        OCTAL("八进制",3),
        HEXADECIMAL("十六进制",4);
        private String name;
        private int value;
        binaryUnit(String name, int i) {
        this.name = name;
        this.value = i;
        }
        private int getValue(){
            return this.value;
        }
        private String getName(){
            return this.name;
        }
    }


    /**
     * 二进制转八进制（octal），十进制（decimal），十六进制(hexadecimal)
     * @param binary 二进制字符串
     * @param type 目标进制类型
     */
    public static String binaryToOther(String binary,binaryType type){
        if(null == binary || "".equals(binary)){
            System.out.println("无效二进制串");
        }
        switch(type){
            case OCTAL:return BinaryAndDecimal.binaryToOctal(binary);
            case DECIMAL:return BinaryAndDecimal.binaryToDecimal(binary);
            case HEXADECIMAL:return BinaryAndDecimal.binaryToHexadecimal(binary);
            default:return binary;
        }

    }

    /**
     * 二进制转八进制
     * @param binary
     * @return
     */
    public static String binaryToOctal(String binary){
        StringBuilder result = new StringBuilder("0");
        //有几个八进制单位
        int count = binary.length()/binaryUnit.OCTAL.getValue();
        //如果位数不足则补齐
        int remainder = binary.length()%binaryUnit.OCTAL.getValue();
        if(0!=remainder){
            ++count;
        }
        //需要补齐的位数
        int lackLength = binaryUnit.OCTAL.getValue()-remainder;
        //高位补0
        if(0 != lackLength){
            for (int i = 0; i < lackLength; i++) {
                binary="0"+binary;
            }
        }
        for (int i = 0; i < count; i++) {
            result.append(BinaryAndDecimal.binaryToDecimal(binary.substring(i*3,(i+1)*3)));
        }
        return result.toString();
    }

    /**
     * 二进制转十进制
     * @param binary
     * @return
     */
    public static String binaryToDecimal(String binary){
        int decimal = 0;

        char[] bits = binary.toCharArray();
        for (int i = 0;i<bits.length;i++) {
            if((int)bits[i]!=48 && (int)bits[i]!=49){
                System.out.println("无效二进制串");
            }
            if((int)bits[i]==49){
                decimal+= Math.pow(2,bits.length-i-1);
            }

        }
        System.out.println(String.format("二进制:%s => 十进制:%d",binary,decimal));
        return String.valueOf(decimal);
    }

    /**
     * 二进制转十六进制
     * @param binary
     * @return
     */
    public static String binaryToHexadecimal(String binary){
        return null;
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
