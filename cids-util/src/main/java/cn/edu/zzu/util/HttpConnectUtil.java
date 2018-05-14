package cn.edu.zzu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http连接工具
 * Created by qinhao on 2018/5/14.
 */
public class HttpConnectUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnectUtil.class);

    public static int getResponseCode(String protocol, String url) {
        int result = -1;
        URL requestUrl = null;
        try {
            requestUrl = new URL(url);
            if ("http".equals(protocol)) {
                HttpURLConnection httpConn = (HttpURLConnection) requestUrl.openConnection();
                httpConn.setConnectTimeout(1000);
                httpConn.connect();
                result = httpConn.getResponseCode();
            }
            if ("https".equals(protocol)) {
                //忽略https证书认证
                CiTrustManager.ignoreSsl();
                //发送https请求
                HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl.openConnection();
                httpsConn.setConnectTimeout(1000);
                httpsConn.connect();
                result = httpsConn.getResponseCode();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
