package com.mrl.server.system.config;

import com.mrl.common.utils.DateUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author luc
 * @date 2020/7/2316:52
 */
public class P6spySqlFormatConfigure implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtils.isNotBlank(sql)? DateUtil.formatFullSplitTime(LocalDateTime.now())+
                " | 耗时 "+ elapsed+" ms | SQL 语句："+
                StringUtils.LF+sql.replaceAll("[\\s]+]",StringUtils.SPACE)+";":StringUtils.EMPTY;
    }
}
