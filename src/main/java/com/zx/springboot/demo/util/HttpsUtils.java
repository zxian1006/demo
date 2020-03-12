package com.zx.springboot.demo.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HttpsUtils {

    public static CloseableHttpClient client;

    private static void init(){
        try {

            SSLContext sslContext = HttpClient.createIgnoreVerifySSL();
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1.2"}, null, new NullHostNameVerifier() );

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sf)
                .build();

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            client = HttpClients.custom().setConnectionManager(connectionManager).build();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static {
        init();
    }

    public static String httpsGet(String url, Map headers) throws Exception{
        HttpGet httpGet = new HttpGet(url);
        setHeader(httpGet, headers);
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = "";
        if( entity != null){
            body = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);

            response.close();
        }

        return body;
    }


    public static String httpsPost(String url, Map headers, Map requestBody){
        HttpPost post = new HttpPost(url);
        return httpsAction(post, headers, requestBody);
    }

    private static String httpsAction(HttpRequestBase requestBase, Map headers, Map body) {
        setHeader(requestBase,headers);
        if(!CollectionUtils.isEmpty(body)){
            HttpEntityEnclosingRequestBase base = (HttpEntityEnclosingRequestBase) requestBase;

            try {
                setBody(base, body, "UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        try {
            CloseableHttpResponse response = client.execute(requestBase);
            HttpEntity entity = response.getEntity();
            String responseBody = "";
            if( entity!=null){
                responseBody = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);

                response.close();
                return responseBody;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static void setBody(HttpEntityEnclosingRequestBase base, Map<String, String> body, String charset) throws UnsupportedEncodingException {
        if( body !=null ){
            String requestBody = JSON.toJSONString(body);
            HttpEntity entity = new ByteArrayEntity(requestBody.getBytes("UTF-8"), ContentType.APPLICATION_JSON);
            base.setEntity(entity);
        }
    }


    private static void setHeader(HttpRequestBase requestBase, Map<String, String> params) {
        if( params != null){
            Set<String> keySet = params.keySet();
            for (String key: keySet) {
                requestBase.setHeader(key, params.get(key));
            }
        }
    }

    public static Map<String, String> loginGetHeader(String url, Map headersRequest) throws Exception{
        init();

        HttpGet httpGet = new HttpGet(url);

        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        httpGet.setParams(params);

        setHeader(httpGet, headersRequest);

        CloseableHttpResponse response = client.execute(httpGet);

        Map<String, String> headersParams = new HashMap<>();
        for (Header header: response.getAllHeaders()){
            if( headersParams.keySet().contains(header.getName())){
                headersParams.put(header.getValue(), header.getValue());
            }else{
                headersParams.put(header.getName(), header.getValue());
            }
        }

        Header locationHeader = response.getFirstHeader("Location");
        String location = "";
        if(locationHeader != null){
            location = locationHeader.getValue();
        }

        HttpEntity entity = response.getEntity();
        String body = "";
        if(entity != null){
            body = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);

            response.close();
        }
        System.out.println(body);
        return headersParams;
    }

    public static String httpsFormPost(String url, Map<String, String> header, Map<String, String> requestBody) throws IOException {
        init();
        HttpPost httpPost = new HttpPost(url);

        setHeader(httpPost, header);
        setFormBody(httpPost, requestBody, "UTF-8");

        CloseableHttpResponse response = client.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String body = "";
        if( entity != null){
            body = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);

            response.close();
        }
        return body;
    }

    private static void setFormBody(HttpPost httpPost, Map<String, String> paramBody, String charset) throws UnsupportedEncodingException {
        if( paramBody != null){
            List<NameValuePair> list = new ArrayList<>();
            Set<String> keySet = paramBody.keySet();
            for (String key: keySet){
                list.add(new BasicNameValuePair(key, paramBody.get(key)));
            }

            if( list.size()>0){
                HttpEntity entity =new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(entity);
            }
        }
    }

    public static Map<String, String> loginPostHeader(String url, Map<String, String> headers, Map<String, String> requsetBody) throws IOException {
        init();
        HttpPost httpPost = new HttpPost(url);

        setHeader(httpPost, headers);
        setFormBody(httpPost, requsetBody, "UTF-8");

        HttpParams params = new BasicHttpParams();
        params.setParameter("http.protocol.handle-redirects", false);
        httpPost.setParams(params);

        CloseableHttpResponse response = client.execute(httpPost);

        Map<String, String> headersParams = new HashMap<>();
        for (Header header: response.getAllHeaders()){

            headersParams.put(header.getName(), header.getValue());
        }
        HttpEntity entity = response.getEntity();
        String body = "";
        if( entity != null){
            body = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);

            if(!StringUtils.isEmpty(body)){
                Map<String, String> body2 = JSON.parseObject(body, Map.class);
                headersParams.put("code", body2.get("code"));
                if( body2.containsKey("message")){
                    headersParams.put("message", body2.get("message"));
                }
            }

            response.close();
        }
        System.out.println(body);
        return headersParams;
    }
}
