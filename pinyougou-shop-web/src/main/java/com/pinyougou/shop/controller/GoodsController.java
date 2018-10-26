package com.pinyougou.shop.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.Goods;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import com.pinyougou.service.GoodsService;
import com.pinyougou.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
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
        //获取商家id
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //添加查询条件
        goods.setSellerId(name);
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
     *
     * @param id
     * @return
     */
    @RequestMapping("/selectByOne")
    public GoodsVo selectByOne(Long id) {
        return goodsService.selectByOne(id);
    }

    /**
     * 添加
     *
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody GoodsVo goodsVo) {
        //获取登录名
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //设置商家id
        goodsVo.getGoods().setSellerId(sellerId);
        try {
            goodsService.add(goodsVo);
            return new Result(true, "增加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败！");
        }
    }

    /**
     * 修改
     *
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody GoodsVo goodsVo) {
        //校验是否是当前商家的Id
        GoodsVo goodsVo1 = goodsService.selectByOne(goodsVo.getGoods().getId());
        //获取当前登录的商家id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //如果传递进来的商家id并不是当前登录的用户的id，则属于非法操作
        if (!goodsVo1.getGoods().getSellerId().equals(sellerId) || !goodsVo.getGoods().getSellerId().equals(sellerId)) {
            return new Result(false, "非法操作！");
        }
        try {
            goodsService.update(goodsVo);
            return new Result(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败！");
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
            return new Result(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败！");
        }
    }
    //提交审核
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

            return  new Result(true,"提交成功！");
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,"提交失败！");
        }
    }

    //上架与下架
    @RequestMapping("/isMarketable")
    public Result isMarketable(Long[] ids, String status) {
        List<Long> idList = new ArrayList();
        for (Long id : ids) {
            idList.add(id);
        }
        try {
            List<Goods> goodsList = goodsService.selectBatchIds(idList);
            for (Goods goods : goodsList) {
                goods.setIsMarketable(status);
                goodsService.updateById(goods);
            }
            return new Result(true, "提交成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "提交失败！");
        }
    }
}

