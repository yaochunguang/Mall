package com.company.mall.sellergoods.service;
import java.util.List;
import com.company.mall.pojo.TbSpecification;

import com.company.entity.PageResult;
/**
 * 服务层接口
 * @author chunguang.yao
 *
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	List<TbSpecification> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	void add(TbSpecification specification);
	
	
	/**
	 * 修改
	 */
	void update(TbSpecification specification);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	TbSpecification findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageResult findPage(TbSpecification specification, int pageNum, int pageSize);
	
}
