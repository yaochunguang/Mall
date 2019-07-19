package com.company.mall.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登陆处理类
 * @author: chunguang.yao
 * @date: 2019-07-20 0:14
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/getLoginName")
    public Map getLoginName(String sellerId) {
        Map map = new HashMap();
        // 通过SpringSecurity框架获取登录名称
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("loginName", name);
        return map;
    }
}
