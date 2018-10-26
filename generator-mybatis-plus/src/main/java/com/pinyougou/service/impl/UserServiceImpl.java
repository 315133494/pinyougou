package com.pinyougou.service.impl;

import com.pinyougou.pojo.User;
import com.pinyougou.mapper.UserMapper;
import com.pinyougou.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
