package com.app.common.controller;

import com.app.common.entity.AbstractEntity;
import com.app.common.entity.PageModel;
import com.app.common.entity.Response;
import com.app.common.service.BaseService;
import com.app.common.util.AppServiceHelper;
import com.app.common.util.LoginUtil;
import com.app.common.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController<T> {

    /**
     * 输出日志类
     */
    protected static Logger log;

    protected T entity;

    protected Class<T> entityClass;

    protected String entityClassName;

    /**
     * 返回模型对象
     */
    public T getModel() {
        return entity;
    }

    public BaseController() {
        super();
        // 通过反射取得Entity的Class.
        try {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            entityClassName = entityClass.getSimpleName();
            log = LoggerFactory.getLogger(this.getClass());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得Service接口对象
     * 默认以实体对象类名的前缀小写形式+Service获取对应的Service对象实例
     */
    protected BaseService<T> getBaseService() {
        BaseService<T> baseService = (BaseService<T>) findBean(StringUtils.uncapitalize(entityClassName)
                + "ServiceImpl");
        return baseService;
    }

    protected Object findBean(String beanId) {
        return AppServiceHelper.findBean(beanId);
    }

    /**
     * 查询所有（不分页）
     * @return
     */
    @RequestMapping("/findAll")
    public Response findAll() {
        List<T> entityLs = getBaseService().findAll();
        return new Response().success(entityLs); // 返回一个String类型的Json串
    }

    /**
     * 添加（json参数）
     * @return
     */
    @RequestMapping("/addJson")
    public Response add(HttpServletRequest request) {
        String jsonStr = getJsonFromRequest(request);
        List<T> entityLs = Util.jsonArrToList(jsonStr, entityClass);
        for (T entity : entityLs) {
            AbstractEntity ae = (AbstractEntity)entity;
            ae.setCreatedBy(LoginUtil.getUserName());
            ae.setCreatedDt(new Date());
        }
        getBaseService().insertAll(entityLs);
        return new Response().success();
    }

    // 更新数据
    @RequestMapping("/updateJson")
    public Response update(HttpServletRequest request) {
        String jsonStr = getJsonFromRequest(request);
        List<T> entityLs = Util.jsonArrToList(jsonStr, entityClass);
        for (T entity : entityLs) {
            AbstractEntity ae = (AbstractEntity)entity;
            ae.setUpdatedBy(LoginUtil.getUserName());
            ae.setUpdatedDt(new Date());
        }
        getBaseService().updateAll(entityLs);
        return new Response().success();
    }

    /**
     * 删除数据(json参数)
     * @return
     */
    @RequestMapping("/deleteJson")
    public Response delete(HttpServletRequest request) {
        String jsonStr = getJsonFromRequest(request);
        List<T> entityLs = Util.jsonArrToList(jsonStr, entityClass);
        getBaseService().deleteAll(entityLs);
        return new Response().success();
    }

    /**
     * 保存数据
     * @param entity
     * @return
     */
    @RequestMapping("/add")
    public Response add(@RequestBody T entity) {
        AbstractEntity ae = (AbstractEntity)entity;
        ae.setCreatedBy(LoginUtil.getUserName());
        ae.setCreatedDt(new Date());
        getBaseService().insert(entity);
        return new Response().success();
    }

    /**
     * 批量保存数据
     * @param entityLs
     * @return
     */
    @RequestMapping("/addAll")
    public Response addAll(@RequestBody List<T> entityLs) {
        for (T entity : entityLs) {
            AbstractEntity ae = (AbstractEntity)entity;
            ae.setCreatedBy(LoginUtil.getUserName());
            ae.setCreatedDt(new Date());
        }
        getBaseService().insertAll(entityLs);
        return new Response().success();
    }

    /**
     * 更新数据
     * @param entity
     * @return
     */
    @RequestMapping("/update")
    public Response update(@RequestBody T entity) {
        AbstractEntity ae = (AbstractEntity)entity;
        ae.setUpdatedBy(LoginUtil.getUserName());
        ae.setUpdatedDt(new Date());
        getBaseService().update(entity);
        return new Response().success();
    }

    /**
     * 批量更新数据
     * @param entityLs
     * @return
     */
    @RequestMapping("/updateAll")
    public Response updateAll(@RequestBody List<T> entityLs) {
        for (T entity : entityLs) {
            AbstractEntity ae = (AbstractEntity)entity;
            ae.setUpdatedBy(LoginUtil.getUserName());
            ae.setUpdatedDt(new Date());
        }
        getBaseService().updateAll(entityLs);
        return new Response().success();
    }

    /**
     * 删除数据(json参数)
     * @return
     */
    @RequestMapping("/delete")
    public Response delete(@RequestBody T entity) {
        getBaseService().delete(entity);
        return new Response().success();
    }

    /**
     * 删除数据(json参数)
     * @return
     */
    @RequestMapping("/deleteAll")
    public Response deleteAll(@RequestBody List<T> entityLs) {
        getBaseService().deleteAll(entityLs);
        return new Response().success();
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping("/findByPage")
    public Response findByPage(HttpServletRequest request) {
        String json = getJsonFromRequest(request);
        Map<String, String> params = Util.jsonToMap(json);
        PageModel<T> page = getBaseService().findByPage(params);
        return new Response().success(page);
    }

    /**
     * 读取request中的json信息
     * @return jsonStr
     */
    public String getJsonFromRequest(HttpServletRequest request) {
        String jsonStr = null;
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> param = new HashMap<>();
            for (String key : parameterMap.keySet()) {
                param.put(key, parameterMap.get(key)[0]);
            }
            jsonStr = Util.mapToJson(param);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return jsonStr;
    }

}
