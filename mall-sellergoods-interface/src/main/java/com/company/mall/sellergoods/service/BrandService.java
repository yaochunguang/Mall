package com.company.mall.sellergoods.service;

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

}