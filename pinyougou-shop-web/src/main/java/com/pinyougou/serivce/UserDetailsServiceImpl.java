package com.pinyougou.serivce;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 认证类
 */
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("经过了UserDetailsServiceImpl");
        //构建角色列表
        List<GrantedAuthority> grantedAuths=new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        //得到商家对象
        EntityWrapper<Seller> wrapper=new EntityWrapper<>();
        wrapper.eq("seller_id",username);
        Seller seller = sellerService.selectOne(wrapper);
        if (seller!=null){
            if (seller.getStatus().equals("1")){
                return new User(username,seller.getPassword(),grantedAuths);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
