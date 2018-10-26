package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
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
public interface SpecificationMapper extends BaseMapper<Specification> {
    /**
     * 规格下拉列表数据
     * @return
     */
    @Select("select id ,spec_name as text from tb_specification")
    List<Map> selectOptionList();
}
