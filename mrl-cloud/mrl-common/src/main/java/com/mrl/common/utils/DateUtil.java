package com.mrl.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @author luc
 * @date 2020/7/2316:55
 */
public class DateUtil {
    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static String formatFullTime(LocalDateTime localDateTime){
        return formatFullTime(localDateTime,FULL_TIME_PATTERN);
    }
    public static String formatFullSplitTime(LocalDateTime localDateTime){
        return formatFullTime(localDateTime,FULL_TIME_SPLIT_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime,String pattern){
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dtf);
    }

    public static String getDateFormat(Date date,String dateFormatType){
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormatType, Locale.CHINA);
        return sdf.format(date);
    }
    public static String formatCSTTime(String date,String format) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat(CST_TIME_PATTERN,Locale.US);
        Date usDate=sdf.parse(date);
        return getDateFormat(usDate,format);
    }
    public static String formatInstant(Instant instant,String format){
        LocalDateTime localDateTime=LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }
}
