package studio.yttrium.service.impl;

import org.springframework.stereotype.Service;
import studio.yttrium.dao.PrivilegeDao;
import studio.yttrium.pojo.Privilege;
import studio.yttrium.pojo.PrivilegeExample;
import studio.yttrium.service.PrivilegeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限Service接口的实现类
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/19
 * Time: 17:39
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Resource
    private PrivilegeDao privilegeDao;

    /**
     * 获得权限ids
     * @param id
     * @return
     */
    public List<Integer> findPrivilegeList(Integer id) {
        return privilegeDao.selectReferIdByUserId(id);
    }

    /**
     * 删除权限
     * @param referId
     * @param userId
     * @return
     */
    public int deletePrivilege(Integer referId, int userId) {
        PrivilegeExample example = new PrivilegeExample();
        PrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andReferIdEqualTo(referId);
        return privilegeDao.deleteByExample(example);
    }

    /**
     * 验证权限是否存在
     * @param referId
     * @param userId
     * @return
     */
    public int findPrivilegeCount(Integer referId, int userId) {
        PrivilegeExample example = new PrivilegeExample();
        PrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andReferIdEqualTo(referId);
        return privilegeDao.selectByExample(example).size();
    }

    /**
     * 添加权限
     * @param referId
     * @param userId
     * @return
     */
    public int addPrivilege(Integer referId, int userId) {
        Privilege privilege = new Privilege();
        privilege.setReferId(referId);
        privilege.setUserId(userId);
        return privilegeDao.insertSelective(privilege);
    }
}
