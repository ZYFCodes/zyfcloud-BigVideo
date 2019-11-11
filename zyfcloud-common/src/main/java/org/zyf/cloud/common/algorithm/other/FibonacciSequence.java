package org.zyf.cloud.common.algorithm.other;

import java.util.HashMap;

/**
 * 描述：斐波那契数列全算法+优化
 * * ***费波那契数列由0和1开始，之后的费波那契系数就是由之前的两数相加而得出。首几个费波那契系数是：
 * * ***0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, ……
 *
 * @author yanfengzhang
 * @date 2019-11-11 15:46
 */
public class FibonacciSequence {
    private static HashMap<Integer, Long> map = new HashMap<>();
    private static long[] cacheArray = new long[8000 + 1];
    private static long[][] initMatirx = {{1, 1}, {1, 0}};
    /**
     * 单位矩阵
     */
    private static long[][] unitMatrix = {{1, 0}, {0, 1}};

    private static void indexValidity(int index) {
        if (index <= 0) {
            throw new RuntimeException("输入参数小于1");
        }
    }

    /**
     * 功能描述：使用递归，也是大多数人直接想到的方法
     * 简单易懂，清晰明了。但是缺点就是效率非常低，时间复杂度为O(index)=(2^h)-1=2^index
     * 这个方法传入n=50，电脑上耗时高达35.549秒，性能在这个时候已经很低了
     *
     * @author yanfengzhang
     * @date 2019-11-05 20:31
     */
    public static long getFibonacciByRecursive(int index) {
        indexValidity(index);
        if (index == 1 || index == 2) {
            return 1;
        }
        return getFibonacciByRecursive(index - 2) + getFibonacciByRecursive(index - 1);
    }

    /**
     * 功能描述：优化算法1------采用递归+HashMap缓存
     * 将大量的重复计算使用HashMap进行缓存，传入n=8000的时候也不过花费80毫秒
     * 存在的问题：1.map的key和value分别是Integer和Long对象，会有自动装拆箱的性能问题。如果在一个循环体自动装拆箱，会创建大量无用的中间对象，这样会增加GC压力，拉低程序的性能。
     * 2.map还会有自动扩容、取hashCode、hash冲突之后可能最坏Ologn的时间复杂度等
     * 这个方法n=8000时，电脑上耗时80毫秒，性能相比而言提高了很多
     *
     * @param index 对应第n位下标
     * @author yanfengzhang
     * @date 2019-11-06 14:58
     */
    public static long getFibonacciByRecursiveAndHashMap(int index) {
        indexValidity(index);
        if (index == 1 || index == 2) {
            return 1;
        }
        if (!map.containsKey(index)) {
            map.put(index, getFibonacciByRecursiveAndHashMap(index - 2) + getFibonacciByRecursiveAndHashMap(index - 1));
        }
        return map.get(index);
    }

    /**
     * 功能描述：优化算法2------采用递归+数组缓存
     * 存在的问题：n=10000报StackOverflowError栈溢出的错误，原因：当一个函数被Java程序调用的时候，就会在调用栈上分配栈帧。栈帧包含被调用函数的参数、局部变量和返回地址。
     * 返回地址指示了当函数执行完毕之后下一步该执行哪里。如果创建栈帧时没有内存空间，JVM就会抛出StackOverflowError。而递归就是无限制的调用自身函数，然后就栈溢出了。
     * 这个方法n=8000时，电脑上耗时1.06毫秒，性能相比而言提高了很多，但n过大会有报错
     *
     * @param index 对应第n位下标
     * @author yanfengzhang
     * @date 2019-11-06 15:39
     */
    public static long getFibonacciByRecursiveAndArray(int index) {
        indexValidity(index);
        if (index == 1 || index == 2) {
            return cacheArray[index] = 1;
        }
        if (cacheArray[index] == 0) {
            cacheArray[index] = getFibonacciByRecursiveAndArray(index - 1) + getFibonacciByRecursiveAndArray(index - 2);
        }
        return cacheArray[index];
    }

