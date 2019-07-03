package com.company.mall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.company.entity.PageResult;
import com.company.entity.Result;
import com.company.mall.sellergoods.service.BrandService;
import com.company.mall.pojo.TbBrand;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return brandService.findPage(page, rows);
    }

    /**
     * 增加品牌
     * @return
     */
    @RequestMapping("/addBrand")
    public Result addBrand(@RequestBody TbBrand brand) {
        try {
            brandService.addBrand(brand);
            return  new Result(true, "新增成功!");
        } catch (Exception e) {
            return  new Result(false, "新增失败!");
        }
    }
}
