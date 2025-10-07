package com.pmx.zelda_java.appinfo.service.impl;

import com.pmx.zelda_java.appinfo.entity.Car;
import com.pmx.zelda_java.appinfo.entity.Goods;
import com.pmx.zelda_java.appinfo.entity.SysOrder;
import com.pmx.zelda_java.appinfo.mapper.CarMapper;
import com.pmx.zelda_java.appinfo.mapper.SysOrderMapper;
import com.pmx.zelda_java.appinfo.service.IGoodsService;
import com.pmx.zelda_java.appinfo.service.ISysOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pmx.zelda_java.appinfo.vo.CarVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder> implements ISysOrderService {


}
