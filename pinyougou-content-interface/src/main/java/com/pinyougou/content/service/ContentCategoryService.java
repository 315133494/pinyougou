package com.pinyougou.content.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.ContentCategory;

/**
 * <p>
 * 内容分类 服务类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
public interface ContentCategoryService extends IService<ContentCategory> {

    /**
     * 根据条件查询分页列表
     * @param page 分页信息
     * @param contentCategory 查询条件
     * @return
     */
    Page<ContentCategory> queryAllContentCategoryListByConditionPage(Page<ContentCategory> page, ContentCategory contentCategory);
}
