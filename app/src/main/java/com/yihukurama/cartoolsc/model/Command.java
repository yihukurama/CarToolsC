package com.yihukurama.cartoolsc.model;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class Command {
    public static final String SKIPDAOHAN = "0";
    public static final String SKIPDUOMEITI = "1";
    public static final String SKIPDIANTAI = "2";
    public static final String SKIPSHUSHI = "3";
    public static final String SKIPTONGXUN = "4";
    public static final String SKIPFUWU = "5";

    public static final String YINLIANG = "6";//冒号后面是滑动占屏幕的百分比，正数为上滑，负数下滑
    public static final String SAVEADDRESS = "8";
    public static final String EXIT = "9";

    public static final String WENDUJIA = "19";//温度加1
    public static final String WENDUJIAN = "20";//温度减1

    public static final String DANGQIANWENDU = "21";//把当前温度发给手机
    public static final String ENDGESTURE = "22";
}