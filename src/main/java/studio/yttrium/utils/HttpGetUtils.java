package studio.yttrium.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/17
 * Time: 17:47
 */
public class HttpGetUtils {

    private String url;
    //默认编码为utf-8
    private String charset = "UTF-8";
    //超时
    private int timeout = 3000;
    //请求头信息
    private Map<String,String> headers = new LinkedHashMap<String, String>();


    public HttpGetUtils(String url) {
        this.url = url;
    }

    /**
     * 执行方法
     * @param <T>
     * @param requiredType	返回类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T>T executeMethod(Class<T> requiredType) {
        if(null == requiredType){
            LoggerUtils.fmtError(getClass(), "必须给定返回值类型。");
            return null;
        }
        URL xurl = null;
        InputStream input = null;
        InputStream stream = null;
        HttpURLConnection conn = null;
        BufferedReader in = null;
        try {
            xurl = new URL(url);
            if("https".equalsIgnoreCase(xurl.getProtocol())){
                SslUtils.ignoreSsl();
            }
            // 打开和URL之间的连接
            conn = (HttpURLConnection) xurl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setRequestMethod("GET");// 提交模式
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "close");
            conn.setConnectTimeout(timeout);  //设置连接主机超时（单位：毫秒）
            conn.setReadTimeout(timeout);     //设置从主机读取数据超时（单位：毫秒）

            //请求头信息输入，包括cookie什么的
            for(Map.Entry<String, String> head : headers.entrySet()) {
                conn.addRequestProperty(head.getKey(), head.getValue());
            }
            input = conn.getInputStream();
            //获取相应头
            Map<String, List<String>> headers = conn.getHeaderFields();
            //判断请求头信息是否压缩
            for (Map.Entry<String, List<String>> head : headers.entrySet()) {
                if(null != head.getValue() && head.getValue().size() > 0){
                    if("Content-Encoding".equalsIgnoreCase(head.getKey())){
                        //GZIP 解压缩
                        if(null != head.getValue() && head.getValue().size() > 0 && "gzip".equalsIgnoreCase(StringUtils.trimToEmpty(head.getValue().get(0)))){
                            input = new GZIPInputStream(input);
                        }
                    }
                }
            }
            String result = "";
            //先采用 IOUtils 转换，如果错误，再用普通的方式转换
            try{
                result = IOUtils.toString(input,charset);
            }catch (Exception e) {
                StringBuffer sb = new StringBuffer("");
                in = new BufferedReader(new InputStreamReader(input, charset));
                String line = "";
                while ((line = in.readLine()) != null){
                    sb.append(line);
                }
                result = sb.toString();
            }
            //如果返回String，直接返回即可
            if("java.lang.String".equals(requiredType.getCanonicalName())){
                return (T)result;
            }
            //其他对象，直接Gson转换
//            return new Gson().fromJson(result,requiredType);
            return JacksonUtils.json2pojo(result,requiredType);
        } catch (Exception e) {
            LoggerUtils.fmtError(HttpGetUtils.class, e, "请求失败，url[%s]", url);
            return  null;
        } finally {
            if(null !=conn)conn.disconnect();
            xurl = null;
            try {
                if(null != in)in.close();
                in = null;
            } catch (Exception e2) {
                LoggerUtils.fmtError(HttpGetUtils.class, e2, "请求完毕关闭流出现异常，可以忽略！[%s]", xurl);
            }
            try {
                if(null != input)input.close();
                input = null;
            } catch (Exception e2) {
                LoggerUtils.fmtError(HttpGetUtils.class, e2, "请求完毕关闭流出现异常，可以忽略！[%s]", xurl);
            }
            try {
                if(null != stream)stream.close();
                stream = null;
            } catch (Exception e2) {
                LoggerUtils.fmtError(HttpGetUtils.class, e2, "请求完毕关闭流出现异常，可以忽略！[%s]", xurl);
            }
        }

    }
    public String getCharset() {
        return charset;
    }
    public HttpGetUtils setCharset(String charset) {
        this.charset = charset;
        return this;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }
    /**
     * 批量设置请求头信息
     * @param headers
     * @return
     */
    public HttpGetUtils setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
    //单个设置请求头信息
    public HttpGetUtils setHeader(String key,String value){
        this.headers.put(key, value);
        return this;
    }

}
