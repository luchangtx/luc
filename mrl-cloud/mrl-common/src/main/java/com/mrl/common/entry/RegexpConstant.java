package com.mrl.common.entry;

/**
 * @author luc
 * @date 2020/7/2416:05
 */
public class RegexpConstant {
    //手机号，只是简单校验是否为1开头的11位的数字，实际规则更复杂
    public static final String MOBILE_REG="[1]\\d{10}";
}
