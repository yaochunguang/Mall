package com.company.mall.pojogroup;

import com.company.mall.pojo.TbGoods;
import com.company.mall.pojo.TbGoodsDesc;
import com.company.mall.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 商品和商品明细组合实体类
 * @author: chunguang.yao
 * @date: 2019-07-29 23:04
 */
public class Goods implements Serializable {

    // 商品SPU
    private TbGoods goods;

    // 商品SKU
    private TbGoodsDesc goodsDesc;

    // 商品SKU列表
    private List<TbItem> itemList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
