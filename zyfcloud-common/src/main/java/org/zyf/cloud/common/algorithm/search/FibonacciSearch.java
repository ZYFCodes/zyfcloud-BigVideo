package org.zyf.cloud.common.algorithm.search;

/**
 * 描述：斐波那契查找
 *
 * @author yanfengzhang
 * @date 2019-11-11 15:57
 */
public class FibonacciSearch {
    /**
     * 斐波那契查找是根据斐波那契序列的特点对有序表进行分割的。
     *
     * @param srcArray 有序数组
     * @param key      查找元素
     * @return key的数组下标，没找到返回-1
     */
    public static int fibonacciSearch(int[] srcArray, int n, int key) {
        int[] f = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34};
        int low = 1;
        int high = n;
        int k = 0;
        int mid;
        /* 计算n位于斐波那契数列的位置 */
        while (n > f[k] - 1) {
            k++;
        }
        while (low <= high) {
            mid = low + f[k - 1] - 1;
            if (key < srcArray[mid]) {
                high = mid - 1;
                k = k - 1;
            } else if (key > srcArray[mid]) {
                low = mid + 1;
                k = k - 2;
            } else {
                return Math.min(mid, n);
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(fibonacciSearch(arr, arr.length, 5));
        System.out.println(fibonacciSearch(arr, arr.length, 1));
        System.out.println(fibonacciSearch(arr, arr.length, 9));
    }
}
