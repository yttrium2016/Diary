package studio.yttrium.service;

import studio.yttrium.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/14
 * Time: 11:18
 */

public interface UserService {

    /**
     * 通过用户名查找用户
     * @param name
     * @return
     */
    User findUserByLoginName(String name);

    /**
     * 查找所有的用户 (不包括id)
     * @param id
     * @return
     */
    List<User> getUserList(Integer id);

    /**
     * 查询已经授权的用户
     * @param id
     * @return
     */
    List<User> findUserLeftPrivilege(Integer id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 修改用户
     * @param loginUser
     * @return
     */
    int editUser(User loginUser);
}
