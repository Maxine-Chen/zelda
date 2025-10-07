package com.pmx.zelda_java.appinfo.service;

import com.pmx.zelda_java.appinfo.entity.Car;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pmx.zelda_java.appinfo.vo.CarVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
public interface ICarService extends IService<Car> {

    /**
     * 根据ID查询购物车记录
     */
    Car findById(Integer id);

    /**
     * 查询购物车列表并关联商品信息
     * @return 包含商品详细信息的购物车列表
     */
    List<CarVO> getCarListWithGoodsInfo();

    /**
     * 根据用户ID查询购物车列表并关联商品信息
     * @param userId 用户ID
     * @return 包含商品详细信息的购物车列表
     */
    List<CarVO> getCarListWithGoodsInfoByUserId(Long userId);

    /**
     * 根据用户ID和商品ID查询购物车记录
     */
    Car findByUserIdAndGoodsId( Integer userId,  Integer goodsId);

}
