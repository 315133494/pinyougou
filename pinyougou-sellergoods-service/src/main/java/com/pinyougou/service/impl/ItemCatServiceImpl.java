package com.pinyougou.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-02
 */
@Service
@Transactional
public class ItemCatServiceImpl extends ServiceImpl<ItemCatMapper, ItemCat> implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据上级ID返回列表
     * @param parentId 上级id
     * @return
     */
    @Override
    public List<ItemCat> selectByParentId(Long parentId) {
        //根据上级id查询
        EntityWrapper<ItemCat> wrapper=new EntityWrapper<>();
        wrapper.eq("parent_id",parentId);

        //缓存
        //每次执行查询的时候，一次性读取缓存进行存储 (因为每次增删改都要执行此方法)
        List<ItemCat> itemCats1 = selectList(null);
        for (ItemCat itemCat : itemCats1) {
            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());
        }

        //执行查询
        List<ItemCat> itemCats = itemCatMapper.selectList(wrapper);


        return itemCats;
    }
}
