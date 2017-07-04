package com.nbcuni.test.cms.utils;

import com.nbcuni.test.cms.utils.httpclient.CustomHttpClient;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class UrlUtils {

    private static Random random = new Random();
    private static CustomHttpClient client = new CustomHttpClient();

    /**
     * *************************************************************************
     *
     * Sort get URLs parameters in alphabetical order e.g.
     *
     * Input: "http://testurl.com/test/service?bbb=hello&amp;aaa=Hello2";
     * Output: "http://testurl.com/test/service?aaa=Hello2&amp;bbb=hello";
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */

    public static String getUrlWithSortedGetParameters(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, true);
            buffer.append("?");
            for (Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey()).append("=")
                        .append(entry.getValue()).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     *
     * Suffle order of get URLs parameters randomly e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=Hello2&amp;ccc=ffdgfgf;
     * Output: http://testurl.com/test/service?aaa=Hello2&amp;ccc=ffdgfgf&amp;bbb=hello;
     *
     * @param url
     *         as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlWithShuffleGetParameters(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, true);
            buffer.append("?");
            List<String> keys = new ArrayList<String>(map.keySet());
            Collections.shuffle(keys);
            for (String key : keys) {
                buffer.append(key).append("=").append(map.get(key)).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters KEYs into lower case e.g.
     *
     * Input: http://testurl.com/test/service?BBB=hello&amp;AAA=Hello2&amp;CCC=ffdgfgf;
     * Output: http://testurl.com/test/service?bbb=hello&amp;aaa=Hello2&amp;ccc=ffdgfgf;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlKeyLowerCase(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, false);
            buffer.append("?");
            for (Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey().toLowerCase()).append("=")
                        .append(entry.getValue()).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters KEYs into upper case e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=Hello2&amp;ccc=ffdgfgf;
     * Output: http://testurl.com/test/service?BBB=hello&amp;AAA=Hello2&amp;CCC=ffdgfgf;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlKeyUpperCase(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, false);
            buffer.append("?");
            for (Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey().toUpperCase()).append("=")
                        .append(entry.getValue()).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters KEYs into mixed case (randomly)
     *               e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=Hello2&amp;ccc=ffdgfgf;
     * Output: http://testurl.com/test/service?bBb=hello&amp;aaA=Hello2&amp;cAA=ffdgfgf;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlKeyMixedCase(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, false);
            buffer.append("?");
            for (Entry<String, String> entry : map.entrySet()) {
                buffer.append(mixCase(entry.getKey())).append("=")
                        .append(entry.getValue()).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters VALUEs into lower case e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=Hello2&amp;ccc=FGGFRR;
     * Output: http://testurl.com/test/service?bbb=hello&amp;aaa=hello2&amp;ccc=fggfrr;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlValueLowerCase(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, false);
            buffer.append("?");
            for (Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey()).append("=")
                        .append(entry.getValue().toLowerCase()).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters VALUEs into upper case e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=hello2&amp;ccc=fggfrr;
     * Output: http://testurl.com/test/service?bbb=hello&amp;aaa=Hello2&amp;ccc=FGGFRR;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlValueUpperCase(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, false);
            buffer.append("?");
            for (Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey()).append("=")
                        .append(entry.getValue().toUpperCase()).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters VALUEs into mixed case
     *               (randomly) e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=hello2&amp;ccc=fggfrr;
     * Output: http://testurl.com/test/service?bbb=HellO&amp;aaa=HElLo2&amp;ccc=fGGFRR;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlValueMixedCase(String url) {
        String mainPart = url.split("\\?")[0];
        if (mainPart.equals(url)) {
            return url;
        } else {
            StringBuffer buffer = new StringBuffer(mainPart);
            Map<String, String> map = getUrlParameters(url, false);
            buffer.append("?");
            for (Entry<String, String> entry : map.entrySet()) {
                buffer.append(entry.getKey()).append("=")
                        .append(mixCase(entry.getValue())).append("&");
            }
            mainPart = buffer.substring(0, buffer.length() - 1);
            return mainPart;
        }
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters KEYs and VALUEs into lower case
     *               e.g.
     *
     * Input: http://testurl.com/test/service?bbB=HellO&amp;AAA=HelLO2&amp;CCC=FGgfRR;
     * Output: http://testurl.com/test/service?bbb=hello&amp;aaa=hello2&amp;ccc=fggfrr;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlKeyAndValueLowerCase(String url) {
        return getUrlValueLowerCase(getUrlKeyLowerCase(url));
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters KEYs and VALUEs into upper case
     *               e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=hello2&amp;ccc=fggfrr;
     * Output: http://testurl.com/test/service?BBB=HELLO&amp;AAA=HELLO2&amp;CCC=FGGFRR;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlKeyAndValueUpperCase(String url) {
        return getUrlValueUpperCase(getUrlKeyUpperCase(url));
    }

    /**
     * *************************************************************************
     *
     * Convert get URLs parameters KEYs and VALUEs into mixed case
     *               (randomly) e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=hello2&amp;ccc=fggfrr;
     * Output: http://testurl.com/test/service?BbB=HellO&amp;AaA=HElLo2&amp;CCc=fGGFRR;
     *
     * @param url
     *            as String
     *@return processed URL
     **********************************************************
     */
    public static String getUrlKeyAndValueMixedCase(String url) {
        return getUrlValueMixedCase(getUrlKeyMixedCase(url));
    }

    /**
     * *************************************************************************
     *
     * Getting domain from URL
     *                e.g.
     *
     * Input: http://testurl.com/test/service?bbb=hello&amp;aaa=hello2&amp;ccc=fggfrr;
     * Output: testurl.com
     *
     * @param url
     *            as String
     *
     * @return domain as string
     *
     **********************************************************
     */
    public static String getDomain(String url) {
        String domain = null;
        try {
            URL urlOject = new URL(url);
            domain = urlOject.getHost();
        } catch (MalformedURLException e) {
            Utilities.logSevereMessage("Unable to get domain from URL " + url);
        }
        return domain;
    }


    /**
     * *************************************************************************
     *
     * Ping url and return true if status code is 200 else return false.
     *                e.g.
     *
     * @param url - url as String
     * @return boolean - is URL available.
     *
     *
     **********************************************************
     */
    public static boolean pingUrl(String url) {
        HttpResponse response = client.httpGet(url);
        int statusCode = response.getStatusLine().getStatusCode();
        if (HttpStatus.SC_OK == statusCode) {
            return true;
        } else {
            return false;
        }
    }


    private static Map<String, String> getUrlParameters(String url,
                                                        Boolean sorted) {
        Map<String, String> map = null;
        if (sorted) {
            map = new TreeMap<String, String>();
        } else {
            map = new LinkedHashMap<String, String>();
        }
        String parameters = url.split("\\?")[1];
        for (String singleParam : parameters.split("&")) {
            String key = singleParam.split("=")[0];
            String value = singleParam.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

    private static String mixCase(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (random.nextBoolean()) {
                builder.append(text.substring(i, i + 1).toUpperCase());
            } else {
                builder.append(text.substring(i, i + 1).toLowerCase());
            }
        }
        return builder.toString();
    }

}
