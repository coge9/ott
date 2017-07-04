package com.nbcuni.test.cms.utils.httpclient;

import com.nbcuni.test.webdriver.Utilities;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Dzianis Kulesh
 *         <p>
 *         This class is represent Http Client allowing to execute HTTP calls
 *         such as GET, POST, PUT, DELETE http requests.
 */

public class CustomHttpClient {

    private CloseableHttpClient httpClient;

    public CustomHttpClient() {
        super();
        httpClient = HttpClients.createDefault();
    }

    // ---------------------HTTP GET METHOD ---------------------------------//

    /**
     * Execute GET request, and add auth Header
     *
     * @param url - URL to which get request should be sent
     * @param userName - user name for auth
     * @param userPassword - password for auth
     *
     * @return - HttpResponse object
     */
    @SuppressWarnings("deprecated")
    public HttpResponse httpGetWithAuth(String url, String userName,
                                        String userPassword) {
        Header authHeader = BasicScheme.authenticate(
                new UsernamePasswordCredentials(userName, userPassword),
                "UTF-8", false);
        return httpGet(url, authHeader);
    }

    /**
     * Execute GET request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which GET request should be sent
     * @param headers - list of Header objects (represented HTTP headers)
     * @return - HttpResponse object
     */
    public HttpResponse httpGet(String url, Header... headers) {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            for (Header header : headers) {
                httpGet.addHeader(header);
            }
        }
        return executeMethod(httpGet);
    }

    /**
     * Execute GET request, also allow to pass some HTTP headers to request as
     * map of key/value
     *
     * @param url     - URL to which GET request should be sent
     * @param headers - Map of key/value pairs represent of HTTP Headers
     * @return - HttpResponse object
     */
    public HttpResponse httpGet(String url, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                httpGet.addHeader(header.getKey(), header.getValue());
            }
        }
        return executeMethod(httpGet);
    }

    // ---------------------HTTP POST METHOD ---------------------------------//

    /**
     * Execute POST request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which POST request should be sent
     * @param body    - body of HTTP request as String
     * @param headers - list of Header objects (represented HTTP headers)
     * @return - HttpResponse object
     */

    public HttpResponse httpPost(String url, String body, Header... headers) {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Header header : headers) {
                httpPost.addHeader(header);
            }
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(body);
        } catch (UnsupportedEncodingException e) {
            Utilities.logSevereMessage("Error during form HTTP POST body " + Utilities.convertStackTraceToString(e));
        }
        httpPost.setEntity(entity);
        return executeMethod(httpPost);
    }

    /**
     * Execute POST request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which POST request should be sent
     * @param body    - body of HTTP request as String
     * @param headers - key/value pairs represent HTTP headers
     * @return - HttpResponse object
     */

    public HttpResponse httpPost(String url, String body,
                                 Map<String, String> headers) {
        List<Header> headerList = new ArrayList<Header>();
        for (Entry<String, String> keyValuePair : headers.entrySet()) {
            Header header = new BasicHeader(keyValuePair.getKey(),
                    keyValuePair.getValue());
            headerList.add(header);
        }
        Header[] headerArray = headerList
                .toArray(new Header[headerList.size()]);
        return httpPost(url, body, headerArray);
    }

    /**
     * Execute POST request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which POST request should be sent
     * @param body    - body of HTTP request as key/value pairs
     * @param headers - list of Header objects (represented HTTP headers)
     * @return - HttpResponse object
     */
    public HttpResponse httpPost(String url, Map<String, String> body,
                                 Header... headers) {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Header header : headers) {
                httpPost.addHeader(header);
            }
        }
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        for (Entry<String, String> parametr : body.entrySet()) {
            urlParameters.add(new BasicNameValuePair(parametr.getKey(),
                    parametr.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            Utilities.logSevereMessage("Error during form HTTP POST body " + Utilities.convertStackTraceToString(e));
        }
        return executeMethod(httpPost);
    }

    /**
     * Execute POST request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which POST request should be sent
     * @param body    - body of HTTP request as key/value pairs
     * @param headers - key/value pairs represent HTTP headers
     * @return - HttpResponse object
     */
    public HttpResponse httpPost(String url, Map<String, String> body,
                                 Map<String, String> headers) {
        List<Header> headerList = new ArrayList<Header>();
        for (Entry<String, String> keyValuePair : headers.entrySet()) {
            Header header = new BasicHeader(keyValuePair.getKey(),
                    keyValuePair.getValue());
            headerList.add(header);
        }
        Header[] headerArray = headerList
                .toArray(new Header[headerList.size()]);
        return httpPost(url, body, headerArray);
    }

    // ---------------------HTTP PUT METHOD ---------------------------------//

    /**
     * Execute PUT request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which PUT request should be sent
     * @param body    - body of HTTP request as String
     * @param headers - list of Header objects (represented HTTP headers)
     * @return - HttpResponse object
     */

    public HttpResponse httpPut(String url, String body, Header... headers) {
        HttpPut httpPut = new HttpPut(url);
        if (headers != null) {
            for (Header header : headers) {
                httpPut.addHeader(header);
            }
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(body);
        } catch (UnsupportedEncodingException e) {
            Utilities.logSevereMessage("Error during form HTTP PUT body " + Utilities.convertStackTraceToString(e));
        }
        httpPut.setEntity(entity);
        return executeMethod(httpPut);
    }

    /**
     * Execute POST request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which PUT request should be sent
     * @param body    - body of HTTP request as String
     * @param headers - key/value pairs represent HTTP headers
     * @return - HttpResponse object
     */

    public HttpResponse httpPut(String url, String body,
                                Map<String, String> headers) {
        List<Header> headerList = new ArrayList<Header>();
        for (Entry<String, String> keyValuePair : headers.entrySet()) {
            Header header = new BasicHeader(keyValuePair.getKey(),
                    keyValuePair.getValue());
            headerList.add(header);
        }
        Header[] headerArray = headerList
                .toArray(new Header[headerList.size()]);
        return httpPut(url, body, headerArray);
    }

    // ------------HTTP DELETE METHOD----------------------------//

    /**
     * Execute DELETE request, also allow to pass some HTTP headers to request
     * as array of Header objects
     *
     * @param url     - URL to which DELETE request should be sent
     * @param headers - list of Header objects (represented HTTP headers)
     * @return - HttpResponse object
     */
    public HttpResponse httpDelete(String url, Header... headers) {
        HttpDelete httpDelete = new HttpDelete(url);
        if (headers != null) {
            for (Header header : headers) {
                httpDelete.addHeader(header);
            }
        }
        return executeMethod(httpDelete);
    }


    /**
     * Execute HEAD request, also allow to pass some HTTP headers to request as
     * array of Header objects
     *
     * @param url     - URL to which HEAD request should be sent
     * @param headers - list of Header objects (represented HTTP headers)
     * @return - HttpResponse object
     */
    public HttpResponse httpHead(String url, Header... headers) {
        HttpHead httpHead = new HttpHead(url);
        if (headers != null) {
            for (Header header : headers) {
                httpHead.addHeader(header);
            }
        }
        return executeMethod(httpHead);
    }

    /**
     * Execute HEAD request, also allow to pass some HTTP headers to request as
     * map of key/value
     *
     * @param url     - URL to which HEAD request should be sent
     * @param headers - Map of key/value pairs represent of HTTP Headers
     * @return - HttpResponse object
     */

    public HttpResponse httpHead(String url, Map<String, String> headers) {
        HttpHead httpHead = new HttpHead(url);
        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                httpHead.addHeader(header.getKey(), header.getValue());
            }
        }
        return executeMethod(httpHead);
    }


    // --------------GENERAL EXECUTING FOR ALL HTTP METHODS------------//
    private HttpResponse executeMethod(HttpRequestBase request) {
        HttpResponse httpResponse = null;
        try {
            httpClient.close();
        } catch (IOException e) {
            Utilities.logSevereMessage("Error during closing http client");
        }
        httpClient = HttpClients.createDefault();
        try {
            httpResponse = httpClient.execute(request);
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error during executing HTTP " + request.getMethod()
                    + e.getMessage());
        }
        return httpResponse;
    }

}
