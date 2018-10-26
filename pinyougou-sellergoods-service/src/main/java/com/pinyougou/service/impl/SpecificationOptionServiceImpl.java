package com.pinyougou.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationOptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
@Service
@Transactional
public class SpecificationOptionServiceImpl extends ServiceImpl<SpecificationOptionMapper, SpecificationOption> implements SpecificationOptionService {

}
