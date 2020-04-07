package com.huawei.spider.center.utils;

/**
 * 常见排序算法
 */
public class SortTest {

    public static void main(String[] args) {
        int[] array = {30, 20, 2, 8, 6, 10, 1, 4, 3, 5};
        SortTest sortTest = new SortTest();
//        sortTest.bubbleSort(array);
        sortTest.quickSort(array, 0, array.length - 1);
        for (int a : array) {
            System.out.println(a);
        }
    }

    /**
     * 冒泡排序
     *
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
     *
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
     * 快速排序有没有既不浪费空间又可以快一点的排序算法呢？那就是“快速排序”啦！光听这个名字是不是就觉得很高端呢。
     * 假设我们现在对“6 1 2 7 9 3 4 5 10 8”这个10个数进行排序。首先在这个序列中随便找一个数作为基准数（不要被
     * 这个名词吓到了，就是一个用来参照的数，待会你就知道它用来做啥的了）。为了方便，就让第一个数6作为基准数吧。
     * 接下来，需要将这个序列中所有比基准数大的数放在6的右边，比基准数小的数放在6的左边，类似下面这种排列：
     * 3 1 2 5 4 6 9 7 10 8
     * 在初始状态下，数字6在序列的第1位。我们的目标是将6挪到序列中间的某个位置，假设这个位置是k。现在就需要寻找
     * 这个k，并且以第k位为分界点，左边的数都小于等于6，右边的数都大于等于6。想一想，你有办法可以做到这点吗？
     * 参考文章：https://blog.csdn.net/shujuelin/article/details/82423852
     *
     * @param array
     */
    public void quickSort(int[] array, int low, int high) {
        if (array == null || array.length == 0) return;
        if (low > high) {
            return;
        }
        int i = low, j = high, temp = array[low];// temp就是基准位
        while (i < j) {
            while (temp <= array[j] && i < j) {// 先看右边，依次往左递减
                j--;
            }
            while (temp >= array[i] && i < j) {// //再看左边，依次往右递增
                i++;
            }
            if (i < j) {// 如果满足条件则交换
                int t = array[j];
                array[j] = array[i];
                array[i] = t;
            }
        }

        // 最后将基准为与i和j相等位置的数字交换
        array[low] = array[i];
        array[i] = temp;

        quickSort(array, low, j - 1);// 递归调用左半数组
        quickSort(array, j + 1, high);// 递归调用右半数组
    }

}
