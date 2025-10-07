package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.appinfo.entity.Content;
import com.pmx.zelda_java.appinfo.service.IContentService;
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
 * @since 2025-09-17
 */
@RestController
@RequestMapping("/appinfo/content")
public class ContentController {

    @Autowired
    private IContentService contentService ;

    @GetMapping("List")
    public Object findAll(){
        try{
            List<Content> list=contentService.list();
            return AjaxResult.success(list);
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }

    }

}
