package com.company.mall.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.company.entity.PageResult;
import com.company.mall.content.service.ContentService;
import com.company.mall.mapper.TbContentMapper;
import com.company.mall.pojo.TbContent;
import com.company.mall.pojo.TbContentExample;
import com.company.mall.pojo.TbContentExample.Criteria;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;


/**
 * 服务实现层
 *
 * @author chunguang.yao
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbContent content) {
        contentMapper.insert(content);
        // 清除redis缓存中的数据
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContent content) {
        // 查询修改前的分类id
        Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
        // 清除redis缓存中的数据
        redisTemplate.boundHashOps("content").delete(categoryId);
        contentMapper.updateByPrimaryKey(content);
        // 如果分类id发生的改变，清除修改后的id的缓存数据
        if(categoryId.longValue() != content.getCategoryId().longValue()) {
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            // 清除redis缓存中的数据
            Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();
            redisTemplate.boundHashOps("content").delete(categoryId);
            contentMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();

        if (content != null) {
            if (content.getTitle() != null && content.getTitle().length() > 0) {
                criteria.andTitleLike("%" + content.getTitle() + "%");
            }
            if (content.getUrl() != null && content.getUrl().length() > 0) {
                criteria.andUrlLike("%" + content.getUrl() + "%");
            }
            if (content.getPic() != null && content.getPic().length() > 0) {
                criteria.andPicLike("%" + content.getPic() + "%");
            }
            if (content.getStatus() != null && content.getStatus().length() > 0) {
                criteria.andStatusLike("%" + content.getStatus() + "%");
            }
        }

        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<TbContent> findByCategoryId(Long caegoryId) {
        // 先判断redis缓存中是否存在缓存数据
        List<TbContent> contentList = (List<TbContent>)redisTemplate.boundHashOps("content").get(caegoryId);
        // 如果缓存中没有，则从数据库中获取
        if(null == contentList) {
            System.out.println("从数据库读取数据");
            TbContentExample example = new TbContentExample();
            Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(caegoryId);
            // 状态为有效的
            criteria.andStatusEqualTo("1");
            // 排序
            example.setOrderByClause("sort_order");
            contentList = contentMapper.selectByExample(example);
            // 将数据库的数据存入缓存
            redisTemplate.boundHashOps("content").put(caegoryId, contentList);
        } else {
            System.out.println("从缓存读取数据");
        }
        return contentList;
    }

}
