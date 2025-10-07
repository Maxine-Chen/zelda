package com.pmx.zelda_java.appinfo.DTO;

import lombok.Data;

@Data
public class OrderGoodsDTO {
    private Integer goodsId;
    private Integer goodsNum;
    private double goodsPrice;
    private String goodsName;
    private String goodsImg;
}
