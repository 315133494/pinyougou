package com.pinyougou.manager.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import com.pinyougou.service.SpecificationOptionService;
import com.pinyougou.service.SpecificationService;
import com.pinyougou.vo.SpecificationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private SpecificationOptionService specificationOptionService;

    /**
     * 添加
     * @param specificationVo
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationVo specificationVo) {
        try {
            specificationService.add(specificationVo);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    /**
     * 修改更新
     * @param specificationVo
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationVo specificationVo) {
        try {

            specificationService.update(specificationVo);
            return new Result(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败！");
        }
    }

    /**
     * 按条件查询分页规格列表
     * @param current 当前页
     * @param size 每页显示的记录㶼
     * @param specification 封装查询条件
     * @return
     */
    @RequestMapping(value = "/queryAllSpecificationListByConditionPage", method = RequestMethod.POST)
    public PageResult queryAllSpecificationListByConditionPage(@RequestParam(defaultValue = "1") int current,
                                                               @RequestParam(defaultValue = "10") int size,
                                                             @RequestBody  Specification specification) {

        //设置分页信息
        Page<Specification> page = new Page<>(current, size);
        //查询后的结果
        Page<Specification> page1 = specificationService.selectAllSpecificationListByConditionPage(page, specification);
        //显示在页面信息类
        PageResult pageResult = new PageResult();
        //设置记录
        pageResult.setRows(page1.getRecords());
        //设置总记录数
        pageResult.setTotal(page1.getTotal());
        return pageResult;
    }

    /**
     * 根据id查询规格
     * @param id
     * @return
     */
    @RequestMapping("/selectByOne")
    public SpecificationVo selectByOne(Long id){
        //查询规格
        Specification specification = specificationService.selectById(id);
        //查询规格选项
        EntityWrapper<SpecificationOption> wrapper=new EntityWrapper<>();
        wrapper.eq("spec_id",specification.getId());
        List<SpecificationOption> specificationOptions = specificationOptionService.selectList(wrapper);
        //设置返回类型
        SpecificationVo specificationVo=new SpecificationVo();
        specificationVo.setSpecification(specification);
        specificationVo.setSpecificationOptionList(specificationOptions);
        return  specificationVo;
    }

    /**
     * 根据id删除品牌
     * @param ids 规格id数组
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        //定义返回结果
        List<Long> idlist=new ArrayList<>();
        for (Long id:ids) {
            idlist.add(id);
        }
        try {
            //批量删除
            specificationService.deleteBatchIds(idlist);

            //还要删除规格选项
            //循环删除
            for (int i=0;i<idlist.size();i++){
                EntityWrapper<SpecificationOption> wrappe=new EntityWrapper<>();
                wrappe.eq("spec_id",idlist.get(i));
                specificationOptionService.delete(wrappe);
            }

            return  new Result(true,"删除成功！");
        }catch (Exception e){
            return  new Result(false,"删除失败！");
        }
    }

    /**
     * 规格下列列表数据
     * @return
     */
    @RequestMapping("/selectSpecificationOptionList")
    public List<Map> selectSpecificationOptionList() {
        return specificationService.selectOptionList();
    }
}

