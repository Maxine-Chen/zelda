package com.pmx.zelda_java.appinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
@TableName("sys_order_details")
public class SysOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    /**
     * 订单编号
     */
    private String orderNum;

    private Integer goodsId;

    /**
     * 购买该商品的数量
     */
    private Integer goodsNum;

    /**
     * 购买时的商品价格
     */
    private Double goodsPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getGoodsId() {return goodsId;}

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString() {
        return "SysOrderDetails{" +
            "id = " + id +
            ", orderId = " + orderId +
            ", orderNum = " + orderNum +
            ", goodsId = " + goodsId +
            ", goodsNum = " + goodsNum +
            ", goodsPrice = " + goodsPrice +
            "}";
    }
}
