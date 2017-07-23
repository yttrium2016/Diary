package studio.yttrium.service.impl;

import org.springframework.stereotype.Service;
import studio.yttrium.dao.UserDao;
import studio.yttrium.pojo.User;
import studio.yttrium.pojo.UserExample;
import studio.yttrium.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户Service接口的实现类
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/14
 * Time: 11:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 通过登录名字获取用户
     * @param name
     * @return
     */
    public User findUserByLoginName(String name) {
        return userDao.selectByLoginName(name);
    }

    /**
     * 获得用户列表(不包括id)
     * @param id
     * @return
     */
    public List<User> getUserList(Integer id) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (id != null) {
            criteria.andIdNotEqualTo(id);
        }
        return userDao.selectByExample(example);
    }

    /**
     * 获取授权的用户通过权限
     * @param id
     * @return
     */
    public List<User> findUserLeftPrivilege(Integer id) {
        return userDao.selectUserLeftPrivilege(id);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int addUser(User user) {
        return userDao.insertSelective(user);
    }

    /**
     * 修改用户
     * @param loginUser
     * @return
     */
    public int editUser(User loginUser) {
        return userDao.updateByPrimaryKeySelective(loginUser);
    }
}
