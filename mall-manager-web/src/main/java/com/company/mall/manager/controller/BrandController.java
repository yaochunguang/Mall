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
import java.util.Map;

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

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    @RequestMapping("/findBrandById")
    public TbBrand findBrandById(Long id) {
        return brandService.findBrandById(id);
    }

    /**
     * 更新品牌信息
     * @param brand
     * @return
     */
    @RequestMapping("/updateBrand")
    public  Result updateBrand(@RequestBody TbBrand brand) {
        try {
            brandService.updateBrand(brand);
            return  new Result(true, "修改成功!");
        } catch (Exception e) {
            return  new Result(false, "修改失败!");
        }
    }

    /**
     * 删除品牌信息
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBrandById")
    public Result deleteBrandById(long[] ids) {
        try {
            brandService.deleteBrandById(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            return new Result(false, "删除失败!");
        }
    }

    /**
     * 带条件分页查询
     * @param brand
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, int page, int rows) {
        return brandService.findPageByCondition(brand, page, rows);
    }

    /**
     * 品牌下拉框数据
     * @return
     */
    @RequestMapping("/selectBrandList")
    public List<Map> selectBrandList() {
        return brandService.selectBrandList();
    }
}
