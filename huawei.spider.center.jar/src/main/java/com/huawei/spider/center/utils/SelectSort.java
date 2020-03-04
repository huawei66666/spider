package com.huawei.spider.center.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class SelectSort {


    public static void main(String[] args) {
//        int[] array = {30, 20, 2, 8, 6, 10, 1, 4, 3};
//        SelectSort selectSort = new SelectSort();
//        selectSort.sort(array);
//        for (int a : array) {
//            System.out.println(a);
//        }


        SelectSort s = new SelectSort();

        InnerClass in = new SelectSort.InnerClass();
        in.doit();
        in.outSort();
        List<SelectSort> list = new ArrayList<>();

    }

    private static int aa = 12;

    public static class InnerClass {

        private InnerClass doit() {
            System.out.println(aa);
            System.out.println("inner doit");
            return new InnerClass();
        }

        private void outSort() {
            int[] array = {30, 20, 2, 8, 6, 10, 1, 4, 3};
            //sort(array);
            for (int a : array) {
                System.out.println(a);
            }
        }

    }


}
