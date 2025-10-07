package com.pmx.zelda_java.appinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pmx.zelda_java.appinfo.entity.Car;
import com.pmx.zelda_java.appinfo.entity.Goods;
import com.pmx.zelda_java.appinfo.mapper.CarMapper;
import com.pmx.zelda_java.appinfo.service.ICarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.pmx.zelda_java.appinfo.service.IGoodsService;
import com.pmx.zelda_java.appinfo.vo.CarVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private CarMapper carMapper;


    @Override
    public Car findById(Integer id) {
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return carMapper.selectOne(queryWrapper);
    }

    @Override
    public List<CarVO> getCarListWithGoodsInfo() {
        // 1. 查询所有购物车记录
        List<Car> carList = this.list();
        return combineCarWithGoodsInfo(carList);
    }

    @Override
    public List<CarVO> getCarListWithGoodsInfoByUserId(Long userId) {
        // 1. 根据用户ID查询购物车记录
        List<Car> carList = this.lambdaQuery()
                .eq(Car::getUserId, userId)
                .list();
        return combineCarWithGoodsInfo(carList);
    }

    @Override
    public Car findByUserIdAndGoodsId(Integer userId,Integer goodsId) {
        // 使用QueryWrapper查询
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("goods_id", goodsId);
        return carMapper.selectOne(queryWrapper);
    }

    /**
     * 通用的组合购物车和商品信息的方法
     */
    private List<CarVO> combineCarWithGoodsInfo(List<Car> carList) {
        // 如果购物车为空，直接返回空列表
        if (carList == null || carList.isEmpty()) {
            return List.of();
        }

        // 2. 提取所有商品ID
        List<Integer> goodsIdList = carList.stream()
                .map(Car::getGoodsId)
                .distinct() // 去重
                .collect(Collectors.toList());

        // 3. 批量查询商品信息
        List<Goods> goodsList = goodsService.listByIds(goodsIdList);

        // 4. 将商品列表转为Map，key为商品ID，value为商品对象
        Map<Integer, Goods> goodsMap = goodsList.stream()
                .collect(Collectors.toMap(Goods::getId, goods -> goods));

        // 5. 组合数据：将购物车信息与商品信息合并
        return carList.stream().map(car -> {
            CarVO carVO = new CarVO();
            // 复制购物车基础信息
            BeanUtils.copyProperties(car, carVO);

            // 获取对应的商品信息
            Goods goods = goodsMap.get(car.getGoodsId());
            if (goods != null) {
                carVO.setGoodsName(goods.getGoodsName());
                carVO.setGoodsPrice(goods.getGoodsPrice());
                carVO.setGoodsImg(goods.getGoodsImg());
            }
            return carVO;
        }).collect(Collectors.toList());
    }
}
