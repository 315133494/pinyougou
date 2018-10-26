package com.pinyougou.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.pojo.GoodsDesc;
import com.pinyougou.service.GoodsDescService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-02
 */
@Service
@Transactional
public class GoodsDescServiceImpl extends ServiceImpl<GoodsDescMapper, GoodsDesc> implements GoodsDescService {

}
