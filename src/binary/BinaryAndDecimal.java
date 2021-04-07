package binary;

import java.util.Stack;

/**
 * @author dengwenqi
 */
public class BinaryAndDecimal {
    static String[] templates = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public static void main(String[] args) {
        StringBuffer result = new StringBuffer();
        System.out.printf("使用栈：%s\n",BinaryAndDecimal.decimalToBinaryWithStack(13));
        System.out.printf("使用递归：%s\n",BinaryAndDecimal.decimalToBinaryWithRecursion(14,result));
        result.delete(0,result.length());
        System.out.printf("使用位运算递归：%s\n",BinaryAndDecimal.decimalToBinaryWithBitArithmetic(12,result));
        System.out.println(BinaryAndDecimal.binaryToOther("1111",binaryType.OCTAL));
        System.out.println(BinaryAndDecimal.binaryToOther("11111111",binaryType.HEXADECIMAL));
    }

    public enum binaryType{
        /**
         * 进制类型
         */
        BINARY("BINARY",2),
        OCTAL("OCTAL",8),
        DECIMAL("DECIMAL",10),
        HEXADECIMAL("HEXADECIMAL",16);
        private final String name;

        binaryType(String name, int i) {
            this.name = name;
        }

    }
    public enum binaryUnit{
        /**
         * 进制单位， 八进制代表2的3次方 单位为3
         */
        OCTAL("OCTAL",3),
        HEXADECIMAL("HEXADECIMAL",4);
        private final int value;
        binaryUnit(String name, int i) {
            this.value = i;
        }
        private int getValue(){
            return this.value;
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
            return null;
        }
        switch(type){
            case OCTAL:return BinaryAndDecimal.binaryToOctal(binary);
            case DECIMAL:return BinaryAndDecimal.binaryToDecimal(binary);
            case HEXADECIMAL:return BinaryAndDecimal.binaryToHexadecimal(binary);
            default:return binary;
        }

    }

    /**
     * 将二进制转二进制的整数次方归为一类进行计算
     * @param binary 二进制字符串
     * @return 对应的进制字符串
     */
    public static String binaryToBinaryOfIntegerPower(String binary,binaryType type,StringBuilder buffer){
        StringBuilder binaryBuffer = new StringBuilder(binary);
        int power = binaryUnit.valueOf(type.name).getValue();
        //每次方个二进制位为一个进制单位，有几个八进制单位
        int count = binary.length()/power;
        //如果位数不足则补齐
        int remainder = binary.length()%power;

        //需要补齐的位数
        int lackLength = 0;
        //如果存在余数
        if(0 != remainder){
            //如果存在余数，则表示多一个进制位
            ++count;
            //如果存在余数，计算出需要补足的位数
            lackLength = binaryUnit.valueOf(type.name).getValue()-remainder;
        }
        //高位补0
        if(0 != lackLength){
            for (int i = 0; i < lackLength; i++) {
                binaryBuffer.insert(0,"0");
            }
        }
        for (int i = 0; i < count; i++) {
            String decimalResult = BinaryAndDecimal.binaryToDecimal(binaryBuffer.substring(i*power,(i+1)*power));
                int templateIndex = Integer.parseInt(decimalResult);
                buffer.append(templates[templateIndex]);
        }
        return buffer.toString();
    }

    /**
     * 二进制转八进制
     * @param binary 二进制字符串
     * @return 八进制字符串
     */
    public static String binaryToOctal(String binary){
        StringBuilder result = new StringBuilder("0");
        return BinaryAndDecimal.binaryToBinaryOfIntegerPower(binary,binaryType.OCTAL,result);
    }

    /**
     * 二进制转十进制
     * @param binary 二进制字符串
     * @return 十进制字符串
     */
    public static String binaryToDecimal(String binary){
        int decimal = 0;

        char[] bits = binary.toCharArray();
        for (int i = 0;i<bits.length;i++) {
            //bits[i]的码不为 '0','1'
            if((int)bits[i]!=48 && (int)bits[i]!=49){
                System.out.println("无效二进制串");
            }
            //十进制为所有二进制的对应次方相加
            if((int)bits[i]==49){
                //bits.length-i-1 为当前下标数的补 例如：下标是0的时候 次方应为2。以此类推1,1;2,0
                decimal+= Math.pow(2,bits.length-i-1);
            }

        }
        System.out.printf("二进制:%s => 十进制:%d%n",binary,decimal);
        return String.valueOf(decimal);
    }

    /**
     * 二进制转十六进制
     * @param binary 二进制字符串
     * @return 十六进制字符串
     */
    public static String binaryToHexadecimal(String binary){
        StringBuilder result = new StringBuilder("Ox");
        return BinaryAndDecimal.binaryToBinaryOfIntegerPower(binary,binaryType.HEXADECIMAL,result);
    }


    /**
     * 使用栈来进行十进制转二进制
     * @param decimal 十进制
     */
    public static String decimalToBinaryWithStack(int decimal) {
        StringBuilder buffer = new StringBuilder();
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
        while (!stack.isEmpty()) {
            int r = stack.pop();
            buffer.append(r%2);
        }
        return buffer.toString();
    }

    /**
     * 运用递归来转二进制
     * @param decimal 十进制
     * @param buffer 存储容器
     */
    public static String decimalToBinaryWithRecursion(int decimal,StringBuffer buffer){
        //定义个参数来知道递归到第几层了
        //如果传入的是负数的话
        //转化为补码（正数的绝对值除符号位按位取反+1）
        if (decimal < 0) {
            int complement = ((-decimal) ^ Integer.MAX_VALUE) + 1;
            decimalToBinaryWithRecursion(complement,buffer);
        }
        //递结束条件
        if(decimal < 1){
            return null;
        }
        //递操作
        decimalToBinaryWithRecursion(decimal/2,buffer);
        //归操作
        buffer.append(decimal%2);
        return buffer.toString();
    }

    /**
     * 使用位运算来进行十进制转二进制
     * @param decimal 十进制
     * @param buffer 存储容器
     */
    public static String decimalToBinaryWithBitArithmetic(int decimal,StringBuffer buffer) {
        //定义个参数来知道递归到第几层了
        //如果传入的是负数的话
        //转化为补码（正数的绝对值除符号位按位取反+1）
        if (decimal < 0) {
            int complement = ((-decimal) ^ Integer.MAX_VALUE) + 1;
            decimalToBinaryWithBitArithmetic(complement,buffer);
        }
        //结束条件
        if (decimal == 0) {
            return null;
        }
        //递
        decimalToBinaryWithBitArithmetic(decimal >> 1,buffer);
        //归
        buffer.append(decimal & 1);
        return buffer.toString();
    }
}
