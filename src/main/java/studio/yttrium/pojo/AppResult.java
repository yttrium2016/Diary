package studio.yttrium.pojo;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2018/1/2
 * Time: 13:31
 */
public class AppResult extends HashMap<String, Object> {

    /**
     * 成功
     */
    private static int SUCCESS = 0;
    /**
     * 失败
     */
    private static int ERROR = -1;


    public AppResult() {
        put("code", SUCCESS);
    }

    public static AppResult error() {
        return error(ERROR, "发生错误,请打110报警");
    }

    public static AppResult error(String msg) {
        return error(ERROR, msg);
    }

    public static AppResult error(int code, String msg) {
        AppResult r = new AppResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static AppResult success(String msg) {
        AppResult r = new AppResult();
        r.put("msg", msg);
        return r;
    }

    public static AppResult success() {
        return new AppResult();
    }

    public AppResult data(Object value) {
        super.put("data", value);
        return this;
    }

}
