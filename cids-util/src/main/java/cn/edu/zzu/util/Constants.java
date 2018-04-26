package cn.edu.zzu.util;

import org.springframework.context.MessageSource;

import java.util.*;

/**
 * Created by qinhao on 2018/4/26.
 */
public class Constants {
    //设置缓存常量
    public static Map constants = new HashMap();

    private static MessageSource messageSource = null;


    /**
     * 是否标志
     */
    public static final String TIsFlag = "TIsFlag";
    public static final String IF_NO = "0";// 否
    public static final String IF_YES = "1";// 是

    /*********定义变量**********/
    static {
        Map map = null;
        ArrayList list = null;

        map = new HashMap();
        list = new ArrayList();
        list.add(addValue(IF_NO, "IF_NO"));
        list.add(addValue(IF_YES, "IF_YES"));
        map.put("name", TIsFlag);
        map.put("values", list);
        constants.put(TIsFlag, map);
    }

    /*********定义变量**********/
    public static void setMessageSource(MessageSource messageSource) {
        Constants.messageSource = messageSource;
    }

    //return map value
    private static Map addValue(String constkey, String messKey) {
        Map map = new HashMap();
        map.put("constkey", constkey);
        map.put("messKey", messKey);
        return map;
    }

    /**
     * 获取常量values:list
     *
     * @param name constant name
     */
    private static List getValues(String name) {
        //1.获取TIsFlag对应的map
        Map constant = (Map) constants.get(name);
        if (constant != null) {
            //2.获取map里的mapList
            return (List) constant.get("values");
        }
        return null;
    }

    /**
     * 获取字典项值对应的描述
     *
     * @param dictName
     * @param dictValue
     * @return
     */
    public static String getDictLabel(String dictName, String dictValue, Locale locale) {
        Map<String, String> map = getDictListMap(dictName, locale);
        Iterator keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (key.equals(dictValue)) {
                return map.get(key);
            }
        }
        return "";
    }

    /**
     * 根据字典常量名获取常量Map{key,desc}
     *
     * @param dictName
     * @param locale
     * @return
     */
    public static Map<String, String> getDictListMap(String dictName, Locale locale) {
        if (Constants.messageSource == null) {
            throw new RuntimeException("Constants's messageSource is not set !");
        }
        //改成有序的Map
        Map<String, String> map = new LinkedHashMap<String, String>();
        List<Map> mapList = Constants.getValues(dictName);
        for (Map<String, Object> val : mapList) {
            String value = Constants.messageSource.getMessage(dictName + "." + (String) val.get("messKey"), null, locale);
            String key = (String) val.get("constkey");
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取所有字典
     *
     * @return
     */
    public static Map getAllDict(Locale locale) {
        Map<String, Object> map = new LinkedHashMap();
        Iterator keys = constants.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, getDictListMap(key, locale));
        }
        return map;
    }
}
