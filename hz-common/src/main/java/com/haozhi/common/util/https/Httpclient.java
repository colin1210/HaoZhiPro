package com.haozhi.common.util.https;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/** 
 * @Description httpclient处理http请求和https请求 
 */  
public class Httpclient {  
  
    /** 
     * @Description 处理http请求的post方法 
     * @param url 
     *            :url 
     * @param params 
     *            :参数 
     * @return 返回的字符串 
     */  
    public static String post(String url, Hashtable<String, String> params) {  
        CloseableHttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = "";  
        try {  
            httpClient = HttpClients.createDefault();  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(4000).setConnectTimeout(4000).build();  
            httpPost = new HttpPost(url);  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> ps = new ArrayList<NameValuePair>();  
            for (String pKey : params.keySet()) {  
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(ps, "utf-8"));  
            CloseableHttpResponse response = httpClient.execute(httpPost);  
            HttpEntity httpEntity = response.getEntity();  
  
            result = EntityUtils.toString(httpEntity, "utf-8");  
  
        } catch (ClientProtocolException e) {  
            result = "";  
        } catch (IOException e) {  
            result = "";  
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                result = "";
            }
        }  
        return result;  
    }  
  
    /** 
     * @Description 处理http请求的get方法 
     * @param url 
     *            :url 
     * @param params 
     *            :参数 
     * @return 返回的字符串 
     */  
    public static String get(String url, Hashtable<String, String> params) {  
        CloseableHttpClient httpClient = null;  
        HttpGet httpGet = null;  
  
        String result = "";  
        try {  
            httpClient = HttpClients.createDefault();  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(4000).setConnectTimeout(4000).build();  
            String ps = "";  
            for (String pKey : params.keySet()) {  
                if (!"".equals(ps)) {  
                    ps = ps + "&";  
                }  
                // 处理特殊字符，%替换成%25，空格替换为%20，#替换为%23  
                String pValue = params.get(pKey).replace("%", "%25")  
                        .replace(" ", "%20").replace("#", "%23");  
                ps += pKey + "=" + pValue;  
            }  
            if (!"".equals(ps)) {  
                url = url + "?" + ps;  
            }  
            httpGet = new HttpGet(url);  
            httpGet.setConfig(requestConfig);  
            CloseableHttpResponse response = httpClient.execute(httpGet);  
            HttpEntity httpEntity = response.getEntity();  
            result = EntityUtils.toString(httpEntity, "utf-8");  
        } catch (ClientProtocolException e) {  
            result = "";  
        } catch (IOException e) {  
            result = "";  
        } catch (Exception e) {  
            result = "";  
        } finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                result = "";
            }
        }  
        return result;  
    }  
  
    /** 
     * @Description 处理https请求的post方法 
     * @param url 
     *            :url 
     * @param params 
     *            :参数 
     * @return 返回的字符串 
     */  
    public static String postSSL(String url, Hashtable<String, String> params) {  
        CloseableHttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = "";  
        try {  
            httpClient = (CloseableHttpClient) wrapClient();  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(18000).setConnectTimeout(18000).build();  
            httpPost = new HttpPost(url);  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> ps = new ArrayList<NameValuePair>();  
            for (String pKey : params.keySet()) {  
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(ps, "utf-8"));  
            CloseableHttpResponse response = httpClient.execute(httpPost);  
            HttpEntity httpEntity = response.getEntity();  
  
            result = EntityUtils.toString(httpEntity, "utf-8");  
  
        } catch (ClientProtocolException e) {  
            result = "";  
        } catch (IOException e) {  
            result = "";  
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
            try {  
                if (httpClient != null) {
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                result = "";  
            }  
        }  
        return result;  
    }  
  
    /** 
     * @Description 处理https请求的get方法 
     * @param url 
     *            :url 
     * @param params 
     *            :参数 
     * @return 返回的字符串 
     */  
    public static String getSSL(String url, Hashtable<String, String> params) {  
        CloseableHttpClient httpClient = null;  
        HttpGet httpGet = null;  
  
        String result = "";  
        try {  
            httpClient = (CloseableHttpClient) wrapClient();  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(4000).setConnectTimeout(4000).build();  
            String ps = "";  
            for (String pKey : params.keySet()) {  
                if (!"".equals(ps)) {  
                    ps = ps + "&";  
                }  
                // 处理特殊字符，%替换成%25，空格替换为%20，#替换为%23  
                String pValue = params.get(pKey).replace("%", "%25")  
                        .replace(" ", "%20").replace("#", "%23");  
                ps += pKey + "=" + pValue;  
            }  
            if (!"".equals(ps)) {  
                url = url + "?" + ps;  
            }  
            httpGet = new HttpGet(url);  
            httpGet.setConfig(requestConfig);  
            CloseableHttpResponse response = httpClient.execute(httpGet);  
            HttpEntity httpEntity = response.getEntity();  
            result = EntityUtils.toString(httpEntity, "utf-8");  
        } catch (ClientProtocolException e) {  
            result = "";  
        } catch (IOException e) {  
            result = "";  
        } catch (Exception e) {  
            result = "";  
        } finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            try {  
                if (httpClient != null) {
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                result = "";  
            }  
        }  
        return result;  
    }  

    /** 
     * @Description 创建一个不进行正式验证的请求客户端对象 不用导入SSL证书 
     * @return HttpClient 
     */  
    public static HttpClient wrapClient() {  
        try {  
            SSLContext ctx = SSLContext.getInstance("TLS");  
            X509TrustManager tm = new X509TrustManager() {  
                public X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
  
                public void checkClientTrusted(X509Certificate[] arg0,  
                        String arg1) throws CertificateException {  
                }  
  
                public void checkServerTrusted(X509Certificate[] arg0,  
                        String arg1) throws CertificateException {  
                }  
            };  
            ctx.init(null, new TrustManager[] { tm }, null);  
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(  
                    ctx, NoopHostnameVerifier.INSTANCE);  
            CloseableHttpClient httpclient = HttpClients.custom()  
                    .setSSLSocketFactory(ssf).build();  
            return httpclient;  
        } catch (Exception e) {  
            return HttpClients.createDefault();  
        }  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
  
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxee2025c28e25e73a&secret=80ae453eb1aaf182917e56ec1b2057af&code=021mIT100ZfzoF1Z51200sU5200mIT1l&grant_type=authorization_code";
        Hashtable<String, String> params= new  Hashtable<String, String>();
        String result = Httpclient.postSSL(url,params);
        System.out.println(result);
   
    }  
  
} 