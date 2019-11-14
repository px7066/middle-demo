package com.github.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>时间格式</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class DateUtil {
    private static final String DATA_PATTERN = "yyyy-MM-dd";

    private static final String TIME_PARTTERN = "yyyy-MM-dd HH:mm:ss";

    public static String dataFormat(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_PATTERN);
        return sdf.format(date);
    }

    public static String timeFormat(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PARTTERN);
        return sdf.format(date);
    }

    public static Date dateParse(String dataString) throws ParseException {
        if(dataString == null || dataString.trim().length() == 0){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_PATTERN);
        return sdf.parse(dataString);
    }

    public static Date timeParse(String dataString) throws ParseException {
        if(dataString == null || dataString.trim().length() == 0){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PARTTERN);
        return sdf.parse(dataString);
    }

}
