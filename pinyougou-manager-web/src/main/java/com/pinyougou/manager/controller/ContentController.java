package com.pinyougou.manager.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.Content;
import com.pinyougou.pojo.ContentCategory;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 根据条件查询分页列表
     *
     * @param current 当前页
     * @param size    每页显示的记录数
     * @return
     */
    @RequestMapping(value = "/queryAllContentListByConditionPage", method = RequestMethod.POST)
    public PageResult queryAllContentCategoryListByConditionPage(@RequestParam(defaultValue = "1") int current,
                                                                 @RequestParam(defaultValue = "10") int size) {
        System.out.println("进入queryAllContentListByConditionPage。。。。。。。。。。。");
        //设置分页信息
        Page<Content> page = new Page<Content>(current, size);
        //查询后的结果
        page = contentService.selectPage(page);
        //显示在页面信息类
        PageResult pageResult = new PageResult();
        //设置记录
        pageResult.setRows(page.getRecords());
        //设置总记录数
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectByOne")
    public Content selectByOne(Long id) {

        return contentService.selectById(id);
    }

    /**
     * 新增
     *
     * @param content
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Content content) {
        try {
            contentService.insert(content);
            //清除缓存
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
            return new Result(true, "添加成功！");
        } catch (Exception e) {
            return new Result(false, "添加失败！");
        }
    }

    /**
     * 更新
     *
     * @param content
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody Content content) {
        //查询修改前的分类id
        Long categoryId = contentService.selectById(content.getId()).getCategoryId();
        //清除缓存
        redisTemplate.boundHashOps("content").delete(categoryId);

        try {
            contentService.updateById(content);
            //如果分类id发生了修改，清除修改后的分类id的缓存
            if (categoryId.longValue()!=content.getCategoryId().longValue()){
                redisTemplate.boundHashOps("content").delete(content.getCategoryId());
            }

            return new Result(true, "添加成功！");
        } catch (Exception e) {
            return new Result(false, "添加失败！");
        }
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        List<Long> idList = new ArrayList<>();
        for (Long id : ids) {
            idList.add(id);
            //清除缓存
            Long categoryId = contentService.selectById(id).getCategoryId();//广告分类id
            redisTemplate.boundHashOps("content").delete(categoryId);
        }
        try {
            contentService.deleteBatchIds(idList);
            return new Result(true, "删除成功！");
        } catch (Exception e) {
            return new Result(false, "删除失败！");
        }
    }

    @RequestMapping("/status")
    public Result status(Long[] ids, String status) {
        List<Long> idList = new ArrayList<>();
        for (Long id : ids) {
            idList.add(id);
            //清除缓存
            Long categoryId = contentService.selectById(id).getCategoryId();//广告分类id
            redisTemplate.boundHashOps("content").delete(categoryId);
        }
        try {
            List<Content> contents = contentService.selectBatchIds(idList);
            for (Content content:contents){
                content.setStatus(status);
                contentService.updateById(content);
            }
            return new Result(true, "更新成功！");
        } catch (Exception e) {
            return new Result(false, "更新失败！");
        }
    }
}

