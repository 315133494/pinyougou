package com.pinyougou.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
@Service
@Transactional
public class TypeTemplateServiceImpl extends ServiceImpl<TypeTemplateMapper, TypeTemplate> implements TypeTemplateService {
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 按条件查询分页模板列表
     * @param page 分页
     * @param typeTemplate 查询条件
     * @return
     */
    @Override
    public Page<TypeTemplate> queryAllTypeTemplateListByConditionPage(Page<TypeTemplate> page, TypeTemplate typeTemplate) {
        EntityWrapper<TypeTemplate> wrapper=new EntityWrapper<>();
        //
        if (typeTemplate!=null){
            //模糊查询
            if (typeTemplate.getName()!=null){
                wrapper.eq("name",typeTemplate.getName());
            }
        }
        List<TypeTemplate> typeTemplates = typeTemplateMapper.selectPage(page, wrapper);
        //设置内容进page
        page.setRecords(typeTemplates);

        //存入数据到缓存
        saveToRedis();
        return page;
    }

    /**
     * 将数据存入缓存
     */
    public void saveToRedis(){
        //获取模板数据
        List<TypeTemplate> typeTemplates = selectList(null);
        //循环模板
        for (TypeTemplate typeTemplate : typeTemplates) {
            //存储品牌列表
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(),Map.class);
            redisTemplate.boundHashOps("barndList").put(typeTemplate.getId(),brandList);

            //存储规格列表
            List<Map> specList = selectSpecList(typeTemplate.getId());
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(),specList);
        }

    }

    /**
     * 返回规格列表
     * @param id
     * @return
     */
    @Override
    public List<Map> selectSpecList(Long id) {
        //查询模板
        TypeTemplate typeTemplate = typeTemplateMapper.selectById(id);

        //把json字符串转成数组
        List<Map> list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);

        for (Map map : list) {
            //查询规格选项列表
            EntityWrapper<SpecificationOption> wrapper = new EntityWrapper<SpecificationOption>();
            wrapper.eq("spec_id", map.get("id"));
            List<SpecificationOption> options = specificationOptionMapper.selectList(wrapper);
            map.put("options", options);
        }

        return list;
    }
}
