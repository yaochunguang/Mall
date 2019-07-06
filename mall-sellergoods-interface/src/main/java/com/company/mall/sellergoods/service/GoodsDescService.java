package com.company.mall.sellergoods.service;

import com.company.entity.PageResult;
import com.company.mall.pojo.TbGoodsDesc;

import java.util.List;
/**
 * 服务层接口
 * @author chunguang.yao
 *
 */
public interface GoodsDescService {

	/**
	 * 返回全部列表
	 * @return
	 */
	List<TbGoodsDesc> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	void add(TbGoodsDesc goodsDesc);
	
	
	/**
	 * 修改
	 */
	void update(TbGoodsDesc goodsDesc);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	TbGoodsDesc findOne(Long id);
	
	
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
	PageResult findPage(TbGoodsDesc goodsDesc, int pageNum, int pageSize);
	
}
