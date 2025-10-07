package com.pmx.zelda_java.appinfo.controller;


import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pmx.zelda_java.appinfo.DTO.OrderGoodsDTO;
import com.pmx.zelda_java.appinfo.DTO.OrderSubmitDTO;
import com.pmx.zelda_java.appinfo.entity.SysOrder;
import com.pmx.zelda_java.appinfo.entity.SysOrderDetails;
import com.pmx.zelda_java.appinfo.service.ISysOrderDetailsService;
import com.pmx.zelda_java.appinfo.service.ISysOrderService;
import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/appinfo/sysOrder")
public class SysOrderController {

    @Autowired
    private ISysOrderService sysOrderService;

    @Autowired
    private ISysOrderDetailsService sysOrderDetailsService;

    @GetMapping("List")
    public Object findAll(){
        try{
            List<SysOrder> list=sysOrderService.list();
            return AjaxResult.success(list);
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }

    @GetMapping("ListByUser")
    public Object findByUserId(@RequestParam Integer userId) {
        try {
            List<SysOrder> list = sysOrderService.lambdaQuery()
                    .eq(SysOrder::getUserId, userId)
                    .list();
            return AjaxResult.success(list);
        } catch(Exception e) {
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }

    @PostMapping("/submitOrder")
    public AjaxResult submitOrder(@RequestBody OrderSubmitDTO orderSubmitDTO) {
        try {
            // 1. 创建订单主表记录
            SysOrder order = new SysOrder();
            order.setUserId(orderSubmitDTO.getUserId());
            order.setOrderTotal(orderSubmitDTO.getTotalPrice());
            order.setOrderNum(generateOrderNo(order.getUserId()));
            order.setCreatTime(LocalDateTime.now());
            order.setFirstImg(orderSubmitDTO.getFirstImg());
            order.setFirstName(orderSubmitDTO.getFirstName());

            boolean orderSaved = sysOrderService.save(order);

            if (orderSaved) {
                // 2. 创建订单明细记录
                List<SysOrderDetails> orderDetailsList = new ArrayList<>();

                for (OrderGoodsDTO item : orderSubmitDTO.getOrderGoods()) {
                    SysOrderDetails detail = new SysOrderDetails();
                    detail.setOrderId(order.getId());
                    detail.setOrderNum(order.getOrderNum());
                    detail.setGoodsId(item.getGoodsId());
                    detail.setGoodsNum(item.getGoodsNum());
                    detail.setGoodsPrice(item.getGoodsPrice());
                    orderDetailsList.add(detail);
                }

                // 批量保存订单明细
                boolean detailsSaved = sysOrderDetailsService.saveBatch(orderDetailsList);

                if (detailsSaved) {
                    return AjaxResult.success("订单创建成功");
                }
            }

            return AjaxResult.fail("订单创建失败");

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("订单提交异常");
        }
    }

    // 生成订单号
    private String generateOrderNo(Integer userId) {
        // 生成12位随机数
        String randomNum = String.format("%012d", (int)(Math.random() * 1000000000000L));
        return userId + "_" + randomNum;
    }


}
