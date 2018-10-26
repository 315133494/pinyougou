package com.pinyougou.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    /**
     * 根据条件查询品牌分页列表
     * @param page 分页条件
     * @param brand 查询条件
     * @return
     */
    @Override
    public Page<Brand> selectBrandListByConditionPage(Page<Brand> page, Brand brand) {
        EntityWrapper<Brand> wrapper=new EntityWrapper<>();
        //按组件模糊查询
        if (brand!=null){
            //品牌名称
            if (brand.getName()!=null){
                wrapper.like("name",brand.getName());
            }
            //品牌首字母
            if (brand.getFirstChar()!=null){
                wrapper.like("first_char",brand.getFirstChar());
            }
        }
        //按条件分页查询
        List<Brand> brands = brandMapper.selectPage(page, wrapper);
        //设置记录进返回的结果
        page.setRecords(brands);
        return page;
    }

    /**
     * 品牌下拉框数据
     * @return
     */
    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }

}
