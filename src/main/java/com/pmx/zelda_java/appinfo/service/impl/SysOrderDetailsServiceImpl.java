package com.pmx.zelda_java.appinfo.service.impl;

import com.pmx.zelda_java.appinfo.entity.Goods;
import com.pmx.zelda_java.appinfo.entity.SysOrderDetails;
import com.pmx.zelda_java.appinfo.mapper.SysOrderDetailsMapper;
import com.pmx.zelda_java.appinfo.service.IGoodsService;
import com.pmx.zelda_java.appinfo.service.ISysOrderDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pmx.zelda_java.appinfo.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class SysOrderDetailsServiceImpl extends ServiceImpl<SysOrderDetailsMapper, SysOrderDetails> implements ISysOrderDetailsService {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private SysOrderDetailsMapper sysOrderDetailsMapper;

    @Override
    public List<OrderVO> getDetailsListWithGoodsInfoByGoodsId(Integer orderId) {

        List<SysOrderDetails> orderList = this.lambdaQuery()
                .eq(SysOrderDetails::getOrderId, orderId)
                .list();
        return combineGoodsListWithGoodsInfo(orderList);
    }



    private List<OrderVO> combineGoodsListWithGoodsInfo(List<SysOrderDetails> detailsList) {
        // 如果购物车为空，直接返回空列表
        if (detailsList == null || detailsList.isEmpty()) {
            return List.of();
        }

        // 2. 提取所有商品ID
        List<Integer> goodsIdList = detailsList.stream()
                .map(SysOrderDetails::getGoodsId)
                .distinct() // 去重
                .collect(Collectors.toList());

        // 3. 批量查询商品信息
        List<Goods> goodsList = goodsService.listByIds(goodsIdList);

        // 4. 将商品列表转为Map，key为商品ID，value为商品对象
        Map<Integer, Goods> goodsMap = goodsList.stream()
                .collect(Collectors.toMap(Goods::getId, goods -> goods));

        // 5. 组合数据：将订单详情信息与商品信息合并
        return detailsList.stream().map(sysOrderDetails -> {
            OrderVO orderVO = new OrderVO();
            // 复制订单详情基础信息
            BeanUtils.copyProperties(sysOrderDetails, orderVO);

            // 获取对应的商品信息
            Goods goods = goodsMap.get(sysOrderDetails.getGoodsId());
            if (goods != null) {
                orderVO.setGoodsName(goods.getGoodsName());
                orderVO.setGoodsPrice(goods.getGoodsPrice());
                orderVO.setGoodsImg(goods.getGoodsImg());
            }
            return orderVO;
        }).collect(Collectors.toList());
    }
}
