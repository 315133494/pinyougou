package com.pinyougou.manager.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.pojo.ContentCategory;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 内容分类 前端控制器
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 根据条件查询分页列表
     * @param current 当前页
     * @param size 每页显示的记录数
     * @param contentCategory 查询条件
     * @return
     */
    @RequestMapping(value = "/queryAllContentCategoryListByConditionPage",method = RequestMethod.POST)
    public PageResult queryAllContentCategoryListByConditionPage(@RequestParam(defaultValue = "1") int current,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestBody ContentCategory contentCategory){
        System.out.println("进入queryAllContentCategoryListByConditionPage。。。。。。。。。。。");
        //设置分页信息
        Page<ContentCategory> page = new Page<ContentCategory>(current, size);
        //查询后的结果
        Page<ContentCategory> page1 = contentCategoryService.queryAllContentCategoryListByConditionPage(page, contentCategory);
        //显示在页面信息类
        PageResult pageResult = new PageResult();
        //设置记录
        pageResult.setRows(page1.getRecords());
        //设置总记录数
        pageResult.setTotal(page1.getTotal());
        return pageResult;
    }

    @RequestMapping("/queryAllList")
    public List<ContentCategory> queryAllList(){
        return  contentCategoryService.selectList(null);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectByOne")
    public ContentCategory selectByOne(Long id){
        return contentCategoryService.selectById(id);
    }

    /**
     * 新增
     * @param contentCategory
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody ContentCategory contentCategory){
        try {
            contentCategoryService.insert(contentCategory);
            return new Result(true,"添加成功！");
        }catch (Exception e){
            return  new Result(false,"添加失败！");
        }
    }

    /**
     * 更新
     * @param contentCategory
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody ContentCategory contentCategory){
        try {
            contentCategoryService.updateById(contentCategory);
            return new Result(true,"添加成功！");
        }catch (Exception e){
            return  new Result(false,"添加失败！");
        }
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        List<Long> idList=new ArrayList<>();
        for (Long id:ids){
            idList.add(id);
        }
        try {
            contentCategoryService.deleteBatchIds(idList);
            return new Result(true,"删除成功！");
        }catch (Exception e){
            return  new Result(false,"删除失败！");
        }
    }
}

