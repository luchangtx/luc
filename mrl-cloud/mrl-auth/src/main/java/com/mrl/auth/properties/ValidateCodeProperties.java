package com.mrl.auth.properties;

import lombok.Data;

/**
 * 图片验证码配置信息类
 * @author luc
 * @date 2020/7/2310:42
 */
@Data
public class ValidateCodeProperties {
    /**
     * 验证码有效时间，单位s
     */
    private Long time=120L;
    /**
     * 验证码类型，png/gif
     */
    private String type="png";
    /**
     * 图片宽度 px
     */
    private Integer width=130;
    /**
     * 图片高度 px
     */
    private Integer height=48;
    /**
     * 验证码位数
     */
    private Integer length=4;
    /**
     * 验证码值类型
     * 1.数字+字母
     * 2.纯数字
     * 3.纯字母
     */
    private Integer charType=2;
}
