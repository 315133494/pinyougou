package com.pinyougou.page.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.mapper.GoodsMapper;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.GoodsDesc;
import com.pinyougou.pojo.Item;
import com.pinyougou.pojo.ItemCat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ItemPageServiceImpl implements ItemPageService {
    @Value("${pagedir}")
    private String pagedir;
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public boolean genItemHtml(Long goodsId) {

        try {
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map dataModel=new HashMap<>();
            //1、加载商品表数据
            Goods goods = goodsMapper.selectById(goodsId);
            dataModel.put("goods",goods);
            //2、加载商品扩展表数据
            Wrapper<GoodsDesc> wrapper=new EntityWrapper<>();
            wrapper.eq("goods_id",goodsId);
            GoodsDesc goodsDesc = goodsDescMapper.selectList(wrapper).get(0);
            Writer out = new FileWriter(pagedir + goodsId + ".html");
            dataModel.put("goodsDesc",goodsDesc);

            //3、商品分类

            String itemCat1 = itemCatMapper.selectById(goods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectById(goods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectById(goods.getCategory3Id()).getName();

            dataModel.put("itemCat1", itemCat1);
            dataModel.put("itemCat2", itemCat2);
            dataModel.put("itemCat3", itemCat3);

            //4、sku列表
            Wrapper<Item> itemwarpper=new EntityWrapper<>();
            //状态为有效
            itemwarpper.eq("status","1");
            //指定spu id
            itemwarpper.eq("goods_id",goodsId);
            List<Item> itemList = itemMapper.selectList(itemwarpper);

            dataModel.put("itemList",itemList);


            template.process(dataModel,out);
            out.close();
            System.out.println("hwhw");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean deleteItemHtml(Long[] goodsIds) {
        try {
            for (Long goodsId : goodsIds) {
                new File(pagedir+goodsId+".html").delete();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }


}
