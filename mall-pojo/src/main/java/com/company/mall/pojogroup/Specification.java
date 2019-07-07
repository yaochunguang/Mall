package com.company.mall.pojogroup;

import com.company.mall.pojo.TbSpecification;
import com.company.mall.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 规格组合实体类
 * @author: chunguang.yao
 * @date: 2019-07-07 22:30
 */
public class Specification implements Serializable {

    private TbSpecification specification;

    private List<TbSpecificationOption> specificationOptionList;

    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
