package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.appinfo.entity.Tool;
import com.pmx.zelda_java.appinfo.service.IBannerService;
import com.pmx.zelda_java.appinfo.service.IToolService;
import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/appinfo/tool")
public class ToolController {

    @Autowired
    private IToolService toolService;

    @GetMapping("List")
    public Object findAll(){
        try{
            List<Tool> list =toolService.list();
            return AjaxResult.success(list);
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }
}
