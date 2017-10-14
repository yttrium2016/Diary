package studio.yttrium.service.impl;

import org.springframework.stereotype.Service;
import studio.yttrium.dao.LogDao;
import studio.yttrium.pojo.Log;
import studio.yttrium.service.LogService;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/10/13
 * Time: 16:26
 */
@Service
public class LogServiceImpl implements LogService{

    @Resource
    private LogDao logDao;

    @Override
    public void addLog(Log log) {
        logDao.insertSelective(log);
    }
}
