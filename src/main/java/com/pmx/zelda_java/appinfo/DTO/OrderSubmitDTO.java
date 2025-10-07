package com.pmx.zelda_java.appinfo.DTO;


import lombok.Data;

import java.util.List;

@Data
public class OrderSubmitDTO {
    private Integer userId;
    private double totalPrice;
    private List<OrderGoodsDTO> orderGoods;
    private String firstName;
    private String firstImg;
}
