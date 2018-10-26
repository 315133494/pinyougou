package com.pinyougou.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.service.GoodsService;
import com.pinyougou.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 根据条件查询分页列表
     * @param page 分页条件
     * @param goods 查询条件
     * @return
     */
    @Override
    public Page<Goods> queryAllGoodsListByConditionPage(Page<Goods> page, Goods goods) {
        EntityWrapper<Goods> wrapper=new EntityWrapper<>();
        //按组件模糊查询
        if (goods!=null){
            //商家 id
            if (goods.getSellerId()!=null&&!"".equals(goods.getSellerId())){
                wrapper.eq("seller_id",goods.getSellerId());
            }
            //状态
            if (goods.getAuditStatus()!=null&&!"".equals(goods.getAuditStatus())){
                wrapper.eq("audit_status",goods.getAuditStatus());
            }
            //是否上下架
            if (goods.getIsMarketable()!=null&&!"".equals(goods.getIsMarketable())){
                wrapper.eq("is_marketable",goods.getIsMarketable());
            }
            //商品名称
            if (goods.getGoodsName()!=null){
                wrapper.like("goods_name",goods.getGoodsName());
            }
        }
        //只查询未逻辑删除的
        wrapper.isNull("is_delete");
        //按条件分页查询
        List<Goods> goodsList = goodsMapper.selectPage(page, wrapper);
        //设置记录进返回的结果
        page.setRecords(goodsList);
        return page;
    }


    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    @Override
    public GoodsVo selectByOne(Long id) {
        //获取goods信息
        Goods goods = goodsMapper.selectById(id);

        GoodsVo goodsVo=new GoodsVo();
        goodsVo.setGoods(goods);

        //获取 GoodsDesc
        EntityWrapper<GoodsDesc> wrapper=new EntityWrapper<>();
        wrapper.eq("goods_id",id);
        GoodsDesc goodsDesc = goodsDescMapper.selectList(wrapper).get(0);

        goodsVo.setGoodsDesc(goodsDesc);

        //查询sku商品列表
        EntityWrapper<Item> wrapperItem=new EntityWrapper<>();
        wrapperItem.eq("goods_id",id);
        List<Item> items = itemMapper.selectList(wrapperItem);
        goodsVo.setItemList(items);
        return goodsVo;
    }


    /**
     * 增加
     * @param goodsVo
     */
    @Override
    public void add(GoodsVo goodsVo) {
        //设置未申请状态
        goodsVo.getGoods().setAuditStatus("0");
        Integer insert = goodsMapper.insert(goodsVo.getGoods());
        //设置商品扩展id
        goodsVo.getGoodsDesc().setGoodsId(goodsVo.getGoods().getId());
        //插入商品扩展数据
        goodsDescMapper.insert(goodsVo.getGoodsDesc());
        //插入商品sku列表数据
        saveItemList(goodsVo);

    }

    /**
     * 更新
     * @param goodsVo
     */
    @Override
    public void update(GoodsVo goodsVo) {
        //设置未审核状态，如果是经过修改的商品，需要重新设计状态
        goodsVo.getGoods().setAuditStatus("0");
        //保存商品表
        goodsMapper.updateById(goodsVo.getGoods());
        //保存商品扩展表
        goodsDescMapper.updateById(goodsVo.getGoodsDesc());
        //删除原有的sku列表数据
        EntityWrapper<Item> wrapper=new EntityWrapper<>();
        wrapper.eq("goods_id",goodsVo.getGoods().getId());
        itemMapper.delete(wrapper);
        //添加新的sku列表数据
        //插入商品sku列表数据
        saveItemList(goodsVo);
    }

    /**
     * 根据商品id和状态查询item表的信息
     * @param ids
     * @param status
     * @return
     */
    @Override
    public List<Item> selectItemListByGoodsIdandStatus(Long[] ids, String status) {
        EntityWrapper<Item> wrapper=new EntityWrapper<>();
        wrapper.in("goods_id",Arrays.asList(ids) );
        wrapper.eq("status",status);
        System.out.println("ids="+Arrays.asList(ids)+"     status="+status);
        List<Item> items = itemMapper.selectList(wrapper);
        return items;
    }

    private void saveItemList(GoodsVo goodsVo){
        if ("1".equals(goodsVo.getGoods().getIsEnableSpec())){
            for (Item item:goodsVo.getItemList()){
                //标题
                String title = goodsVo.getGoods().getGoodsName();
                Map<String,Object> specMap= JSON.parseObject(item.getSpec());
                for (String  key:specMap.keySet()){
                    title+=" "+specMap.get(key);
                }
                item.setTitle(title);

                setItemValues(goodsVo,item);
                itemMapper.insert(item);
            }
        }else {
            Item item=new Item();
            //商品KPU+规格描述串作为SKU名称
            item.setTitle(goodsVo.getGoods().getGoodsName());
            //价格
            item.setPrice( goodsVo.getGoods().getPrice() );
            //状态
            item.setStatus("1");
            //是否默认
            item.setIsDefault("1");
            //库存数量
            item.setNum(99999);
            item.setSpec("{}");
            for (Item it :goodsVo.getItemList()) {
                System.out.println(it);
            }
            setItemValues(goodsVo,item);
            itemMapper.insert(item);
        }
    }
    private  void setItemValues(GoodsVo goodsVo,Item item){
        //商品spu编号
        item.setGoodsId(goodsVo.getGoods().getId());
        //商家编号
        item.setSellerId(goodsVo.getGoods().getSellerId());
        //商品分类编号（3级
        System.out.println("item.setCategoryId===="+goodsVo.getGoods().getCategory3Id());
        item.setCategoryId(goodsVo.getGoods().getCategory3Id());
        //创建时间
        item.setCreateTime(new Date());
        //修改时间
        item.setUpdateTime(new Date());
        //品牌名称
        System.out.println("goodsVo.getGoods().getBrandId()=="+goodsVo.getGoods().getBrandId());
        Brand brand = brandMapper.selectById(goodsVo.getGoods().getBrandId());


        item.setBrand(brand.getName());
        //分类名称
        ItemCat itemCat = itemCatMapper.selectById(goodsVo.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        //商家名称
        EntityWrapper<Seller> wrapper=new EntityWrapper<>();
        wrapper.eq("seller_id",goodsVo.getGoods().getSellerId());
        Seller seller = sellerMapper.selectList(wrapper).get(0);
        item.setSeller(seller.getNickName());
        //图片地址(取spu的第一个图片)
        List<Map> imageList = JSON.parseArray(goodsVo.getGoodsDesc().getItemImages(), Map.class);
        if (imageList.size()>0){
            item.setImage((String)imageList.get(0).get("url"));
        }
    }
}
