package com.example.administrator.aviation.sys;

/**
 * 存放一些基础方法（单例模式，暂时没有用到这个类）
 */
public class MethodMgr {
    private static volatile MethodMgr instance;

    // 空的构造方法
    private MethodMgr(){}

    public static MethodMgr getInstance() {
        if (instance == null) {
            synchronized (MethodMgr.class) {
                if (instance == null) {
                    instance = new MethodMgr();
                }
            }
        }
        return instance;
    }
}
