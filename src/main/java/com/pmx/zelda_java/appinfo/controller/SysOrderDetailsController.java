package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.appinfo.entity.SysOrder;
import com.pmx.zelda_java.appinfo.entity.SysOrderDetails;
import com.pmx.zelda_java.appinfo.service.ICarService;
import com.pmx.zelda_java.appinfo.service.IGoodsService;
import com.pmx.zelda_java.appinfo.service.ISysOrderDetailsService;
import com.pmx.zelda_java.appinfo.service.ISysOrderService;
import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
@RestController
@RequestMapping("/appinfo/sysOrderDetails")
public class SysOrderDetailsController {

    @Autowired
    private ISysOrderDetailsService sysOrderDetailsService;

    @Autowired
    private ISysOrderService sysOrderService;

    @GetMapping("ListByOrder")
    public Object findByUserId(@RequestParam Integer orderId) {
        try {
            // 根据订单ID查询购物车列表
            var list = sysOrderDetailsService.getDetailsListWithGoodsInfoByGoodsId(orderId);
            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }


}
