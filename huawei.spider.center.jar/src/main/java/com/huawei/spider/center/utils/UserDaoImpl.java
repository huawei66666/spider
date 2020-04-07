package com.huawei.spider.center.utils;

public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("save");
    }

    @Override
    public void delete(Integer id) {
        System.out.println("delete");
    }
}
