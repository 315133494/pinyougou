package com.pinyougou.content.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.Content;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
public interface ContentService extends IService<Content> {
    /**
     * 根据分类广告Id查询广告列表
     * @param categoryId
     * @return
     */
     List<Content> selectByCategoryId(Long categoryId);
}
