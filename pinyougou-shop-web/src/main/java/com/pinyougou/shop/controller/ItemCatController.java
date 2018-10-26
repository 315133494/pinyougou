package com.pinyougou.shop.controller;


import com.pinyougou.pojo.ItemCat;
import com.pinyougou.result.Result;
import com.pinyougou.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author ZhiCaiLi
 * @since 2018-10-02
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    /**
     * 根据上级ID返回列表
     * @param parentId 上级id
     * @return
     */
    @RequestMapping("/selectByParentId")
    public List<ItemCat> selectByParentId(Long parentId) {
        List<ItemCat> itemCats = itemCatService.selectByParentId(parentId);
        return itemCats;
    }

    /**
     * 查询全部分类
     * @return
     */
    @RequestMapping("/selectAll")
    public List<ItemCat> selectAll(){
        List<ItemCat> itemCats = itemCatService.selectList(null);
        return itemCats;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody ItemCat itemCat){
        try {
            itemCatService.insert(itemCat);
            return new Result(true,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败！");
        }
    }

    //根据id查询
    @RequestMapping("/selectByOne")
    public ItemCat selectByOne(Long id){
        return itemCatService.selectById(id);
    }

    //修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody ItemCat itemCat){
        try {
            itemCatService.updateById(itemCat);
            return new Result(true,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败！");
        }
    }
    //删除
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        //定义返回结果
        List<Long> idlist=new ArrayList<>();
        for (Long id:ids) {
            idlist.add(id);
        }
        try {
            //批量删除
            itemCatService.deleteBatchIds(idlist);
            return  new Result(true,"删除成功");
        }catch (Exception e){
            return  new Result(false,"删除失败");
        }
    }

}

