package com.leyou.service;

import com.bw.leyou.pojo.Category;
import com.bw.leyou.pojo.CategoryBrand;
import com.leyou.exception.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mapper.CategoryBrandMapper;
import com.leyou.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     *
     * @param pid 根据pid查询分类
     * @return
     */
    public List<Category> queryCategoryByParentId(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categoriesList = categoryMapper.select(category);
        if(categoriesList == null || categoriesList.isEmpty()){
            throw new LyException(ExceptionEnum.PRICE_CANNOT_BY_NULL);
        }
        return categoriesList;
    }

    /**
     *
     * @param bid 根据品牌id查询中间表获取分类表id，根据分类表id查询分类，
     *            三级联动回显
     * @return
     */
    public List<Category> queryCategoryByBid(Long bid) {

        return categoryMapper.queryCategoryByBid(bid);
    }

    /**
     * 根据id查询分类
     */
    public List<Category> queryCategoryCids(List<Long> cids){
        List<Category> categories = categoryMapper.selectByIdList(cids);
        if(categories == null || categories.isEmpty()){
            throw new LyException(ExceptionEnum.PRICE_CANNOT_BY_NULL);
        }
        return categories;
    }
}
