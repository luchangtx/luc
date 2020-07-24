package com.mrl.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置响应
 * @author luc
 * @date 2020/7/2111:04
 */
public class ResponseUtil {
    /**
     * @param response    HttpServletResponse
     * @param contentType String
     * @param status      http状态码
     * @param value       响应内容
     * @throws IOException
     */
    public static void makeResponse(HttpServletResponse response,String contentType,int status,Object value) throws IOException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(JSONObject.toJSONString(value).getBytes());
    }

    /**
     * 封装前端分页表格所需数据
     * @param pageInfo
     * @return
     */
    public static Map<String,Object> getDataTable(IPage<?> pageInfo){
        Map<String,Object> data=new HashMap<>();
        data.put("rows",pageInfo.getRecords());
        data.put("total",pageInfo.getTotal());
        return data;
    }
}
