package com.pinyougou.portal.controller;


import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
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

    /**
     * 根据广告分类ID查询广告列表
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("/selectByCategoryId")
    public List<Content> seletByCategoryId(Long categoryId) {
        System.out.println("进入前台 selectBycategory");
        List<Content> contents = contentService.selectByCategoryId(categoryId);
        return contents;
    }

}