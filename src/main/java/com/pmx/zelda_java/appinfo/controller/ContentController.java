package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.appinfo.entity.Content;
import com.pmx.zelda_java.appinfo.service.IContentService;
import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("getById")
    public Object getById(@RequestParam Integer id){
        try{
            Content content = contentService.getById(id);
            if(content != null){
                return AjaxResult.success(content);
            }
            else{
                return AjaxResult.fail("内容不存在");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }

    @PostMapping("add")
    public Object add(@RequestBody Content content){
        try{
            boolean result = contentService.save(content);
            if(result){
                return AjaxResult.success("添加成功");
            }
            else{
                return AjaxResult.fail("添加失败");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }

    @PostMapping("update")
    public Object update(@RequestBody Content content){
        try{
            boolean result = contentService.updateById(content);
            if(result){
                return AjaxResult.success("更新成功");
            }
            else{
                return AjaxResult.fail("更新失败");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }

    @PostMapping("delete")
    public Object delete(@RequestParam Integer id){
        try{
            boolean result = contentService.removeById(id);
            if(result){
                return AjaxResult.success("删除成功");
            }
            else{
                return AjaxResult.fail("删除失败");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }

}
