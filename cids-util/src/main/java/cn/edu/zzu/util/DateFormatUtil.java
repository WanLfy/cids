package cn.edu.zzu.util;

import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinhao on 2018/3/22.
 */
public class DateFormatUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DateFormatUtil.class);

    /**
     * 字符串日期转日期
     *
     * @param dateStr
     * @return 日期
     */
    public static Date dateStrToDate(String dateStr) {
        Date date = null;
        if (StringUtils.isEmpty(dateStr))
            return null;
        Map<String, String> map = new HashMap<String, String>();
        map.put("yyyy-MM-dd", "[0-9]{4}-[0-9]{2}-[0-9]{2}");
        map.put("yyyyMMdd", "[0-9]{4}[0-9]{2}[0-9]{2}");
        map.put("yyyyMMddHHmmss", "[0-9]{8}[0-9]{6}");
        map.put("yyyy-MM-dd HH:mm:ss", "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}");
        for (String key : map.keySet()) {
            Pattern p = Pattern.compile(map.get(key));
            Matcher m = p.matcher(dateStr);
            boolean dateFlag = m.matches();
            if (dateFlag) {
                SimpleDateFormat tempFormat = new SimpleDateFormat(key);
                try {
                    date = tempFormat.parse(dateStr);
                } catch (ParseException e) {
                    logger.error("日期转化失败", e);
                }
            }
        }
        return date;
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param formatStr
     * @return 字符串
     */
    public static String formatDateToString(Date date, String formatStr) {
        if (date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * 字符串日期格式化,默认格式：yyyy-MM-dd
     *
     * @param dateStr
     * @param formatStr
     * @return 字符串
     */
    public static String formatStrDateToString(String dateStr, String formatStr) {
        Date date = dateStrToDate(dateStr);
        if (date == null)
            return "";
        if (StringUtils.isEmpty(formatStr))
            formatStr = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }
}
