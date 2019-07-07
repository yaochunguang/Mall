package com.company.mall.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.company.entity.PageResult;
import com.company.mall.mapper.TbBrandMapper;
import com.company.mall.pojo.TbBrand;
import com.company.mall.pojo.TbBrandExample;
import com.company.mall.sellergoods.service.BrandService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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

    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        // 使用PageHelper插件
        PageHelper.startPage(pageNum, pageSize);

        Page<TbBrand> page = (Page<TbBrand>)brandMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void addBrand(TbBrand brand) {
        brandMapper.insert(brand);
    }

    @Override
    public TbBrand findBrandById(long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateBrand(TbBrand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void deleteBrandById(long[] ids) {
        for (long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult findPageByCondition(TbBrand brand, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        if(null != brand) {
            if(null != brand.getName() && brand.getName().length() > 0) {
                criteria.andNameLike("%" + brand.getName() + "%");
            }
            if(null != brand.getFirstChar() && brand.getFirstChar().length() > 0) {
                criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");
            }
        }
        Page<TbBrand> page = (Page<TbBrand>)brandMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectBrandList() {
        return brandMapper.selectBrandList();
    }
}
