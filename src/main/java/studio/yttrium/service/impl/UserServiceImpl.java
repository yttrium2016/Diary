package studio.yttrium.service.impl;

import org.springframework.stereotype.Service;
import studio.yttrium.dao.UserDao;
import studio.yttrium.pojo.User;
import studio.yttrium.pojo.UserExample;
import studio.yttrium.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/14
 * Time: 11:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public User findUserByLoginName(String name) {
        return userDao.selectByLoginName(name);
    }

    public List<User> getUserList(Integer id) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (id != null) {
            criteria.andIdNotEqualTo(id);
        }
        return userDao.selectByExample(example);
    }

    public List<User> findUserLeftPrivilege(Integer id) {
        return userDao.selectUserLeftPrivilege(id);
    }
}
