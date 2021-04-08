package binary;

import com.sun.javafx.binding.StringFormatter;

import java.util.Stack;

/**
 * 进行以下总结：
 * 进制互相转化几个核心的功能：
 * 1.其他进制转换为十进制
 * 2.十进制转换为其他进制
 * 任何进制都可以通过十进制做中间进制的转换
 * 其中有几个注意的点：
 * 1.二进制向2ⁿ的转换 可以用N个bit位为一组依次进行十进制转换，转换的结果拼接为对应进制结果
 * 2.2ⁿ进制向二进制转换可以将每一位拆分为N个bit位进行结果拼接
 * 3.十进制转换为其他进制时可以用递归，迭代法（For）（可以利用栈来保证顺序为递归顺序）的方法计算。其中可以用位运算替代求商和取余
 * 4.判断一个数是否为2ⁿ的方法为:存在一个整数N，有(N&(N-1))==0
 * 5.对负数取补码的方法为：((-N) ^ Integer.MAX_VALUE) + 1;
 * 6.遍历一个数列L，有当前游标i，与i位置对称的位置为 L.length-i-1. (-1是因为数列下标由0开始的)
 * 7.二进制可以看做数组中存了N个1或者0，数组下标即为对应的次幂
 *
 * @author dengwenqi
 */
