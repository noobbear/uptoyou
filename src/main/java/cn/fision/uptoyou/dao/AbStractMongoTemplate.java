package cn.fision.uptoyou.dao;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import cn.fision.uptoyou.util.PageResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;


/**
 * MongoDB操作基类 desc AbStractMongoTemplate.java
 * @author 广东技术师范大学 吴飞雄
 * @project nitrogen-core-dao
 * @email fx@fision.cn
 * @date 2019年1月3日-下午4:32:15完成
 * @version 1.0
 * @param <T>
 */
public abstract class AbStractMongoTemplate<T> {

    @Autowired
    protected MongoTemplate dao;


    public MongoTemplate getMongoTemplate() {
        return dao;
    }

    // 获取全部符合query的文档
    public List<T> find(Query query, Class<T> entityClass) {
        if (query == null)
            query = new Query();
        return dao.find(query, entityClass);
    }

    /**
     * 分页返回文档 desc find
     *
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4  上午11:30:51
     * @param page 第几页
     * @param size
     * @param sort 如果为空，默认按照ID倒叙查询，即按照插入时间倒叙查询
     * @param entityClass
     * @return
     */
    public PageResult find(int page, int size, Sort sort, Class<T> entityClass) {
        Query query = new Query();
        return getPageResult(page, size, entityClass, sort, query);
    }

    /**
     * 凭某个字段查询列表 desc findByProperty
     *
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4/ 上午11:05:28
     * @param proppertyName
     * @param value
     * @param entityClass
     * @return
     */
    public List<T> findByProperty(String proppertyName, Object value, Class<T> entityClass) {
        Query query = new Query(Criteria.where(proppertyName).is(value));
        List<T> list = dao.find(query, entityClass);
        return list;
    }

    /**
     * 模糊查询，不分页 desc SearchByProperty
     *
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4/ 上午11:22:10
     * @param proppertyName
     * @param value
     * @param entityClass
     * @return
     */
    public List<T> SearchByProperty(String proppertyName, Object value, Class<T> entityClass) {
        Query query = new Query(Criteria.where(proppertyName).regex(".*?\\" +
                String.valueOf(value) + ".*"));
        List<T> list = dao.find(query, entityClass);
        return list;
    }

