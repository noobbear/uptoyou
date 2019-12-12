package cn.fision.uptoyou.controller;

import cn.fision.uptoyou.bean.User;
import cn.fision.uptoyou.service.UserService;
import cn.fision.uptoyou.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-12 13:46
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/save")
    public JsonResult saveOne(@NotNull User user){
        JsonResult jsonResult;
        if (user.getId()==null){
            jsonResult = userService.saveUser(user);
        }else{
            //如果user中有预置字段或不能更新的字段，先从原来的user中获取填充，再保存
            //User old = userService.findByUserId(user.getId());
            //user.setXXX(old.getXXX());
            jsonResult =  userService.saveUser(user);
        }
        return  jsonResult;
    }
}
