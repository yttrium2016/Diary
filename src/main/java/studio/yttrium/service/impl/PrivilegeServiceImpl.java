package studio.yttrium.service.impl;

import org.springframework.stereotype.Service;
import studio.yttrium.dao.PrivilegeDao;
import studio.yttrium.service.PrivilegeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/19
 * Time: 17:39
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Resource
    private PrivilegeDao privilegeDao;

    public List<Integer> findPrivilegeList(Integer id) {
        return privilegeDao.selectReferIdByUserId(id);
    }
}
