package com.company.mall.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.company.mall.content.service.ContentService;
import com.company.mall.pojo.TbContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 网站前台
 * @author: chunguang.yao
 * @date: 2019-09-20 0:12
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/findByCategoryId")
    public List<TbContent> findByCategoryId(Long categoryId) {
        return contentService.findByCategoryId(categoryId);
    }
}
