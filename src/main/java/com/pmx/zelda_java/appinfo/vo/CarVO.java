
// com.pmx.zelda_java.appinfo.vo.CarVO.java
package com.pmx.zelda_java.appinfo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CarVO {
    // 来自购物车表的字段
    private Integer id;
    private Integer goodsId;
    private Integer userId;
    private Integer goodsNum;
    private LocalDateTime creatTime;
    // 来自商品表的字段
    private String goodsName;
    private Double goodsPrice;
    private String goodsImg;
}
