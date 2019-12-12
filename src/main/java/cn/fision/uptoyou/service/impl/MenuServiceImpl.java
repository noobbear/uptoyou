package cn.fision.uptoyou.service.impl;

import cn.fision.uptoyou.bean.Menu;
import cn.fision.uptoyou.dao.MenuDao;
import cn.fision.uptoyou.service.MenuService;
import cn.fision.uptoyou.util.JsonResult;
import cn.fision.uptoyou.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: uptoyou
 * @description:
 * @author: Fision
 * @Email: fx@fision.cn
 * @create: 2019-12-11 11:15
 **/
@Service
@Lazy
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public JsonResult saveOne(@NotNull Menu menu) {
        Menu save = menuDao.save(menu);
        if (save!=null&& !save.getId().isEmpty()){
            return JsonResult.ok(save);
        }
        return JsonResult.build(-1,"保存失败",save);
    }

    @Override
    public PageResult page(int page, int size) {
        return  menuDao.findPage(page,size,new Query(),null,Menu.class);
    }
    @Override
    public Menu findById(String id){
        return menuDao.getMongoTemplate().findById(id,Menu.class);
    }

    @Override
    public JsonResult justShake(int size){
        //抽样查询
        Aggregation agg = Aggregation.newAggregation(Aggregation.sample(size));
        AggregationResults<Menu> results = menuDao.getMongoTemplate().aggregate(agg, "menu", Menu.class);
        List<Menu> list = results.getMappedResults();
        if (list.size() > 0) {
            return JsonResult.ok(list);
        } else {
            return JsonResult.build(-1, "随机获取菜单，列表为空");
        }
    }

    @Override
    public void clearData() {
        menuDao.clear(Menu.class);
    }
}
