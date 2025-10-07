package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.appinfo.entity.Banner;
import com.pmx.zelda_java.appinfo.entity.Car;
import com.pmx.zelda_java.appinfo.entity.SysOrder;
import com.pmx.zelda_java.appinfo.service.IBannerService;
import com.pmx.zelda_java.appinfo.service.ICarService;
import com.pmx.zelda_java.appinfo.service.IGoodsService;
import com.pmx.zelda_java.appinfo.service.ISysOrderService;
import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.pmx.zelda_java.appinfo.vo.CarVO;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
@RestController
@RequestMapping("/appinfo/car")
public class CarController {

    @Autowired
    private ICarService carService;

    @Autowired
    private ISysOrderService sysOrderService;

    @PostMapping("numSub")
    public AjaxResult numSub(@RequestBody Car car){
        try{
            Car existingCar = carService.findById(car.getId());
            if(existingCar != null && existingCar.getGoodsNum()!=1){
                existingCar.setGoodsNum(existingCar.getGoodsNum()-car.getGoodsNum());
                boolean flag=carService.updateById(existingCar);
                return AjaxResult.success("购物车已更改");
            }
            else if(existingCar != null && existingCar.getGoodsNum()==1){
                carService.removeById(existingCar.getId());
                return AjaxResult.success("商品已删除");
            }
            else{
                return AjaxResult.fail("购物车中未找到该商品");
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail("改变购物车失败！");
        }
    }

    @PostMapping("numPlus")
    public AjaxResult numPlus(@RequestBody Car car){
        try{
            Car existingCar = carService.findById(car.getId());
            if(existingCar != null){
                existingCar.setGoodsNum(existingCar.getGoodsNum() + car.getGoodsNum());
                boolean flag=carService.updateById(existingCar);
            }
            else {
                boolean flag = carService.save(car);
            }
            return AjaxResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail("添加购物车失败！");
        }
    }


    @PostMapping("save")
    public AjaxResult saveCar(@RequestBody Car car){
        try{
            Car existingCar = carService.findByUserIdAndGoodsId(car.getUserId(), car.getGoodsId());
            if(existingCar != null){
                existingCar.setGoodsNum(existingCar.getGoodsNum() + car.getGoodsNum());
                boolean flag=carService.updateById(existingCar);
            }
            else {
                boolean flag = carService.save(car);
            }
            return AjaxResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail("添加购物车失败！");
        }
    }


    @PostMapping("/deleteOrderedGoods")
    public AjaxResult deleteOrderedGoods(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.valueOf(params.get("userId").toString());
            List<Long> carIds = (List<Long>) params.get("carIds");

            if (carIds == null || carIds.isEmpty()) {
                return AjaxResult.fail("参数错误");
            }

            // 批量删除购物车商品
            boolean deleted = carService.removeByIds(carIds);
            if (deleted) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("删除异常");
        }
    }


    @GetMapping("List")
    public Object findAll(){
        try{
            var list = carService.getCarListWithGoodsInfo();
            return AjaxResult.success(list);
        }
        catch(Exception e){
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }

    @GetMapping("ListByUser")
    public Object findByUserId(@RequestParam Long userId) {
        try {
            // 根据用户ID查询购物车列表
            var list = carService.getCarListWithGoodsInfoByUserId(userId);
            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.exception(e.getMessage());
        }
    }
}
