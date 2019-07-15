package com.company.mall.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登录处理器
 * @author: chunguang.yao
 * @date: 2019-07-15 23:18
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 返回登录名
     * @return
     */
    @RequestMapping("getLoginName")
    public Map getLoginName() {
        Map map = new HashMap();
        // 通过SpringSecurity框架获取登录名称
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("loginName", name);
        return map;
    }
}
