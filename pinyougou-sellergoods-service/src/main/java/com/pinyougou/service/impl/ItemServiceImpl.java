package com.pinyougou.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-02
 */
@Service
@Transactional
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
