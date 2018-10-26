package com.pinyougou.service;

import com.baomidou.mybatisplus.service.IService;
import com.pinyougou.pojo.ItemCat;

import java.util.List;

/**
 * <p>
 * 商品类目 服务类
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-02
 */
public interface ItemCatService extends IService<ItemCat> {
    /**
     * 根据上级ID返回列表
     * @param parentId 上级id
     * @return
     */
    public List<ItemCat> selectByParentId(Long parentId);

}
