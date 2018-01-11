package studio.yttrium.utils;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2018/1/11
 * Time: 16:02
 */
public class WeiXinSendUtils {

    private static String SEND_KEY = "2087-0140c5feccff8a6d2a9b0f1b8eecxxxx";

    private static String URL = "https://pushbear.ftqq.com/sub?sendkey=%s&text=%s&desp=%s";

    public static void sendMessage(String title, String des) {

        try {
            String sendKey = java.net.URLEncoder.encode(SEND_KEY, "utf-8");
            String text = java.net.URLEncoder.encode(title, "utf-8");
            String desp = java.net.URLEncoder.encode(des, "utf-8");

            //拼地址
            String apiUrl = String.format(URL, sendKey, text, desp);

            //开始请求
            HttpGetUtils get = new HttpGetUtils(apiUrl);
            String result = get.executeMethod(String.class);
        } catch (Exception e) {
            System.out.println("发送微信推送失败");
        }
    }

    public static void sendMessage(String des) {
        sendMessage("日记服务器推送信息", des);
    }
}
