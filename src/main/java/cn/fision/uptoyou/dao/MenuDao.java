package cn.fision.uptoyou.dao;

import cn.fision.uptoyou.bean.Menu;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-11 11:10
 **/
@Service
@Lazy
public class MenuDao extends AbStractMongoTemplate<Menu>{
}
