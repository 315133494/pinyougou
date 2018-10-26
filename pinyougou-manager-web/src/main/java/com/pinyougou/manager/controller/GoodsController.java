package com.pinyougou.manager.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
//import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.Item;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import com.pinyougou.service.GoodsService;
import com.pinyougou.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
  //  @Autowired
 //   private ItemSearchService itemSearchService;
   // @Autowired
  //  private ItemPageService itemPageService;

    @Autowired
    private Destination queueSolrDestination;//用于发送solr导入的消息

    @Autowired
    private Destination queueSolrDeleteDestination;//用户在索引库中删除记录

    @Autowired
    private Destination topicPageDestination;//用户在索引库中删除记录

    @Autowired
    private Destination topicPageDeleteDestination;//用于删除静态网页的消息

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 根据条件查询分页列表
     *
     * @param current 当前页
     * @param size    每页显示的总记录数
     * @param goods   按组件查询的对象
     * @return
     */

    @RequestMapping(value = "/queryAllGoodsListByConditionPage", method = RequestMethod.POST)
    public PageResult queryAllGoodsListByConditionPage(@RequestParam(defaultValue = "1") int current,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestBody Goods goods) {
        //设置分页信息
        Page<Goods> page = new Page<Goods>(current, size);
        //查询后的结果
        Page<Goods> page1 = goodsService.queryAllGoodsListByConditionPage(page, goods);
        //显示在页面信息类
        PageResult pageResult = new PageResult();
        //设置记录
        pageResult.setRows(page1.getRecords());
        //设置总记录数
        pageResult.setTotal(page1.getTotal());
        return pageResult;
    }

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    @RequestMapping("/selectByOne")
    public GoodsVo selectByOne(Long id){
        return goodsService.selectByOne(id);
    }

    /**
     * 添加
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody GoodsVo goodsVo){
        //获取登录名
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //设置商家id
        goodsVo.getGoods().setSellerId(sellerId);
        try {
            goodsService.add(goodsVo);
            return  new Result(true,"增加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,"增加失败！");
        }
    }
    /**
     * 修改
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody GoodsVo goodsVo){
        //校验是否是当前商家的Id
        GoodsVo goodsVo1 = goodsService.selectByOne(goodsVo.getGoods().getId());
        //获取当前登录的商家id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //如果传递进来的商家id并不是当前登录的用户的id，则属于非法操作
        if (!goodsVo1.getGoods().getSellerId().equals(sellerId)||!goodsVo.getGoods().getSellerId().equals(sellerId)){
            return new Result(false,"非法操作！");
        }
        try {
            goodsService.update(goodsVo);
            return new Result(true,"修改成功！");
        }catch (Exception e ){
            e.printStackTrace();
            return  new Result(false,"修改失败！");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        List<Long> idList = new ArrayList();
        for (Long id : ids) {
            idList.add(id);
        }
        try {
            List<Goods> goodsList = goodsService.selectBatchIds(idList);
            for (Goods goods : goodsList) {
                goods.setIsDelete("1");//审核中的状态
                goodsService.updateById(goods);
            }
            //删除缓存
            //itemSearchService.deleteByGoodIds(Arrays.asList(ids));

            jmsTemplate.send(queueSolrDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });

            //删除页面
            jmsTemplate.send(topicPageDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {

                    return session.createObjectMessage(ids);
                }
            });




            return new Result(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败！");
        }
    }

    @RequestMapping("/audit")
    public Result audit(Long[] ids,String status){
        List<Long> idList=new ArrayList();
        for (Long id:ids) {
            idList.add(id);
        }
        try {
            List<Goods> goodsList = goodsService.selectBatchIds(idList);
            for (Goods goods :goodsList) {
                goods.setAuditStatus(status);//审核中的状态
                goodsService.updateById(goods);
            }
            //*********导入缓存*****************
            if (status.equals("2")){//审核通过
                status="1";
                List<Item> items = goodsService.selectItemListByGoodsIdandStatus(ids, status);
                    //调用搜索接口实现数据批量导入
                    if(items.size()>0){
                        //itemSearchService.importList(items);

                        final String jsonString = JSON.toJSONString(items);
                        jmsTemplate.send(String.valueOf(queueSolrDestination), new  MessageCreator() {
                            @Override
                            public Message createMessage(Session session) throws JMSException {
                                System.out.println("调用搜索接口实现数据批量导入");
                                return session.createTextMessage(jsonString);
                            }
                        });
                    }else{
                        System.out.println("没有明细数据");
                    }
            }

            //静态页面生成
           /* for (Long goodsId:ids){
                itemPageService.genItemHtml(goodsId);
            }*/
            for ( final Long goodsId : ids) {
                jmsTemplate.send(topicPageDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {

                        return session.createTextMessage(goodsId+"");
                    }
                });
            }

            //*********导入缓存结束*****************
            return  new Result(true,"提交成功！");
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,"提交失败！");
        }
    }

    //上架与下架
    @RequestMapping("/isMarketable")
    public Result isMarketable(Long[] ids,String status){
        List<Long> idList=new ArrayList();
        for (Long id:ids) {
            idList.add(id);
        }
        try {
            List<Goods> goodsList = goodsService.selectBatchIds(idList);
            for (Goods goods :goodsList) {
                goods.setIsMarketable(status);
                goodsService.updateById(goods);
            }
            return  new Result(true,"提交成功！");
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,"提交失败！");
        }
    }

    @RequestMapping("/genHtml")
    public void genHtml(Long goodsId){
        System.out.println("genHtml");
      //  itemPageService.genItemHtml(goodsId);
    }

}

