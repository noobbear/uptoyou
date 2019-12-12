package cn.fision.uptoyou.service.impl;

import cn.fision.uptoyou.bean.User;
import cn.fision.uptoyou.dao.UserDao;
import cn.fision.uptoyou.service.UserService;
import cn.fision.uptoyou.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-11 11:14
 **/
@Service
@Lazy
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public JsonResult getUserByUserNameAndPwdPlainText(String userName, String pwdPlainText) {
        String pwd = DigestUtils.md5DigestAsHex(pwdPlainText.getBytes());
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(userName));
        query.addCriteria(Criteria.where("userPassword").is(pwd));
        List<User> users = userDao.find(query, User.class);
        if(users!=null&&users.size()>0){
            return JsonResult.ok(users.get(0));
        }
        return JsonResult.build(-1,"用户名或密码错误",null);
    }

    @Override
    public JsonResult saveUser(@NotNull User user) {
        User save = userDao.save(user);
        return JsonResult.ok(user);
    }

    @Override
    public User findByUserId(String id){
        return userDao.getMongoTemplate().findById(id,User.class);
    }
}
