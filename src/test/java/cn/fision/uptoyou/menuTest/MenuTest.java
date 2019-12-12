package cn.fision.uptoyou.menuTest;

import cn.fision.uptoyou.bean.Menu;
import cn.fision.uptoyou.service.MenuService;
import cn.fision.uptoyou.util.JsonResult;
import cn.fision.uptoyou.util.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-12 14:25
 **/
@SpringBootTest
public class MenuTest {
    @Autowired
    MenuService menuService;

    @Test
    public  void clear(){
        menuService.clearData();
    }


    @Test
    public void save(){
        Menu menu = new Menu();
        menu.setName("番茄炒蛋");
        menu.setContent("🍅一个，蛋2个，做法不详");
        menuService.saveOne(menu);
    }    @Test
    public void save1(){
        Menu menu = new Menu();
        menu.setName("水煮蛋");
        menu.setContent("水煮鸡蛋");
        menuService.saveOne(menu);
    }    @Test
    public void save2(){
        Menu menu = new Menu();
        menu.setName("回锅肉");
        menu.setContent("猪肉5两，青椒2个。。。。");
        menuService.saveOne(menu);
    }
    @Test
    public void println(){
        JsonResult jsonResult = menuService.justShake(1);
        System.out.println(jsonResult.getMsg()+jsonResult.getData());
        PageResult page = menuService.page(1, 1);
        System.out.println(page);
    }
}
