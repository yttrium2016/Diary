package studio.yttrium.service;

import java.util.List;

/**
 * 权限Service接口
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/19
 * Time: 17:39
 */
public interface PrivilegeService {

    /**
     * 获取所有已经授权的id
     * @param id
     * @return
     */
    List<Integer> findPrivilegeList(Integer id);

    /**
     * 删除权限
     * @param referId
     * @param userId
     * @return
     */
    int deletePrivilege(Integer referId, int userId);

    /**
     * 查找权限是否存在
     * @param referId
     * @param userId
     * @return
     */
    int findPrivilegeCount(Integer referId, int userId);

    /**
     * 添加授权
     * @param referId
     * @param userId
     * @return
     */
    int addPrivilege(Integer referId, int userId);
}
