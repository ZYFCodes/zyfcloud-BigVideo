package org.zyf.cloud.common.algorithm.search;

/**
 * 描述：插值查找
 *
 * @author yanfengzhang
 * @date 2019-11-11 15:58
 */
public class InsertValueSearch {
    /**
     * 插值查找 :递归版本插值查找实现
     *
     * @param srcArray 有序数组
     * @param key      查找元素
     * @return key的数组下标，没找到返回-1
     */
    public static int insertValueSearch(int[] srcArray, int key, int start, int end) {
        if (key < srcArray[start] || key > srcArray[end] || start > end) {
            return -1;
        }
        if (srcArray[start] == key) {
            return start;
        }
        if (srcArray[end] == key) {
            return end;
        }
        int mid = start + (end - start) * ((key - srcArray[start]) / (srcArray[end] - srcArray[start]));
        if (srcArray[mid] > key) {
            /*说明key在前半部分*/
            return insertValueSearch(srcArray, key, start, mid - 1);
        } else if (srcArray[mid] < key) {
            /*说明key在前半部分*/
            return insertValueSearch(srcArray, key, mid + 1, end);
        } else {
            return mid;
        }
    }

    /**
     * 插值查找 :非递归版本插值查找实现
     *
     * @param srcArray 有序数组
     * @param key      查找元素
     * @return key的数组下标，没找到返回-1
     */
    public static int commonInsertValueSearch(int[] srcArray, int key) {
        int start = 0;
        int end = srcArray.length - 1;
        int mid = 0;
        if (srcArray[start] == key) {
            return start;
        }
        if (srcArray[end] == key) {
            return end;
        }
        while (start < end) {
            mid = start + (end - start) * ((key - srcArray[start]) / (srcArray[end] - srcArray[start]));
            if (key < srcArray[mid]) {
                end = mid - 1;
            } else if (key > srcArray[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public void toString(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        InsertValueSearch insertValueSearch = new InsertValueSearch();
        int[] srcArray = {2, 5, 6, 7, 9, 15, 17, 19, 22, 24, 28, 29, 36, 37, 39, 45, 56, 62, 75, 86};
        int endOfArray = srcArray.length - 1;
        System.out.print("待查找的数组：");
        insertValueSearch.toString(srcArray);
        System.out.println("递归版本插值查找24实现：" + insertValueSearch(srcArray, 24, 0, endOfArray));
        System.out.println("非递归版本插值查找24实现：" + commonInsertValueSearch(srcArray, 24));
    }
}
