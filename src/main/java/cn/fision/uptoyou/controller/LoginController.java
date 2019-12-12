package cn.fision.uptoyou.controller;

import cn.fision.uptoyou.service.UserService;
import cn.fision.uptoyou.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-11 11:42
 **/
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JsonResult loginValidate(@NotBlank String userName, @NotBlank String password){
        return userService.getUserByUserNameAndPwdPlainText(userName, password);
    }
}
