package com.pinyougou.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.Specification;
import com.pinyougou.vo.SpecificationVo;

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
public interface SpecificationService extends IService<Specification> {
    /**
     * 添加
     * @param specificationVo 规格组合
     */
    public void add(SpecificationVo specificationVo);

    /**
     * 按条件查询分页规格列表
     * @param page 分页
     * @param specification 查询条件
     */
    Page<Specification> selectAllSpecificationListByConditionPage(Page<Specification> page, Specification specification);


    /**
     * 修改
     * @param specificationVo 规格组合
     */
    void update(SpecificationVo specificationVo);

    /**
     * 规格下拉列表数据
     * @return
     */
    List<Map> selectOptionList();
}
