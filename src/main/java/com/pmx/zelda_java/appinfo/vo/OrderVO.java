package com.pmx.zelda_java.appinfo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderVO {
    // 来自商品详情表的字段
    private Integer id;
    private Integer goodsId;
    private Integer goodsNum;
    // 来自商品表的字段
    private String goodsName;
    private Double goodsPrice;
    private String goodsImg;
}
