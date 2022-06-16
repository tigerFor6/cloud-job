package com.wisdge.cloud.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;


/**
 * httpclient 请求工具类
 * httpclient 连接池
 */
public class HttpClientUtils {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * http链接池
     */
    private static PoolingHttpClientConnectionManager cm = null;
    /**
     * http客户端
     */
    private static CloseableHttpClient httpClient = null;
    /**
     * from-请求/响应   类型
     */
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=UTF-8";
    /**
     * json-请求/响应   类型
     */
    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    /**
     * 长连接时间保持设置
     */
    private static final int HTTP_DEFAULT_KEEP_TIME = 60000;

    /**
     * 1、MaxtTotal是整个池子的大小； 2、DefaultMaxPerRoute是根据连接到的主机对MaxTotal的一个细分；比如：
     * MaxtTotal=400 DefaultMaxPerRoute=200
     * 而我只连接到http://sishuok.com时，到这个主机的并发最多只有200；而不是400； 而我连接到http://sishuok.com 和
     * http://qq.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；
     * 所以起作用的设置是DefaultMaxPerRoute。
     */
    private static final int DEFAULT_MAX_PER_ROUTE = 1000;
    /**
     * 设置连接池的大小
     */
    private static final int MAX_TOTAL = 1000;

    /**
     * 设置链接超时
     */
    private static final int CONNECTION_TIMEOUT = 5000;
    /**
     * 设置等待数据超时时间
     */
    private static final int SOCKET_TIMEOUT = 15000;
    /**
     * 初始化连接池
     */
    private static synchronized void initPools() {
        if (httpClient == null) {
            cm = new PoolingHttpClientConnectionManager();
            cm.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
            cm.setMaxTotal(MAX_TOTAL);
            httpClient = HttpClients.custom().setKeepAliveStrategy(defaultStrategy).setConnectionManager(cm).build();
        }
    }
    /**
     * 长连接保持设置 Http connection keepAlive 设置
     */
    private static ConnectionKeepAliveStrategy defaultStrategy = new ConnectionKeepAliveStrategy() {
        @Override
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            int keepTime = HTTP_DEFAULT_KEEP_TIME;
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("format KeepAlive timeout exception, exception:{}", e.toString());
                        throw e;
                    }
                }
            }
            return keepTime * 1000;
        }
    };

    /**
     * 执行http post
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String post(String uri, String data) throws Exception {
        return postOrPut(uri,data,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,null,HttpPost.METHOD_NAME);
    }

    /**
     * 执行http post,带上token
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String postWithToken(String uri, String data, String token) throws Exception {
        return postOrPut(uri,data,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,token,HttpPost.METHOD_NAME);
    }

    /**
     * 执行http post
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String post(String uri, Object data) throws Exception {
        return postOrPut(uri, JSON.toJSONString(data),CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,null,HttpPost.METHOD_NAME);
    }

    /**
     * 执行http post,带上token
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String postWithToken(String uri, Object data,String token) throws Exception {
        return postOrPut(uri, JSON.toJSONString(data),CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,token,HttpPost.METHOD_NAME);
    }

    /**
     * 执行http post
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String put(String uri, String data) throws Exception {
        return postOrPut(uri,data,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,null,HttpPut.METHOD_NAME);
    }

    /**
     * 执行http post,带上token
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String putWithToken(String uri, String data, String token) throws Exception {
        return postOrPut(uri,data,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,token,HttpPut.METHOD_NAME);
    }

    /**
     * 执行http post
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String put(String uri, Object data) throws Exception {
        return postOrPut(uri, JSON.toJSONString(data),CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,null,HttpPut.METHOD_NAME);
    }

    /**
     * 执行http post,带上token
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri  请求地址
     * @param data 请求数据
     * @return String 返回是数据
     * @throws Exception 请求异常
     */
    public static String putWithToken(String uri, Object data,String token) throws Exception {
        return postOrPut(uri, JSON.toJSONString(data),CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,token,HttpPut.METHOD_NAME);
    }


    /**
     * 执行http post请求
     * @param uri 请求地址
     * @param data 请求数据
     * @param reqContentType 请求content-type
     * @param respContentType 响应content-type
     * @return String 返回请求结果
     * @throws Exception 异常
     */
    public static String postOrPut(String uri, String data, String reqContentType, String respContentType, String token, String methodName)
            throws Exception {
        long startTime = System.currentTimeMillis();
        HttpEntity httpEntity = null;
        HttpEntityEnclosingRequestBase method = null;
        String responseBody = "";
        try {
            if (httpClient == null) {
                initPools();
            }
            method = (HttpEntityEnclosingRequestBase) getRequest(uri, methodName, reqContentType,respContentType,token);
            method.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
            HttpContext context = HttpClientContext.create();
            CloseableHttpResponse httpResponse = httpClient.execute(method, context);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseBody = EntityUtils.toString(httpEntity, "UTF-8");
                log.debug("post请求信息：\n请求URL: {},\n请求参数{},\n返回状态码：{},\n返回结果：{}", uri, data,
                        httpResponse.getStatusLine().getStatusCode(), responseBody);
            }
        } catch (Exception e) {
            if (method != null) {
                method.abort();
            }
            e.printStackTrace();
            log.error("execute post request exception, url:{},exception:{}, cost time(ms):{}", uri, e.toString(),
                    System.currentTimeMillis() - startTime);
            throw e;
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consumeQuietly(httpEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("execute post request exception, url:{},exception:{}, cost time(ms):{}", uri,
                            e.toString(), System.currentTimeMillis() - startTime);
                    throw e;
                }
            }
        }
        return responseBody;
    }

    /**
     * 执行GET 请求
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri 请求链接
     * @return String 请求结果
     * @throws Exception 异常
     */
    public static String get(String uri) throws Exception {
        return getOrDelete(uri,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,null,HttpGet.METHOD_NAME);
    }

    /**
     * 执行GET,带上token认证信息
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri 请求链接
     * @return String 请求结果
     * @throws Exception 异常
     */
    public static String getWithToken(String uri,String token) throws Exception {
        return getOrDelete(uri,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,token,HttpGet.METHOD_NAME);
    }

    /**
     * 执行DELETE 请求
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri 请求链接
     * @return String 请求结果
     * @throws Exception 异常
     */
    public static String delete(String uri) throws Exception {
        return getOrDelete(uri,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,null,HttpDelete.METHOD_NAME);
    }

    /**
     * 执行DELETE,带上token认证信息
     * 请求 默认采用Content-Type：application/json
     * 响应 默认采用Content-Type：application/json
     * @param uri 请求链接
     * @return String 请求结果
     * @throws Exception 异常
     */
    public static String deleteWithToken(String uri,String token) throws Exception {
        return getOrDelete(uri,CONTENT_TYPE_JSON,CONTENT_TYPE_JSON,token,HttpDelete.METHOD_NAME);
    }

    /**
     * 执行GET 请求
     * @param uri 请求链接
     * @param reqContentType 请求ContentType
     * @param respContentType 响应ContentType
     * @return String 响应数据
     * @throws Exception 异常
     */
    public static String getOrDelete(String uri, String reqContentType, String respContentType,String token,String methodName) throws Exception {
        long startTime = System.currentTimeMillis();
        HttpEntity httpEntity = null;
        HttpRequestBase method = null;
        String responseBody = "";
        try {
            if (httpClient == null) {
                initPools();
            }
            method = getRequest(uri, methodName, reqContentType, respContentType, token);
            HttpContext context = HttpClientContext.create();
            CloseableHttpResponse httpResponse = httpClient.execute(method, context);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseBody = EntityUtils.toString(httpEntity, "UTF-8");
                log.debug("get请求信息：\n请求URL: {},\n返回状态码：{},\n返回结果：{}", uri,
                        httpResponse.getStatusLine().getStatusCode(), responseBody);
            }
        } catch (Exception e) {
            if (method != null) {
                method.abort();
            }
            e.printStackTrace();
            log.error("execute get request exception, url:{},exception:{}, cost time(ms):{}", uri, e.toString(),
                    System.currentTimeMillis() - startTime);
            throw e;
        } finally {
            if (httpEntity != null) {
                try {
                    EntityUtils.consumeQuietly(httpEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("execute get request exception, url:{},exception:{}, cost time(ms):{}", uri,
                            e.toString(), System.currentTimeMillis() - startTime);
                    throw e;
                }
            }
        }
        return responseBody;
    }

    /**
     * 创建请求
     * @param uri 请求url
     * @param methodName 请求的方法类型
     * @param reqContentType 请求ContentType
     * @param respContentType 响应ContentType
     * @return HttpRequestBase http请求的基本实现对象
     */
    private static HttpRequestBase getRequest(String uri, String methodName, String reqContentType,
                                              String respContentType,String token) {
        HttpRequestBase method = null;

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT).setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                .setExpectContinueEnabled(false).build();
        if (HttpPut.METHOD_NAME.equalsIgnoreCase(methodName)) {
            method = new HttpPut(uri);
        } else if (HttpPost.METHOD_NAME.equalsIgnoreCase(methodName)) {
            method = new HttpPost(uri);
        } else if (HttpGet.METHOD_NAME.equalsIgnoreCase(methodName)) {
            method = new HttpGet(uri);
        } else {
            method = new HttpPost(uri);
        }
        if (StringUtils.isBlank(reqContentType)) {
            reqContentType = CONTENT_TYPE_FORM;
        }
        if (StringUtils.isBlank(respContentType)) {
            respContentType = CONTENT_TYPE_JSON;
        }
        // 请求类型
        method.addHeader("Content-Type", reqContentType);
        method.addHeader("Accept", respContentType);
        method.setConfig(requestConfig);

        //设置请求的token认证信息
        if(StringUtils.isNotBlank(token)){
            method.addHeader("Authorization", "Bearer " + token);
        }
        return method;
    }

    /**
     * 获取 httpClient
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 获取 httpClient连接池
     * @return PoolingHttpClientConnectionManager
     */
    public static PoolingHttpClientConnectionManager getHttpConnectionManager() {
        return cm;
    }

}
