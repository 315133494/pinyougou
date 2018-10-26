package com.pinyougou.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.Brand;

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
public interface BrandService extends IService<Brand> {
    /**
     * 根据条件查询品牌分页列表
     * @param page 分页条件
     * @param brand 查询条件
     * @return
     */
    public Page<Brand> selectBrandListByConditionPage(Page<Brand>page , Brand brand);

    /**
     * 添加品牌
     * @param brand
     */
    //public void add(Brand brand);

    /**
     * 品牌下拉框数据
     * @return
     */
    public List<Map> selectOptionList();
}
