package com.company.mall.sellergoods.service;

import com.company.entity.PageResult;
import com.company.mall.pojo.TbBrand;

import java.util.List;

/**
 * @description: 品牌管理接口类
 * @author: chunguang.yao
 * @date: 2019-06-30 12:51
 */
public interface BrandService {

    /**
     * 查询所有品牌
     * @return
     */
    List<TbBrand> findAll();

    /**
     * 返回分页列表
     * @param pageNum 当前页
     * @param pageSize 每页的记录数
     * @return
     */
    PageResult findPage(int pageNum, int pageSize);

    /**
     * 增加品牌
     * @param brand 品牌对象
     */
    void addBrand(TbBrand brand);

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    TbBrand findBrandById(long id);

    /**
     * 更新品牌信息
     * @param brand
     */
    void updateBrand(TbBrand brand);

    /**
     * 根据id删除品牌信息
     * @param ids
     */
    void deleteBrandById(long[] ids);

    /**
     * 带条件的分页查询
     * @param brand
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult findPageByCondition(TbBrand brand, int pageNum, int pageSize);
}