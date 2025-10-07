package com.pmx.zelda_java.appinfo.controller;

import com.pmx.zelda_java.appinfo.entity.SysUser;
import com.pmx.zelda_java.appinfo.service.ISysUserService;
import com.pmx.zelda_java.config.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pmx
 * @since 2025-08-29
 */
@RestController
@RequestMapping("/appinfo/sysUser")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String, String> loginData) {
        try {
            String usernum = loginData.get("usernum");
            String password = loginData.get("password");

            // 参数验证
            if (usernum == null || usernum.trim().isEmpty()) {
                return AjaxResult.fail("用户名不能为空");
            }
            if (password == null || password.trim().isEmpty()) {
                return AjaxResult.fail("密码不能为空");
            }

            // 查询用户
            SysUser user = sysUserService.lambdaQuery()
                    .eq(SysUser::getUserNum, usernum)
                    .one();

            if (user == null) {
                return AjaxResult.fail("用户不存在");
            }

            // 验证密码
            if (!password.equals(user.getUserPwd())) {
                return AjaxResult.fail("密码错误");
            }

            // 返回用户id
            Map<String, Object> result = new HashMap<>();
            result.put("userName", user.getUserName());
            result.put("userId", user.getId()); // 明确返回用户Id

            return AjaxResult.success(result);

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("登录异常：" + e.getMessage());
        }
    }

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysUser user) {
        try {
            if (user.getUserNum() == null || user.getUserNum().trim().isEmpty()) {
                return AjaxResult.fail("用户名不能为空");
            }
            if (user.getUserPwd() == null || user.getUserPwd().trim().isEmpty()) {
                return AjaxResult.fail("密码不能为空");
            }

            // 检查用户名是否已存在
            SysUser existingUser = sysUserService.lambdaQuery()
                    .eq(SysUser::getUserNum, user.getUserNum())
                    .one();
            if (existingUser != null) {
                return AjaxResult.fail("用户名已存在");
            }


            boolean saved = sysUserService.save(user);
            if (saved) {
                return AjaxResult.success("注册成功");
            } else {
                return AjaxResult.fail("注册失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("注册异常：" + e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public AjaxResult getUserInfo(@RequestParam Long userId) {
        try {
            SysUser user = sysUserService.getById(userId);
            if (user == null) {
                return AjaxResult.fail("用户不存在");
            }

            // 隐藏敏感信息
            user.setUserPwd(null);

            return AjaxResult.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("获取用户信息失败");
        }
    }
}
