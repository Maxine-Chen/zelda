package com.pmx.zelda_java.appinfo.service;

import com.pmx.zelda_java.appinfo.entity.SysOrderDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pmx.zelda_java.appinfo.vo.CarVO;
import com.pmx.zelda_java.appinfo.vo.OrderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
public interface ISysOrderDetailsService extends IService<SysOrderDetails> {
    List<OrderVO> getDetailsListWithGoodsInfoByGoodsId(Integer orderId);
}
