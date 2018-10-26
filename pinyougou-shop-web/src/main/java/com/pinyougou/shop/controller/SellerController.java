package com.pinyougou.shop.controller;


import com.pinyougou.pojo.Seller;
import com.pinyougou.result.Result;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * 添加
     * @param seller 商家信息
     * @return
     */
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public Result add(@RequestBody Seller seller){
        //设置状态，0：未审核   1：已审核   2：审核未通过   3：关闭
        seller.setStatus("0");
        seller.setCreateTime(new Date());
        //密码加密,用BCryt加密
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(seller.getPassword());
        seller.setPassword(password);
        try {
            //执行添加
            sellerService.insert(seller);
            return new Result(true,"申请入驻成功！,请去登录！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"申请入驻成功失败！");
        }
    }
}

