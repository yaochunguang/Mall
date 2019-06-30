package com.company.mall.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.company.mall.mapper.TbBrandMapper;
import com.company.mall.pojo.TbBrand;
import com.company.mall.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description: 品牌管理类实现
 * @author: chunguang.yao
 * @date: 2019-06-30 13:02
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }
}
