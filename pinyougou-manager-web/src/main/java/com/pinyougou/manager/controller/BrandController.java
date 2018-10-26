package com.pinyougou.manager.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.pinyougou.pojo.Brand;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据条件查询品牌分页列表
     *
     * @param current 当前页
     * @param size    每页显示的总记录数
     * @param brand   按组件查询的对象
     * @return
     */
    @RequestMapping(value = "/selectBrandListByConditionPage", method = RequestMethod.POST)
    public PageResult selectBrandListByConditionPage(@RequestParam(defaultValue = "1") int current,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestBody Brand brand) {
        //设置分页信息
        Page<Brand> page = new Page<Brand>(current, size);
        //查询后的结果
        Page<Brand> page1 = brandService.selectBrandListByConditionPage(page, brand);
        //显示在页面信息类
        PageResult pageResult = new PageResult();
        //设置记录
        pageResult.setRows(page1.getRecords());
        //设置总记录数
        pageResult.setTotal(page1.getTotal());
        return pageResult;
    }


    /**
     * 添加品牌
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Brand brand) {
        //定义返回结果
        try {
            //执行添加
            brandService.insert(brand);
            //添加成功
           return new Result(true ,"添加成功！");
        } catch (Exception e) {
            //添加失败
            e.printStackTrace();
            return new Result(false,"添加失败！");
        }
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectByOne")
    public Brand selectByOne(Long id) {
        //根据id查询品牌
        Brand brand = brandService.selectById(id);
        return brand;
    }

    /**
     * 修改品牌信息
     * @param brand
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Brand brand){
        try {
            //修改品牌
            EntityWrapper<Brand> wrapper=new EntityWrapper<>();
            wrapper.eq("id",brand.getId());
            brandService.update(brand,wrapper);
            //修改成功
            return new Result(true,"修改成功！");
        } catch (Exception e) {
            //修改失败
            // e.printStackTrace();
            return new Result(true,"修改失败！");

        }
    }

    /**
     * 根据id删除品牌
     * @param ids 品牌id数组
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
            brandService.deleteBatchIds(idlist);
            return  new Result(true,"删除成功");
        }catch (Exception e){
            return  new Result(false,"删除失败");
        }
    }

    /**
     * 品牌下拉框数据
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
       // System.out.println(brandService.selectOptionList());
        return brandService.selectOptionList();
    }
}

