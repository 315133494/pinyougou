package com.pinyougou.content.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.mapper.ContentCategoryMapper;
import com.pinyougou.pojo.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 内容分类 服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
@Service
@Transactional
public class ContentCategoryServiceImpl extends ServiceImpl<ContentCategoryMapper, ContentCategory> implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;
    /**
     * 根据条件查询分页列表
     * @param page
     * @param contentCategory
     * @return
     */
    @Override
    public Page<ContentCategory> queryAllContentCategoryListByConditionPage(Page<ContentCategory> page, ContentCategory contentCategory) {
        EntityWrapper<ContentCategory> wrapper=new EntityWrapper<>();
        //按名称模糊查询
        if (contentCategory!=null&&!"".equals(contentCategory.getName())){
            wrapper.like("name",contentCategory.getName());
        }
        //执行查询
        List<ContentCategory> contentCategories = contentCategoryMapper.selectPage(page, wrapper);
        //设置记录
        page.setRecords(contentCategories);
        return page;
    }
}
