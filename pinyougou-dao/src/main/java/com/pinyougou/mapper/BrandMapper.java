package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-09-26
 */
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 品牌下拉数据
     * @return
     */
    @Select("select id ,name as text from tb_brand")
     List<Map> selectOptionList();
}
