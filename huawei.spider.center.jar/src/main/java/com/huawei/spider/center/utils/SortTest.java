package com.huawei.spider.center.utils;

/**
 * 常见排序算法
 */
public class SortTest {

    public static void main(String[] args) {
        int[] array = {30, 20, 2, 8, 6, 10, 1, 4, 3};
        SortTest sortTest = new SortTest();
        sortTest.bubbleSort(array);
        for (int a : array) {
            System.out.println(a);
        }
    }

    /**
     * 冒泡排序
     * @param array
     */
    public void bubbleSort(int[] array) {
        if (array == null || array.length == 0) return;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     * @param array
     */
    public void selectSort(int[] array) {
        if (array == null || array.length == 0) return;
        for (int i = 0; i < array.length; i++) {
            int index = 0;
            for (int j = 0; j < array.length - i; j++) {
                if (array[index] < array[j]) {// 循环一遍找出最大的数
                    index = j;
                }
            }
            // 放在数组末尾
            int temp = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[index];
            array[index] = temp;
        }
    }

    /**
     * 快速排序
     * @param array
     */
    public void fastSort(int[] array) {
        if (array == null || array.length == 0) return;




    }

}
