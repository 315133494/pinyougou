package com.pinyougou.service.impl;

import com.pinyougou.pojo.Item;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.service.ItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
