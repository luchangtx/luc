package com.mrl.common.entry;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luc
 * @date 2020/7/2318:02
 */
@Data
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = 2038778386648375090L;

    /**
     * 当前页面数据量
     */
    private int pageSize=10;
    /**
     * 当前页码
     */
    private int pageNum=1;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    private String order;
}
