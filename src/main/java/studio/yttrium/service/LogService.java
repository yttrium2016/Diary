package studio.yttrium.service;

import studio.yttrium.pojo.Log;

/**
 * 日志接口
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/10/13
 * Time: 16:26
 */
public interface LogService {
    /**
     * 添加日志
     * @param log
     */
    void addLog(Log log);
}
