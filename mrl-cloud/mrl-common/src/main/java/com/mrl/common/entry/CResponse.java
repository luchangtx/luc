package com.mrl.common.entry;

import com.mrl.common.exception.ErrorType;

import java.util.HashMap;

/**
 * @author luc
 * @date 2020/7/2110:15
 */
public class CResponse extends HashMap<String,Object> {

    public static final String OK ="操作成功";


    public static CResponse ok(){
        return new CResponse().message(OK);
    }
    public static CResponse fail(){
        return new CResponse().message(ErrorType.SYSTEM_ERROR.getMesg());
    }

    public CResponse message(String message){
        this.put("message",message);
        return this;
    }
    public CResponse data(Object data){
        this.put("data",data);
        return this;
    }

    @Override
    public Object put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public String getMessage(){
        return String.valueOf(get("message"));
    }
    public Object getData(){
        return get("data");
    }
}
