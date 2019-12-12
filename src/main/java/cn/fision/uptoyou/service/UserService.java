package cn.fision.uptoyou.service;

import cn.fision.uptoyou.bean.User;
import cn.fision.uptoyou.util.JsonResult;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-11 11:12
 **/
public interface UserService {
    JsonResult getUserByUserNameAndPwdPlainText(String userName,String pwdPlainText);
    JsonResult saveUser(User user);

    User findByUserId(String id);
}
