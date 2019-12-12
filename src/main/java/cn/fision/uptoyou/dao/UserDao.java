package cn.fision.uptoyou.dao;

import cn.fision.uptoyou.bean.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserDao <br/>
 * Description: <br/>
 *
 * @author Fision<br />
 * @version 1.0
 * @Date: 2019/12/11- 11:06<br/>
 */
@Service
@Lazy
public class UserDao extends  AbStractMongoTemplate<User>{

}
