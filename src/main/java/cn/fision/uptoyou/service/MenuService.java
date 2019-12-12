package cn.fision.uptoyou.service;

import cn.fision.uptoyou.bean.Menu;
import cn.fision.uptoyou.util.JsonResult;
import cn.fision.uptoyou.util.PageResult;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-11 11:14
 **/
public interface MenuService {
    JsonResult saveOne(Menu menu);
    PageResult page(int page,int size);

    Menu findById(String id);

    JsonResult justShake(int size);

    void clearData();
}
