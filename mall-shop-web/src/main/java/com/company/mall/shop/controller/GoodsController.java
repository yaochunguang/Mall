package com.company.mall.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.company.entity.PageResult;
import com.company.entity.Result;
import com.company.mall.pojo.TbGoods;
import com.company.mall.pojogroup.Goods;
import com.company.mall.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 *
 * @author chunguang.yao
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods) {
        try {
            // 获取登录商家的登录名作为商家id
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goods.getGoods().setSellerId(sellerId);
            goodsService.add(goods);
            return new Result(true, "增加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Goods goods) {
        // 检验当前所修改的商品是否是属于当前商家的 -- beg
        // 根据商品id查询当前的商品
        Goods goods1 = goodsService.findOne(goods.getGoods().getId());
        // 获取登陆的商家id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        // 检验
        if(!sellerId.equals(goods1.getGoods().getSellerId()) || !goods.getGoods().getSellerId().equals(sellerId)) {
            return new Result(false, "非法操作!");
        }
        // 检验当前所修改的商品是否是属于当前商家的 -- end

        try {
            goodsService.update(goods);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        // 设置登录商家的商家id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(sellerId);
        return goodsService.findPage(goods, page, rows);
    }

}
