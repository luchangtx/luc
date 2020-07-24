package com.mrl.common.entry;

/**
 * @author luc
 * @date 2020/7/2117:15
 */
public class Constant {
    /**
     * zuul请求头token名称，不需要空格
     */
    public static final String ZUUL_TOKEN_HEADER="ZuulToken";
    /**
     * zuul请求头token值
     */
    public static final String ZUUL_TOKEN_VALUE="mrl:zuul:luc";

    /**
     * 响应数据类型
     */
    public static final String JSON_UTF8="application/json;charset=UTF-8";

    /**
     * gif类型
     */
    public static final String GIF="gif";
    /**
     * png类型
     */
    public static final String PNG="png";
    /**
     * 图形验证码key前缀
     */
    public static final String CODE_PREFIX="mrl.captcha.";
}
