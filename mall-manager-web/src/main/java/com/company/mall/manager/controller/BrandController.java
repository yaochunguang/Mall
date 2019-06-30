package com.company.mall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.company.mall.sellergoods.service.BrandService;
import com.company.mall.pojo.TbBrand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 品牌管理
 * @author: chunguang.yao
 * @date: 2019-06-30 13:06
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }
}
