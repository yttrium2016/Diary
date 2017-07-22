package studio.yttrium.service.impl;

import org.springframework.stereotype.Service;
import studio.yttrium.dao.PrivilegeDao;
import studio.yttrium.pojo.Privilege;
import studio.yttrium.pojo.PrivilegeExample;
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

    public int deletePrivilege(Integer referId, int userId) {
        PrivilegeExample example = new PrivilegeExample();
        PrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andReferIdEqualTo(referId);
        return privilegeDao.deleteByExample(example);
    }

    public int findPrivilegeCount(Integer referId, int userId) {
        PrivilegeExample example = new PrivilegeExample();
        PrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andReferIdEqualTo(referId);
        return privilegeDao.selectByExample(example).size();
    }

    public int addPrivilege(Integer referId, int userId) {
        Privilege privilege = new Privilege();
        privilege.setReferId(referId);
        privilege.setUserId(userId);
        return privilegeDao.insertSelective(privilege);
    }
}
