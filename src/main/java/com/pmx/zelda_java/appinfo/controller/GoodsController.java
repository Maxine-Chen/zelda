package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.appinfo.entity.Goods;
import com.pmx.zelda_java.appinfo.service.IGoodsService;
import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
@RestController
@RequestMapping("/appinfo/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @GetMapping("List")
    public Object findAll(){
        try{
            List<Goods> list=goodsService.list();
            return AjaxResult.success(list);
        }catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }
}
