package com.pmx.zelda_java.appinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
@TableName("sys_order")
public class SysOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNum;

    private LocalDateTime creatTime;

    private Integer userId;

    /**
     * 总金额
     */
    private Double orderTotal;

    private String firstImg;

    private String firstName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public LocalDateTime getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(LocalDateTime creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) { this.firstImg = firstImg;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName;}


    @Override
    public String toString() {
        return "SysOrder{" +
            "id = " + id +
            ", orderNum = " + orderNum +
            ", creatTime = " + creatTime +
            ", userId = " + userId +
            ", orderTotal = " + orderTotal +
                ", firstImg = " + firstImg +
                ", firstName = " + firstName +
            "}";
    }
}
