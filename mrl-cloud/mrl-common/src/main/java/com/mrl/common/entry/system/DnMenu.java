package com.mrl.common.entry.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mrl.common.entry.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 菜单/按钮表
 * @author luc
 * @date 2020/7/2214:04
 */
@Data
@TableName("dn_menu")
@EqualsAndHashCode(callSuper = true)
public class DnMenu extends BasePojo implements Serializable {
    private static final long serialVersionUID = -7485184031456472407L;

    //菜单/按钮ID
    @TableId(value = "MENU_ID",type = IdType.UUID)
    private String menuId;

    //上级菜单ID
    @TableField("PARENT_ID")
    private String parentId;

    //菜单/按钮名称
    @TableField("MENU_NAME")
    private String menuName;

    //对应路由path
    @TableField("PATH")
    private String path;

    //对应路由组件component
    @TableField("COMPONENT")
    private String component;

    //权限标识
    @TableField("PERMS")
    private String perms;

    //图标
    @TableField("ICON")
    private String icon;

    //类型 0菜单 1按钮
    @TableField("TYPE")
    private String type;

    //排序
    @TableField("ORDER_NUM")
    private double orderNum;
}