    /**
     * 功能描述：优化算法3------数组缓存+顺序迭代
     * 先定义好前两个值，后面的值是前两个值的和，一直顺序递推上去即可。完全消除了递归的影响了
     * 这个方法n=10000000(1000w)时，耗时41毫秒，性能完全妥妥的。
     * 思考：1.数组的长度只能是int类型的，太长接收不了，限制了n的大小。
     * 2.前面的两种种优化方案和本次优化方案中，缓存都是要占内存的，在只用计算后面的结果的时候，前面根本就不需要一直保存了，那不如直接取消掉缓存？
     *
     * @param index 对应第n位下标
     * @author yanfengzhang
     * @date 2019-11-06 18:06
     */
    public static long getFibonacciByArrayCacheAndRecursive(int index) {
        indexValidity(index);
        if (index == 1 || index == 2) {
            return 1;
        }
        long temp[] = new long[index + 1];
        temp[0] = 0;
        temp[1] = 1;
        temp[2] = 1;
        for (int i = 3; i <= index; ++i) {
            temp[i] = temp[i - 1] + temp[i - 2];
        }
        return temp[index];
    }

    /**
     * 功能描述：优化算法4------直接取消掉缓存,采用迭代
     * 这个方法n=n=10000000(1000w)，耗时仅为6毫秒! 注意这不是优化的极限
     *
     * @param index 对应第n位下标
     * @author yanfengzhang
     * @date 2019-11-06 18:25
     */
    public static long getFibonacciByIteration(int index) {
        indexValidity(index);
        if (index == 1 || index == 2) {
            return 1;
        }
        long beforeLastFibonacci = 1;
        long lastFibonacci = 1;
        long currentFibonacci = 0;
        for (int i = 3; i <= index; i++) {
            currentFibonacci = beforeLastFibonacci + lastFibonacci;
            beforeLastFibonacci = lastFibonacci;
            lastFibonacci = currentFibonacci;
        }
        return currentFibonacci;
    }

    /**
     * 功能描述：优化算法5------公式解法
     * 用初等代数推导
     * 存在的问题：涉及到无理数的问题以及计算机会出现的精度丢失问题，在上述方法输入n>71的时候答案就已经不准确了
     * 这个方法n=1和n=2100000000的耗时基本一样，时间复杂度为O(1) 不算最优，计算结果也不可靠
     *
     * @param index 对应第n位下标
     * @author yanfengzhang
     * @date 2019-11-06 18:35
     */
    public static long getFibonacciByFormula(int index) {
        double temp = Math.sqrt(5.0);
        return (long) ((Math.pow((1 + temp) / 2, index) - Math.pow((1 - temp) / 2, index)) / temp);
    }


    /**
     * 功能描述：优化算法6------矩阵解法
     * 把公式中可以抽象出来的地方进行矩阵计算，输入n=10000000(1000w)，耗时在280毫秒左右
     * * @param index 对应第n位下标
     *
     * @param index 对应第n位下标
     * @author yanfengzhang
     * @date 2019-11-06 18:47
     */
    public static long getFibonacciByMatirx(int index) {
        indexValidity(index);
        if (index == 1 || index == 2) {
            return 1;
        }
        long[][] tem = initMatirx;
        for (int i = 1; i < index - 2; i++) {
            tem = matirxMulti(tem, initMatirx);
        }
        return tem[0][0] + tem[1][0];
    }

    private static long[][] matirxMulti(long[][] a, long[][] b) {
        long[][] temp = new long[2][2];
        temp[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        temp[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        temp[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        temp[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        return temp;
    }

    /**
     * 功能描述：优化算法7------矩阵解法+快速幂
     * 时间复杂度优化到O(logn)，输入n=2100000000(21亿)耗时0.2毫秒，这应该就是斐波那契问题的最优解了
     *
     * @param index 对应第n位下标
     * @author yanfengzhang
     * @date 2019-11-06 19:03
     */
    public static long getFibonacciByMatirxAndFastpower(int index) {
        indexValidity(index);
        if (index == 1 || index == 2) {
            return 1;
        }
        long[][] result = unitMatrix;
        long[][] tem = initMatirx;
        int m = index - 2;
        while (m != 0) {
            if ((m & 1) == 1) {
                result = matirxMulti(tem, result);
            }
            tem = matirxMulti(tem, tem);
            m >>= 1;
        }
        return result[0][0] + result[1][0];
    }
}
