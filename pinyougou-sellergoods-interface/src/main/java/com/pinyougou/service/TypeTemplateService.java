package com.pinyougou.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.TypeTemplate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
public interface TypeTemplateService extends IService<TypeTemplate> {
    /**
     * 按条件查询分页模板列表
     * @param page 分页
     * @param typeTemplate 查询条件
     * @return
     */
    Page<TypeTemplate> queryAllTypeTemplateListByConditionPage(Page<TypeTemplate> page, TypeTemplate typeTemplate);

    /**
     * 返回规格列表
     * @return
     */
    public List<Map> selectSpecList(Long id);

}
