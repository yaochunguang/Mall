package com.company.mall.sellergoods.service;

import com.company.entity.PageResult;
import com.company.mall.pojo.TbItemCat;

import java.util.List;

/**
 * 服务层接口
 * @author chunguang.yao
 *
 */
public interface ItemCatService {

	/**
	 * 返回全部列表
	 * @return
	 */
	List<TbItemCat> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	void add(TbItemCat itemCat);
	
	
	/**
	 * 修改
	 */
	void update(TbItemCat itemCat);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	TbItemCat findOne(Long id);
	
	
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
	PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize);

	/**
	 * 根据parentId查询是否有下级
	 * SELECT * FROM tb_item_cat t WHERE t.`parent_id` = parendId;  -> 结果等于 0 -> 没有下级
	 * SELECT * FROM tb_item_cat t WHERE t.`parent_id` = parendId;  -> 结果大于 0 -> 有下级
	 * @param parentId
	 * @return
	 */
	int getCountByParentId(Long parentId);

	/**
	 * 根据parentid查询列表
	 * @param parentId
	 * @return
	 */
	List<TbItemCat> findByParentId(Long parentId);
	
}
