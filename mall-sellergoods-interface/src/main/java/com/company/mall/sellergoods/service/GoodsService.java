package com.company.mall.sellergoods.service;
import java.util.List;
import com.company.mall.pojo.TbGoods;

import com.company.entity.PageResult;
import com.company.mall.pojogroup.Goods;

/**
 * 服务层接口
 * @author chunguang.yao
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	void add(Goods goods);
	
	
	/**
	 * 修改
	 */
	void update(Goods goods);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	Goods findOne(Long id);
	
	
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
	PageResult findPage(TbGoods goods, int pageNum, int pageSize);

	/**
	 * 商品审核  --  更新状态
	 * 审核通过的状态值为1，驳回的状态值为2
	 * @param ids
	 * @param status
	 */
	void updateStatus(Long[] ids, String status);
	
}