public class BinaryConvertDecimal {
    static String[] templates = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
            , "G", "H", "J", "K", "L", "M", "N", "P"};

    public static void main(String[] args) {
        System.out.printf("使用栈：%s\n", BinaryConvertDecimal.decimalToBinaryWithStack(13));
        System.out.printf("使用递归：%s\n", BinaryConvertDecimal.decimalToBinaryWithRecursion(14, new StringBuffer()));
        System.out.printf("使用位运算递归：%s\n", BinaryConvertDecimal.decimalToBinaryWithBitArithmetic(12, new StringBuffer()));
        System.out.println(BinaryConvertDecimal.binaryToOther("1111", BinaryType.OCTAL, new StringBuffer()));
        System.out.println(BinaryConvertDecimal.binaryToOther("1111", BinaryType.BASE_24, new StringBuffer()));
        System.out.println(BinaryConvertDecimal.otherToDecimal("666", BinaryType.BASE_7, new StringBuffer()));
        System.out.println(otherToOther("6666", BinaryType.BASE_7, BinaryType.OCTAL, new StringBuffer()));
    }

    public enum BinaryType {
        /**
         * 进制类型
         */
        BINARY("BINARY", 2),
        BASE_3("BASE_3", 3),
        BASE_4("BASE_4", 4),
        BASE_5("BASE_5", 5),
        BASE_6("BASE_6", 6),
        BASE_7("BASE_7", 7),
        OCTAL("OCTAL", 8),
        BASE_9("BASE_9", 9),
        DECIMAL("DECIMAL", 10),
        BASE_11("BASE_11", 11),
        BASE_12("BASE_12", 12),
        BASE_13("BASE_13", 13),
        BASE_14("BASE_14", 14),
        BASE_15("BASE_16", 16),
        HEXADECIMAL("HEXADECIMAL", 16),
        BASE_17("BASE_17", 17),
        BASE_18("BASE_18", 18),
        BASE_19("BASE_19", 19),
        BASE_20("BASE_20", 20),
        BASE_21("BASE_21", 21),
        BASE_22("BASE_22", 22),
        BASE_23("BASE_23", 23),
        BASE_24("BASE_24", 24);
        private final String name;
        private final int value;

        BinaryType(String name, int value) {
            this.name = name;
            this.value = value;
        }

    }

    public enum BinaryUnit {
        /**
         * 如果一个数N为2的正整数次幂，设m=log(2,N)为一个进制单位，如8进制的进制的进制单位为3
         */
        BASE_4("OCTAL", 2),
        OCTAL("OCTAL", 3),
        HEXADECIMAL("HEXADECIMAL", 4);
        private final int value;

        BinaryUnit(String name, int i) {
            this.value = i;
        }

        private int getValue() {
            return this.value;
        }
    }


    /**
     * 二进制转八进制（octal），十进制（decimal），十六进制(hexadecimal)
     *
     * @param binary     二进制字符串
     * @param targetType 目标进制类型
     */
    public static String binaryToOther(String binary, BinaryType targetType, StringBuffer stringBuffer) {
        stringBuffer.delete(0, stringBuffer.length());
        if (null == binary || "".equals(binary)) {
            System.out.println("无效二进制串");
            return null;
        }
        //如果一个数N为2的整数次幂，那么N的二进制中有效地最高位一定为1 ,有表达式 N&N-1=0 成立
        if (0 == (targetType.value & (targetType.value - 1))) {
            return BinaryConvertDecimal.binaryToBinaryOfIntegerPower(binary, targetType, stringBuffer);
        } else {
            return BinaryConvertDecimal.binaryToBinaryOfUnIntegerPower(binary, targetType, stringBuffer);
        }

    }

    /**
     * 将二进制转二进制的整数次幂归为一类进行计算
     *
     * @param binary 二进制字符串
     * @return 对应的进制字符串
     */
    public static String binaryToBinaryOfIntegerPower(String binary, BinaryType type, StringBuffer buffer) {
        buffer.delete(0, buffer.length());
        StringBuilder binaryBuffer = new StringBuilder(binary);
        int power = BinaryUnit.valueOf(type.name).getValue();
        //求binary有多少个进制单位
        int count = binary.length() / power;
        //通过余数来判断是否需要补齐位数。例如 7%2 = 1 则表示2个单位还多一个。需要补齐 3-1=2个
        int remainder = binary.length() % power;
        //存放需要补齐数量
        int lackLength = 0;
        //如果存在余数
        if (0 != remainder) {
            //如果存在余数，则表示多一个进制单位
            ++count;
            //如果存在余数，计算出需要补足的位数
            lackLength = BinaryUnit.valueOf(type.name).getValue() - remainder;
        }
        //高位补0
        if (0 != lackLength) {
            for (int i = 0; i < lackLength; i++) {
                binaryBuffer.insert(0, "0");
            }
        }
        for (int i = 0; i < count; i++) {
            //以进制单位分割字符串
            String decimalResult = BinaryConvertDecimal.binaryToDecimal(binaryBuffer.substring(i * power, (i + 1) * power));
            //返回的每个数字作为templates的下标
            int templateIndex = Integer.parseInt(decimalResult);
            //处理结果
            buffer.append(templates[templateIndex]);
        }
        return buffer.toString();
    }

    /**
     * 将二进制转二进制的非整数次幂归为一类进行计算
     * 具体做法是利用十进制做中间状态转化
     *
     * @param binary     十进制
     * @param targetType 目标进制
     * @param buffer     结果容器
     * @return 进制字符串
     */
    public static String binaryToBinaryOfUnIntegerPower(String binary, BinaryType targetType, StringBuffer buffer) {
        buffer.delete(0, buffer.length());
        //将二进制转化为十进制
        String decimalStr = BinaryConvertDecimal.binaryToDecimal(binary);
        //十进制转为N进制)
        return BinaryConvertDecimal.decimalToOther(Integer.parseInt(decimalStr), targetType, buffer);
    }


    /**
     * 二进制转十进制
     *
     * @param binary 二进制字符串
     * @return 十进制字符串
     */
    public static String binaryToDecimal(String binary) {
        int decimal = 0;

        char[] bits = binary.toCharArray();
        for (int i = 0; i < bits.length; i++) {
            //bits[i]的码不为 '0','1'
            if ((int) bits[i] != 48 && (int) bits[i] != 49) {
                System.out.println("无效二进制串");
            }
            //十进制为所有二进制的对应次幂相加
            if ((int) bits[i] == 49) {
                //bits.length-i-1 为当前下标数的补 例如：下标是0的时候 次幂应为2。以此类推1,1;2,0
                decimal += Math.pow(2, bits.length - i - 1);
            }

        }
        System.out.printf("二进制:%s => 十进制:%d%n", binary, decimal);
        return String.valueOf(decimal);
    }


    /**
     * 其他进制转换为十进制
     *
     * @param number       进制字符串
     * @param resourceType 进制类型
     * @return 十进制字符串
     */
    public static String otherToDecimal(String number, BinaryType resourceType, StringBuffer buffer) {
        buffer.delete(0,buffer.length());
        char[] numberChar = number.toCharArray();
        int decimal = 0;
        for (int i = 0; i < numberChar.length; i++) {
            int numberInt = Integer.parseInt(String.valueOf(numberChar[i]));
            if (numberInt >= resourceType.value) {
                throw new RuntimeException(String.format("%d进制格式错误", resourceType.value));
            }
            decimal += numberInt * (Math.pow(resourceType.value, numberChar.length - i - 1));
        }
        return buffer.append(decimal).toString();
    }

    /**
     * 任意进制互相转化
     *
     * @param number       进制字符串
     * @param resourceType 源类型
     * @param targetType   目标类型
     * @param buffer       结果容器
     * @return 进制字符串
     */
    public static String otherToOther(String number, BinaryType resourceType, BinaryType targetType, StringBuffer buffer) {
        buffer.delete(0, buffer.length());
        if (resourceType == BinaryType.BINARY) {
            return binaryToOther(number, targetType, buffer);
        }
        if (targetType == BinaryType.DECIMAL) {
            return otherToDecimal(number, resourceType, buffer);
        }
        return decimalToOther(Integer.parseInt(otherToDecimal(number, resourceType, buffer)), targetType, buffer);
    }

    /**
     * 十进制转换为其他进制
     *
     * @param decimal    十进制
     * @param targetType 目标类型
     * @param buffer     结果容器
     * @return 进制字符串
     */
    public static String decimalToOther(int decimal, BinaryType targetType, StringBuffer buffer) {
        buffer.delete(0, buffer.length());
        if (decimal < 1) {
            return null;
        }
        decimalToOther(decimal / targetType.value, targetType, buffer);
        buffer.append(decimal % targetType.value);
        return buffer.toString();
    }

    /**
     * 使用栈来进行十进制转二进制
     *
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
            buffer.append(r % 2);
        }
        return buffer.toString();
    }

    /**
     * 运用递归来转二进制
     *
     * @param decimal 十进制
     * @param buffer  存储容器
     */
    public static String decimalToBinaryWithRecursion(int decimal, StringBuffer buffer) {
        buffer.delete(0, buffer.length());
        //定义个参数来知道递归到第几层了
        //如果传入的是负数的话
        //转化为补码（正数的绝对值除符号位按位取反+1）
        if (decimal < 0) {
            int complement = ((-decimal) ^ Integer.MAX_VALUE) + 1;
            decimalToBinaryWithRecursion(complement, buffer);
        }
        //递结束条件
        if (decimal < 1) {
            return null;
        }
        //递操作
        decimalToBinaryWithRecursion(decimal / 2, buffer);
        //归操作
        buffer.append(decimal % 2);
        return buffer.toString();
    }

    /**
     * 使用位运算来进行十进制转二进制
     *
     * @param decimal 十进制
     * @param buffer  存储容器
     */
    public static String decimalToBinaryWithBitArithmetic(int decimal, StringBuffer buffer) {
        buffer.delete(0, buffer.length());
        //定义个参数来知道递归到第几层了
        //如果传入的是负数的话
        //转化为补码（正数的绝对值除符号位按位取反+1）
        if (decimal < 0) {
            int complement = ((-decimal) ^ Integer.MAX_VALUE) + 1;
            decimalToBinaryWithBitArithmetic(complement, buffer);
        }
        //结束条件
        if (decimal == 0) {
            return null;
        }
        //递
        decimalToBinaryWithBitArithmetic(decimal >> 1, buffer);
        //归
        buffer.append(decimal & 1);
        return buffer.toString();
    }
}
