package com.pinyougou.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.Seller;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
public interface SellerService extends IService<Seller> {
    /**
     * 根据条件查询品牌分页列表
     * @param page 分页
     * @param seller 查询对象
     * @return
     */
    Page<Seller> selectSellerListByConditionPage(Page<Seller> page, Seller seller);

    /**
     * 更改状态
     * @param id
     * @param status
     */
    public void updateStatus(Long id,String status);

}
