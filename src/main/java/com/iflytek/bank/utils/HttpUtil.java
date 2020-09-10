package com.iflytek.bank.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author: xiewei2
 * @version: 2.0
 * @since: 2018/7/27 10:03
 * @Description:
 */
public class HttpUtil {

    private HttpUtil() {
    }

    private static final String ENCODING = "UTF-8";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String url, Map<String, String> params) {
        String content = "";
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClientFactory().getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return parseResult(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return content;
    }

    public void SubmitPost(String url,String filename1,String filename2, String filepath){

        HttpClient httpclient = new DefaultHttpClient();

        try {

            HttpPost httpPost = new HttpPost(url);


            EntityBuilder entityBuilder = EntityBuilder.create();
            entityBuilder.setParameters(new BasicNameValuePair("test", "test"));
            HttpEntity httpEntity = entityBuilder.build();
            httpPost.setEntity(httpEntity);

            HttpResponse response = httpclient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            if(statusCode == HttpStatus.SC_OK){
                System.out.println("服务器正常响应.....");
                HttpEntity resEntity = response.getEntity();
                System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据
                EntityUtils.consume(resEntity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
    }


    public static String post(String url, InputStream inputStream) {
        return post(url, null, new InputStreamEntity(inputStream));
    }

    public static String post(String url, Map<String, String> head, List<NameValuePair> pairList) throws UnsupportedEncodingException {
        UrlEncodedFormEntity basicHttpEntity = new UrlEncodedFormEntity(pairList, "UTF-8");
        basicHttpEntity.setContentType("application/x-www-form-urlencoded");
        return post(url, null, basicHttpEntity);
    }

    public static String post(String url, Map<String, String> heads, String body) {
        StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
        stringEntity.setContentType(String.valueOf(ContentType.APPLICATION_JSON));
        stringEntity.setContentEncoding(ENCODING);
        return post(url, heads, stringEntity);
    }

    public static String post(String url, String body) {
        String result = "";
        try {
            result = post(url, null, body);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static String post(String url, Map<String, String> heads, HttpEntity httpEntity) {
        String resultString = "";
        HttpPost httpPost = new HttpPost(url);
        if (heads != null && !heads.isEmpty()) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setEntity(httpEntity);
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClientFactory().getHttpClient();
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
            return parseResult(httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return resultString;
    }

    private static String parseResult(CloseableHttpResponse closeableHttpResponse) {
        String result = "";
        try {
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Header[] headers = closeableHttpResponse.getAllHeaders();
                for (Header header : headers) {
                    System.out.println(header);
                }
                InputStream inputStream = closeableHttpResponse.getEntity().getContent();
                result = IOUtils.toString(inputStream, ENCODING);
                inputStream.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
