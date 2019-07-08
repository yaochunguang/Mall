package com.company.mall.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.company.entity.PageResult;
import com.company.mall.mapper.TbSpecificationMapper;
import com.company.mall.mapper.TbSpecificationOptionMapper;
import com.company.mall.pojo.TbSpecification;
import com.company.mall.pojo.TbSpecificationExample;
import com.company.mall.pojo.TbSpecificationExample.Criteria;
import com.company.mall.pojo.TbSpecificationOption;
import com.company.mall.pojo.TbSpecificationOptionExample;
import com.company.mall.pojogroup.Specification;
import com.company.mall.sellergoods.service.SpecificationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author chunguang.yao
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    // 规格mapper
    @Autowired
    private TbSpecificationMapper specificationMapper;

    // 规格选项mapper
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Specification specification) {
        specificationMapper.insert(specification.getSpecification());
        // 循环插入规格选项
        for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
            // 设置规格选项中关联的规格id
            specificationOption.setSpecId(specification.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(Specification specification) {
        // 更新规格
        specificationMapper.updateByPrimaryKey(specification.getSpecification());
        // 更新规格选项【思路：因为不知道用户是新增的选项还是删除了，所以同时是先删除原来规格选项，再新增】
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(specification.getSpecification().getId());
        // 删除
        specificationOptionMapper.deleteByExample(example);
        // 新增
        for(TbSpecificationOption tbSpecificationOption : specification.getSpecificationOptionList()) {
            tbSpecificationOption.setSpecId(specification.getSpecification().getId());
            specificationOptionMapper.insert(tbSpecificationOption);
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Specification findOne(Long id) {
        // 查询规格
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        // 查询规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);

        // 封装返回实体
        Specification specification = new Specification();
        specification.setSpecification(tbSpecification);
        specification.setSpecificationOptionList(specificationOptionList);
        return specification;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            // 删除规格
            specificationMapper.deleteByPrimaryKey(id);
            // 删除规格选项
            TbSpecificationOptionExample example  = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }


    @Override
    public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbSpecificationExample example = new TbSpecificationExample();
        Criteria criteria = example.createCriteria();

        if (specification != null) {
            if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
                criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
            }

        }

        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectSpecificationdList() {
        return specificationMapper.selectSpecificationdList();
    }

}
