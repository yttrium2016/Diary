package studio.yttrium.service;

import java.util.List;

/**
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
}
