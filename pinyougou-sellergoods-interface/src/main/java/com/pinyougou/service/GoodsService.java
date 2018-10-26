package com.pinyougou.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.Item;
import com.pinyougou.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-02
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 根据条件查询分页列表
     * @param page 分页条件
     * @param goods 查询条件
     * @return
     */
    public Page<Goods> queryAllGoodsListByConditionPage(Page<Goods> page, Goods goods);

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public GoodsVo selectByOne(Long id);

    /**
     * 增加
     * @param goodsVo
     */
    public void add(GoodsVo goodsVo);

    /**
     * 更新
     * @param goodsVo
     */
    public  void update(GoodsVo goodsVo);

    /**
     * 根据商品id和状态查询item表的信息
     * @param ids
     * @param status
     * @return
     */
    public List<Item> selectItemListByGoodsIdandStatus(Long[] ids,String status);
}
