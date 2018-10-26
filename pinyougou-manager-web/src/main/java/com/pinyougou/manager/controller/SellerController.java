package com.pinyougou.manager.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.Seller;
import com.pinyougou.result.PageResult;
import com.pinyougou.result.Result;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    /**
     * 根据条件查询品牌分页列表
     *
     * @param current 当前页
     * @param size    每页显示的总记录数
     * @param seller   按组件查询的对象
     * @return
     */
    @RequestMapping(value = "/queryAllSellerListByConditionPage", method = RequestMethod.POST)
    public PageResult queryAllSellerListByConditionPage(@RequestParam(defaultValue = "1") int current,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestBody Seller seller) {
        //设置分页信息
        Page<Seller> page = new Page<Seller>(current, size);
        //查询后的结果
        Page<Seller> page1 = sellerService.selectSellerListByConditionPage(page, seller);
        //显示在页面信息类
        PageResult pageResult = new PageResult();
        //设置记录
        pageResult.setRows(page1.getRecords());
        //设置总记录数
        pageResult.setTotal(page1.getTotal());
        return pageResult;
    }

    /**
     * 根据id查询
     * @param id id
     * @return
     */
    @RequestMapping("/selectByOne")
    public Seller selectByOne(Long id){
        Seller seller = sellerService.selectById(id);
        return seller;
    }

    /**
     * 更改状态
     * @param id id
     * @param status 状态
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long id,String status){
        try {
            sellerService.updateStatus(id,status);
            return new Result(true,"更新成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新失败！");
        }

    }
}

