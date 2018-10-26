package com.pinyougou.shop.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    @Autowired
    private TypeTemplateService typeTemplateService;

    /**
     * 按条件查询分页模板列表
     * @param current 当前页
     * @param size 每页显示的记录数
     * @param typeTemplate 查询条件
     * @return
     */
    @RequestMapping(value = "/queryAllTypeTemplateListByConditionPage",method = RequestMethod.POST)
    public PageResult queryAllTypeTemplateListByConditionPage(@RequestParam(defaultValue = "1")Integer current  ,
                                                              @RequestParam(defaultValue = "10") Integer size,
                                                              @RequestBody TypeTemplate typeTemplate){
        //设置分页信息
        Page<TypeTemplate> page=new Page<>(current,size);
        Page<TypeTemplate> page1=typeTemplateService.queryAllTypeTemplateListByConditionPage(page,typeTemplate);

        //返回结果
        PageResult pageResult=new PageResult();
        //设置总记录数
        pageResult.setTotal(page1.getTotal());
        //设置记录
        pageResult.setRows(page1.getRecords());
        return pageResult;
    }

    /**
     * 根据id查询模板
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectByOne")
    public TypeTemplate selectByOne(Long id){
        return typeTemplateService.selectById(id);
    }


    /**
     * 新增
     * @param typeTemplate
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.insert(typeTemplate);
            return new Result(true,"添加成功！");
        }catch (Exception e){
            return new Result(true,"添加失败！");
        }
    }

    /**
     * 修改
     * @param typeTemplate
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.updateById(typeTemplate);
            return new Result(true,"修改成功！");
        }catch (Exception e){
            return new Result(true,"修改失败！");
        }
    }


    /**
     * 根据id删除模板
     * @param ids 模板id数组
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
            typeTemplateService.deleteBatchIds(idlist);
            return  new Result(true,"删除成功");
        }catch (Exception e){
            return  new Result(false,"删除失败");
        }
    }

    /**
     * 返回规格列表
     * @param id
     * @return
     */
    @RequestMapping("/selectSpecList")
    public List<Map> selectSpecList(Long id){
        return typeTemplateService.selectSpecList(id);
    }
}