    /**
     * 分页模糊查询 desc SearchByProperty
     *
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4/ 上午11:26:17
     * @param page
     * @param size
     * @param proppertyName
     * @param value
     * @param sort
     * @param entityClass
     * @return
     */
    public PageResult SearchByProperty(int page, int size, String proppertyName, Object value,
                                       Sort sort, Class<T> entityClass) {
        Query query = new Query(Criteria.where(proppertyName).regex(".*?\\" +
                String.valueOf(value) + ".*"));
        return getPageResult(page, size, entityClass, sort, query);
    }
    /**
     * 多个字段分页搜索
     * desc SearchByPropertys
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4/ 上午11:53:22
     * @param page
     * @param size
     * @param map
     * @param entityClass
     * @param sort
     * @return
     */
    public PageResult  SearchByPropertys(int page, int size,Map<String, Object> map,
                                         Class<T> entityClass, Sort sort) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (map != null && !map.isEmpty()) {
            for (String key : map.keySet()) {
                criteria.and(key).regex(".*?\\" + String.valueOf(map.get(key)) + ".*");
            }
            query.addCriteria(criteria);
        }
        return getPageResult(page, size, entityClass, sort, query);
    }

    /**
     * 查询并返回分页结果
     * @param page
     * @param size
     * @param entityClass
     * @param sort
     * @param query
     * @return
     */
    private PageResult getPageResult(int page, int size, Class<T> entityClass, Sort sort, Query query) {
        if (sort == null) {
            sort = Sort.by(Direction.DESC,"_id");
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        PageResult result = new PageResult(page, size);
        query.with(pageable);
        result.setTotalcount(dao.count(query, entityClass));
        result.setList(dao.find(query, entityClass));
        long totalpage = result.getTotalcount() / size;
        if (result.getTotalcount() % size != 0l) {
            totalpage++;
        }
        result.setTotalpage(totalpage);
        return result;
    }

    /**
     * 按指定字段的值搜索并排序
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4/ 上午11:13:21
     * @param map
     * @param entityClass
     * @param sort
     * @return
     */
    public List<T> findByPropertys(Map<String, Object> map, Class<T> entityClass, Sort sort) {
        Query query = new Query();
        if(sort!=null)
            query.with(sort);
        Criteria criteria = new Criteria();
        if (map != null && !map.isEmpty()) {
            for (String key : map.keySet()) {
                criteria.and(key).is(map.get(key));
            }
            query.addCriteria(criteria);
        }
        List<T> list = dao.find(query, entityClass);
        return list;
    }

    /**
     * 获取所有文档
     * @author 吴飞雄wufeixiong
     * @date 2019/1/3/ 下午4:56:22
     * @param entityClass
     * @return
     */
    public List<T> findAll(Class<T> entityClass) {
        return dao.findAll(entityClass);
    }

    /**
     * 分页查询
     * @author 吴飞雄wufeixiong
     * @date 2019/1/3/ 下午5:24:06
     * @param page
     * @param size
     * @param query
     * @param sort
     * @param entityClass
     * @return
     */
    public PageResult findPage(int page, int size, Query query, Sort sort, Class<T> entityClass) {
/*        if (sort == null) {
            //sort = new Sort(Direction.DESC,"_id");
            sort = Sort.by(Direction.DESC,"_id");
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        PageResult result = new PageResult(page, size);
        query.with(pageable);
        query.with(sort);
        result.setTotalcount(dao.count(query, entityClass));
        result.setList(dao.find(query, entityClass));
        long totalpage = result.getTotalcount() / size;
        if (result.getTotalcount() % size != 0l) {
            totalpage++;
        }
        result.setTotalpage(totalpage);
        return result;*/
        return getPageResult(page, size, entityClass, sort, query);

    }

    public List<T> findAllAndRemove(Query query, Class<T> entityClass) {
        return dao.findAllAndRemove(query, entityClass);
    }

    /**
     * 插入1条文档
     *
     * @param t
     * @return
     */
    public T insert(T t) {
        return dao.insert(t);
    }

    /**
     * 插入多条文档
     *
     * @param objectsToSave
     * @return
     */
    public Collection<T> insertAll(Collection<T> objectsToSave) {
        return dao.insertAll(objectsToSave);
    }

    /**
     * 保存1条文档，当文档存在时，覆盖它
     *
     * @param t
     * @return
     */
    public T save(T t) {
        return dao.save(t);
    }

    /**
     * 更新符合条件的若干条文档
     *
     * @param query
     * @param update
     * @param entityClass
     * @return
     */
    public UpdateResult update(Query query, Update update, Class<T> entityClass) {
        return dao.updateMulti(query, update, entityClass);
    }

    /**
     * 更新符合条件的第1条文档
     *
     * @param query
     * @param update
     * @param entityClass
     * @return
     */
    public UpdateResult updateOne(Query query, Update update, Class<T> entityClass) {
        return dao.updateFirst(query, update, entityClass);
    }

    /**
     * 删除符合条件的若干条文档
     *
     * @param query
     * @param entityClass
     * @return
     */
    public DeleteResult delete(Query query, Class<T> entityClass) {
        return dao.remove(query, entityClass);
    }

    /**
     * 删除指定文档
     *
     * @param t
     * @return
     */
    public DeleteResult deleteOne(T t) {
        return dao.remove(t);
    }

    /**
     * desc clear
     *
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4/上午11:39:59
     * @param clazz
     * @return
     */
    public void clear(Class<T> clazz) {
        dao.remove(new Query(),clazz);
    }

    /**
     *
     * desc count
     *
     * @author 吴飞雄wufeixiong
     * @date 2019/1/4  上午11:43:43
     * @param query
     * @param clazz
     * @return
     */
    public long count(Query query, Class<T> clazz) {
        if (query != null) {
            return dao.count(query, clazz);
        } else {
            return dao.count(new Query(), clazz);
        }
    }

}
