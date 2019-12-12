package cn.fision.uptoyou.controller;

import cn.fision.uptoyou.bean.Menu;
import cn.fision.uptoyou.service.MenuService;
import cn.fision.uptoyou.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-12 13:47
 **/
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/menu/save")
    public JsonResult saveOne(@NotNull Menu menu){
        JsonResult result;
        if (menu.getId()==null){
            result = menuService.saveOne(menu);
        }else{
            //如果menu中有预置字段或不能更新的字段，先从原来的menu中获取填充，再保存
            //Menu old = menuService.findByUserId(user.getId());
            //user.setXXX(old.getXXX());
            result =  menuService.saveOne(menu);
        }
        return  result;
    }

    @GetMapping("/menu/shake/{size}")
    public Object shake(@PathVariable(name = "size",required = false) int size){
        JsonResult jsonResult = menuService.justShake(size);
        return  jsonResult.getData();
    }
}
