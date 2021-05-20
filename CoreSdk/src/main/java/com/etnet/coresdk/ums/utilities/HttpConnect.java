package com.etnet.coresdk.ums.utilities;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class HttpConnect {
    private static final String TAG = "HttpConnect";
    public static int configConnectionTimeout = 5000;
    private static final Logger logger = Logger.getLogger(HttpConnect.class);

    private static PoolingHttpClientConnectionManager cm;
    private static CloseableHttpClient httpClient;

    public static String executePostByHttpURLConnection(String targetURL) {
        String responseContent = null;
        System.out.println("Enter executePostByHttpURLConnection, " + targetURL);
        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            URL serverURL = new URL(targetURL);
            HttpsURLConnection conn = (HttpsURLConnection) serverURL.openConnection();
            print_https_cert(conn);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-type", "application/json");
//            conn.setInstanceFollowRedirects(false);
            conn.connect();

            StringBuffer buffer = new StringBuffer();
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            while((s = bufferedReader.readLine()) != null){
                buffer.append(s);
                System.out.println("s:" + s);

            }
            String result = buffer.toString();
            System.out.println("result:" + result);
            responseContent = result;
        }  catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    public static String executePost(String targetURL, Map<String, String> params, int timeout) {
        String responseContent = null;
        HttpPost httpPost = new HttpPost(targetURL);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, CommonFunction.ENCODING));
            httpPost.setConfig(requestConfig);
            if (httpClient == null) {
                Log.e(TAG, "No httpClient available in WebserviceUtils");
                return null;
            } else {
                CloseableHttpResponse response = httpClient.execute(httpPost);
                try {
                    HttpEntity entity = response.getEntity();
                    try {
                        if (null != entity) {
                            responseContent = EntityUtils.toString(entity, CommonFunction.ENCODING);
                        }
                    } finally {
                        if (entity != null) {
                            entity.getContent().close();
                        }
                    }
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }
//                logger.info("requestURI : " + httpPost.getURI() + ", responseContent: " + responseContent);
            }
        } catch (ClientProtocolException e) {
//            logger.error("ClientProtocolException " + targetURL, e);
            e.printStackTrace();
        } catch (IOException e) {
//            logger.error("IOException" + targetURL, e);
            e.printStackTrace();
        } catch (Exception e) {
//            logger.error("Fail to post url request" + targetURL, e);
            e.printStackTrace();
        } finally {
//            httpPost.releaseConnection();
        }
        return responseContent;
    }

    public static String executePost(String targetURL, String urlParameters, int timeout) {
        Map<String, String> params = new HashMap<String, String>();
        if (StringUtils.isNotBlank(urlParameters)) {
            try {
                String[] array = urlParameters.split("&");
                for (int i = 0; i < array.length; i++) {
                    String param = array[i];
                    String[] nameVal = param.split("=");
                    String name = nameVal[0];
                    String val = nameVal.length >= 2 ? nameVal[1] : null;
                    params.put(name, val);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return executePost(targetURL, params, timeout);
    }

    public static String executePost(String url, String action, String reqParam, int timeout) {
        String JSONString = "";
        try {
            if(StringUtils.isNotBlank(url)){
                String url_arr[] = url.split(",");
                int num = CommonFunction.randomGenerator.nextInt(url_arr.length);
                for( int i = 0; i < url_arr.length; i ++){
                    int position  = num + i >= url_arr.length ? num + i - url_arr.length : num + i ;
                    String server = url_arr[position];
                    logger.debug("server : " + server + " ,try : " + i);
                    if(StringUtils.isNotBlank(server)){
                        url = server + action;
                    }
                    JSONString = HttpConnect.executePost(url , reqParam, timeout);
//                    logger.debug(JSONString);
                    if(StringUtils.isNotBlank(JSONString)) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONString;
    }


    public static void init(String maxTotal, String defaultMaxPerRoute, String timeout, String detail) {
        destory();
        int max = CommonFunction.toInteger(maxTotal, 100);
        int defaultMax = CommonFunction.toInteger(defaultMaxPerRoute, 200);
        HttpConnect.configConnectionTimeout = CommonFunction.toInteger(timeout, 2000);
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(max);
        cm.setDefaultMaxPerRoute(defaultMax);
        if (StringUtils.isNotBlank(detail)) {
            String[] detailArray = detail.split("\\|");
            for (int i = 0; i < detailArray.length; i++) {
                try {
                    String subDetail = detailArray[i];
                    String[] subDetailArray = subDetail.split(",");
                    String hostname = subDetailArray[0];
                    HttpHost host = new HttpHost(hostname);
                    if (hostname.contains(":")) {
                        String[] hostnameArray = hostname.split(":");
                        host = new HttpHost(hostnameArray[0], Integer.parseInt(hostnameArray[1]));
                    }
                    cm.setMaxPerRoute(new HttpRoute(host), Integer.parseInt(subDetailArray[1]));
                } catch (Exception e) {
                    logger.error(e, e);
                }
            }
        }
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }
//    public static void destory() {
//        if (httpClient != null) {
//            try {
//                httpClient.close();
//                httpClient = null;
//            } catch (IOException e) {
//                logger.error(e, e);
//            }
//        }
//        if (cm != null) {
//            cm.shutdown();
//            cm = null;
//        }
//    }

    public static void destory() {

    }
    public static PoolingHttpClientConnectionManager getCm() {
        return cm;
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    private static void print_https_cert(HttpsURLConnection conn) {

        if (conn != null) {
            try {
                logger.info("Response Code : " + conn.getResponseCode());
                logger.info("Cipher Suite : " + conn.getCipherSuite());
                logger.info(" ");

                java.security.cert.Certificate[] certs = conn.getServerCertificates();
                for (java.security.cert.Certificate cert : certs) {
                    logger.info("Cert Type : " + cert.getType());
                    logger.info("Cert Hash Code : " + cert.hashCode());
                    logger.info("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
                    logger.info("Cert Public Key Format : " + cert.getPublicKey().getFormat());
                    logger.info(" ");
                }

            } catch (SSLPeerUnverifiedException e) {
                logger.error("SSLPeerUnverifiedException", e);
            } catch (IOException e) {
                logger.error("IOException", e);
            } catch (Exception e) {
                logger.error("Exception", e);
            }
        }
    }
}
