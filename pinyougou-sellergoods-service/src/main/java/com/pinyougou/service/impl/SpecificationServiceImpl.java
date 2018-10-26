package com.pinyougou.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationOptionService;
import com.pinyougou.service.SpecificationService;
import com.pinyougou.vo.SpecificationVo;
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
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    /**
     * 添加
     * @param specificationVo 规格组合
     */
    @Override
    public void add(SpecificationVo specificationVo) {
        //插入规格
        specificationMapper.insert(specificationVo.getSpecification());


        //循环插入规格选项
        for (SpecificationOption specificationOption:specificationVo.getSpecificationOptionList()) {
            //设置规格ID
            specificationOption.setSpecId(specificationVo.getSpecification().getId());
            //插入规格选项
            specificationOptionMapper.insert(specificationOption);
        }
    }

    /**
     * 修改更新规格组合
     * @param specificationVo 规格组合
     */
    @Override
    public void update(SpecificationVo specificationVo) {
        //更新规格
        specificationMapper.updateById(specificationVo.getSpecification());

        //先执行删除规格选项
        //根据规格的id来删除规格选项
        EntityWrapper<SpecificationOption> wrapper=new EntityWrapper<>();
        wrapper.eq("spec_id",specificationVo.getSpecification().getId());
        specificationOptionMapper.delete(wrapper);

        //再执行修改规格选项，就是重新插入规格选项
        //循环插入规格选项
        for (SpecificationOption specificationOption:specificationVo.getSpecificationOptionList()) {
            //设置规格ID
            specificationOption.setSpecId(specificationVo.getSpecification().getId());
            //插入规格选项
            specificationOptionMapper.insert(specificationOption);
        }
    }

    /**
     * 规格下拉列表数据
     * @return
     */
    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }

    /**
     * 按条件查询分页规格列表
     * @param page 分页
     * @param specification 查询条件
     */
    @Override
    public Page<Specification> selectAllSpecificationListByConditionPage(Page<Specification> page, Specification specification) {
        EntityWrapper<Specification> wrapper=new EntityWrapper<>();

        if (specification!=null){
            if (specification.getSpecName()!=null)
            //按组件模糊查询
            wrapper.eq("spec_name",specification.getSpecName());
        }
        //开始查询
        List<Specification> specifications = specificationMapper.selectPage(page, wrapper);

        //设置记录进分页中
        page.setRecords(specifications);

        return page;
    }



}
