package com.pinyougou.content.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.Content;
import com.pinyougou.pojo.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
@Service
@Transactional
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Content> selectByCategoryId(Long categoryId) {
        List<Content> contents =(List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
        if (contents==null){
            System.out.println("从数据库读取数据放入缓存");
            EntityWrapper<Content> wrapper=new EntityWrapper<>();
            wrapper.eq("category_id",categoryId);
            contents = contentMapper.selectList(wrapper);
            redisTemplate.boundHashOps("content").put(categoryId,contents);
        }else {
            System.out.println("从缓存读取数据");
        }
        return contents;
    }

}
