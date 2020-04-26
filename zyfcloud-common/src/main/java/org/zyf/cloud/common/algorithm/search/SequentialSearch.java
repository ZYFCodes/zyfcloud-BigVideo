package org.zyf.cloud.common.algorithm.search;

/**
 * 描述：顺序查找查找（优化）
 *
 * @author yanfengzhang
 * @date 2019-11-11 16:01
 */
public class SequentialSearch {
    /**
     * 顺序查找查找算法 :对常规顺序查找放置“哨兵”，免去查找过程中每一次比较后都要判断查找是否越界的小技巧
     *
     * @param srcArray 数组
     * @param key      需要查找的元素(只返回第一个)
     * @return key的数组下标，没找到返回0
     */
    public static int sequentialSearch(int[] srcArray, int endOfArray, int key) {
        int i;
        /**设置srcArray[0]为关键字值，即“哨兵”*/
        srcArray[0] = key;
        /**循环从数组尾部开始*/
        i = endOfArray;
        while (key != srcArray[i]) {
            i--;
        }

        /**返回0则说明查找失败*/
        return i;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        int[] srcArray = {86, 95, 2, 5, 69, 2, 3, 8, 5, 6, 25, 16, 86, 24, 59, 59};
        int endOfArray = srcArray.length - 1;
        System.out.println(sequentialSearch(srcArray, endOfArray, 16));
    }
}
