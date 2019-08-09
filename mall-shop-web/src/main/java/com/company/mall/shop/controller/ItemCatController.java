package com.company.mall.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.company.entity.PageResult;
import com.company.entity.Result;
import com.company.mall.pojo.TbItemCat;
import com.company.mall.sellergoods.service.ItemCatService;
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
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbItemCat> findAll() {
        return itemCatService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return itemCatService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param itemCat
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbItemCat itemCat) {
        try {
            itemCatService.add(itemCat);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param itemCat
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbItemCat itemCat) {
        try {
            itemCatService.update(itemCat);
            return new Result(true, "修改成功");
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
    public TbItemCat findOne(Long id) {
        return itemCatService.findOne(id);
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
            // 是否能删除的标志，默认为true  --表示能删除
            boolean canDel = true;
            String message = "删除成功!";
            for (Long id : ids) {
                int countByParentId = itemCatService.getCountByParentId(id);
                if (countByParentId > 0) {
                    // 如果有下级，则不能进行删除
                    canDel = false;
                    break;
                }
            }
            if (canDel) {
                itemCatService.delete(ids);
            } else {
                message = "当前记录中存在有下级记录，不能删除该记录！";
                return new Result(false, message);
            }

            return new Result(true, message);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param itemCat
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbItemCat itemCat, int page, int rows) {
        return itemCatService.findPage(itemCat, page, rows);
    }

    /**
     * 根据parentId查询列表信息
     * @param parentId
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<TbItemCat> findByParentId(Long parentId) {
        return itemCatService.findByParentId(parentId);
    }
}
