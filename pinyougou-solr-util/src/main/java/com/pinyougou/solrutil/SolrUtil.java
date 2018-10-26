package com.pinyougou.solrutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;
    /**
     * 导入商品数据
     */
    public  void importItemData(){
        EntityWrapper<Item> wrapper=new EntityWrapper<>();
        //状态要为已审核的
        wrapper.eq("status","1");
        List<Item> items = itemMapper.selectList(wrapper);
        System.out.println("=======商品列表======");
        for (Item item:items){
            //将spec字段中的json字符串转换为map
            Map specMap = JSON.parseObject(item.getSpec());
            //给带注解的字段赋值
            item.setSpecMap(specMap);
            System.out.println(item.getTitle());
        }
        solrTemplate.saveBeans(items);
        solrTemplate.commit();

        System.out.println("=======结束======");

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
        solrUtil.importItemData();
    }

}
