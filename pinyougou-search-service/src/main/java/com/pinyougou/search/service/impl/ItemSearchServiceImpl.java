package com.pinyougou.search.service.impl;

import com.pinyougou.pojo.Item;
import com.pinyougou.search.service.ItemSearchService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();

        //1.查询列表
        Map searchListMap = searchList(searchMap);
        map.putAll(searchListMap);

        //2.根据关键字查询商品分类
        List categoryList = searchCategoryList(searchMap);
        map.put("categoryList", categoryList);
        //查询品牌和规格列表
        if (categoryList.size() > 0) {
            map.putAll(searchBrandAndSpecList((String) categoryList.get(0)));
        }
        return map;
    }

    /**
     * 导入数据
     * @param list
     */
    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    /**
     * 删除数据
     * @param goodsList
     */
    @Override
    public void deleteByGoodIds(List goodsList) {
        System.out.println("删除商品id"+goodsList);
        Query query=new SimpleQuery();
        Criteria criteria=new Criteria("item_goodsid").in(goodsList);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }


    /**
     * 根据关键字搜索列表
     *
     * @param searchMap
     * @return
     */
    private Map searchList(Map searchMap) {
        Map map = new HashMap();

        //关键字处理
        String keywords = (String) searchMap.get("keywords");
        searchMap.put("keywords",keywords.replace(" ",""));

        //高亮选项初始化
        HighlightQuery query = new SimpleHighlightQuery();

        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");//设置高亮显示域
        highlightOptions.setSimplePrefix("<em style='color:red'>");//高亮前辍
        highlightOptions.setSimplePostfix("</em>");//高亮后辍
        query.setHighlightOptions(highlightOptions);//设置高亮选项

        //1.1按照关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //1.2按商品分类筛选
        if (!"".equals(searchMap.get("category"))) {
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //1.3按品牌筛选
        if (!"".equals(searchMap.get("brand"))) {
            Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //1.4过滤规格
        if (searchMap.get("spec") != null) {
            Map<String, String> specMap = (Map) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                Criteria filterCriteria = new Criteria("item_spec_" + key).is(specMap.get(key));
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //1.5按价格筛选
        if (!"".equals(searchMap.get("price"))){
            String[] privce = ((String) searchMap.get("price")).split("-");
            if (!privce[0].equals("0")){
                //如果区间起点不等于0
                Criteria filterCriteria=new Criteria("item_price").greaterThanEqual(privce[0]);
                FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            if (!privce[1].equals("*")){
                //如果区间终点不等于*
                Criteria filterCriteria=new Criteria("item_price").lessThanEqual(privce[1]);
                FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //1.6分页查询
        //提取页码
        Integer pageNo = (Integer) searchMap.get("pageNo");
        if (pageNo==null){
            //默认设置第一页
            pageNo=1;
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");//每页记录数
        if (pageSize==null){
            //默认设置每页显示20条记录
            pageSize=20;
        }
        //从第几条记录查询
        query.setOffset((pageNo-1)*pageSize);
        //设置每页查询的记录数
        query.setRows(pageSize);

        //1.7 排序
        String sortValue = (String) searchMap.get("sort");//ASC  DESC
        String sortField = (String) searchMap.get("sortField");//排序字段
        if (sortValue!=null&&!"".equals(sortValue)){
            if (sortValue.equals("ASC")){
                Sort sort=new Sort(Sort.Direction.ASC,"item_"+sortField);
                query.addSort(sort);
            }
            if (sortValue.equals("DESC")){
                Sort sort=new Sort(Sort.Direction.DESC,"item_"+sortField);
                query.addSort(sort);
            }
        }

//********************获取高亮结果集开始******************
        //获取高亮页对象
        HighlightPage<Item> page = solrTemplate.queryForHighlightPage(query, Item.class);
        for (HighlightEntry<Item> h : page.getHighlighted()) {//循环高亮入口集合
            Item item = h.getEntity();//获得原实体类
            //获取高亮列表(高亮域的个数) h.getHighlights();
            //获取每个域有可能存储多值  h.getSnipplets()
            //获取要高亮的内容 h.getSnipplets.get(0);
            if (h.getHighlights().size() > 0 && h.getHighlights().get(0).getSnipplets().size() > 0) {
                item.setTitle(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮的结果
            }
        }
        //********************获取高亮结果集结束******************
        map.put("rows", page.getContent());
        map.put("totalPages", page.getTotalPages());//返回总页数
        map.put("total", page.getTotalElements());//返回总记录数
        map.put("pageNo",pageNo);//返回当前页
        return map;
    }

    /**
     * 查询分类列表
     *
     * @param searchMap
     * @return
     */
    public List searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList();

        Query query = new SimpleQuery();
        //按照关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //设置分组选项
        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(groupOptions);

        //得到分组页
        GroupPage<Item> page = solrTemplate.queryForGroupPage(query, Item.class);
        //根据列得到结果集
        GroupResult<Item> group_result = page.getGroupResult("item_category");
        //得到分组结果入口
        Page<GroupEntry<Item>> groupEntries = group_result.getGroupEntries();
        //得到分组入口集合
        List<GroupEntry<Item>> content = groupEntries.getContent();
        for (GroupEntry<Item> entity : content) {
            //将分组结果封装到返回值中
            list.add(entity.getGroupValue());
        }
        return list;
    }

    /**
     * 查询品牌和规格列表
     *
     * @param category 分类名称
     * @return
     */
    private Map searchBrandAndSpecList(String category) {
        Map map = new HashMap();
        //返回模板id
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        if (typeId != null) {
            //根据模板id查询品牌列表
            List brandList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
            //返回值添加到品牌列表
            map.put("brandList", brandList);

            //根据模板id查询规格列表
            List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
            map.put("specList", specList);
        }
        return map;
    }
}
