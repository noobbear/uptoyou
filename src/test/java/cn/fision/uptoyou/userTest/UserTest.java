package cn.fision.uptoyou.userTest;
import cn.fision.uptoyou.bean.User;
import cn.fision.uptoyou.service.UserService;
import cn.fision.uptoyou.util.JsonResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-11 11:53
 **/
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void  save(){
        User user = new User();
        user.setUserName("test2");
        user.setUserPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        JsonResult jsonResult = userService.saveUser(user);
        System.out.println(jsonResult);
    }
}
