package com.pinyougou.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
@Service
@Transactional
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements SellerService {
    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 根据条件查询品牌分页列表
     * @param page 分页
     * @param seller 查询对象
     * @return
     */
    @Override
    public Page<Seller> selectSellerListByConditionPage(Page<Seller> page, Seller seller) {
        EntityWrapper<Seller> wrapper=new EntityWrapper<>();
        //按组件模糊查询
        if (seller!=null){
            //品牌名称
            if (seller.getName()!=null){
                wrapper.like("name",seller.getName());
            }
            //品牌首字母
            if (seller.getNickName()!=null){
                wrapper.like("nick_name",seller.getNickName());
            }
            //激活状态
            if (seller.getStatus().equals("0")){
                wrapper.like("status",seller.getStatus());
            }
        }
        //按条件分页查询
        List<Seller> sellers = sellerMapper.selectPage(page, wrapper);
        //设置记录进返回的结果
        page.setRecords(sellers);
        return page;
    }
    /**
     * 更改状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, String status) {
        Seller seller = sellerMapper.selectById(id);
        //设置状态
        seller.setStatus(status);
        //修改
        sellerMapper.updateById(seller);
    }
}
