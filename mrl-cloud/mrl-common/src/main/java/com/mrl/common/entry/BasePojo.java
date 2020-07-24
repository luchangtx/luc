package com.mrl.common.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luc
 * @date 2020/7/2213:43
 */
@Data
public class BasePojo {
    //插入时间
    @TableField("INSERT_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertTime;

    //最后更改时间
    @TableField("LAST_CHANGED")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastChanged;

    @JsonIgnore
    //记录状态1=正常，-1=已删除
    @TableField("STATUS")
    private int status;
}
