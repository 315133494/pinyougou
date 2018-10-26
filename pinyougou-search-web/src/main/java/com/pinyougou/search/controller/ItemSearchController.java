package com.pinyougou.search.controller;

import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/itemSearch")
public class ItemSearchController {
    @Autowired
    private ItemSearchService itemSearchService;

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Map<String,Object> search(@RequestBody Map searchMap){
        System.out.println("search................");
        System.out.println(itemSearchService.search(searchMap));
        return itemSearchService.search(searchMap);
    }
}
