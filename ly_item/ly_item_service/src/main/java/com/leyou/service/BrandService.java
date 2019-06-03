package com.leyou.service;

import com.bw.leyou.pojo.Brand;
import com.bw.leyou.pojo.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.exception.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mapper.BrandMapper;
import com.leyou.mapper.CategoryBrandMapper;
import com.leyou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    public PageResult<Brand> queryAllBrand(Integer page, Integer rowsPerPage, String sortBy, Boolean descending, String search) {
        //分页
        PageHelper.startPage(page,rowsPerPage);
        Example example = new Example(Brand.class);

        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByProperty = sortBy+ " " + (descending ? "DESC":"ASC");

            example.setOrderByClause(orderByProperty);
        }

        //过滤 模糊
        if(StringUtils.isNotBlank(search)){
            example.createCriteria().orLike("name",search+"%").orEqualTo("letter", search.toUpperCase());
        }


        //查询
        List<Brand> list = brandMapper.selectByExample(example);
        if(list == null || CollectionUtils.isEmpty(list)){
            throw  new LyException(ExceptionEnum.BRAND_IS_NOT_EXITS);
        }

        //解析
        PageInfo<Brand> inofo = new PageInfo<>(list);
        return new PageResult<>(inofo.getTotal(),list);
    }

    /**
     * 新增品牌
     * @param brand 品牌
     * @param categories 分类的id
     */
    @Transactional
    public void insertBrand(Brand brand, Long[] categories) {

        //新增品牌
        int count = brandMapper.insertSelective(brand);

        if(count !=1){
            throw new LyException(ExceptionEnum.INSERT_BRAND_ERRO);
        }

        //新增品牌分类关系表
        for (long category_id:categories) {
            CategoryBrand categoryBrand  = new CategoryBrand();
            categoryBrand.setBrandId(brand.getId());
            categoryBrand.setCategoryId(category_id);
            int insert = categoryBrandMapper.insert(categoryBrand);
            if(insert != 1){
                throw  new LyException(ExceptionEnum.INSERT_BRAND_ERRO);
            }
        }
    }

    public Brand querBrandByBid(Long bid) {

        return brandMapper.selectByPrimaryKey(bid);
    }

    /**
     *
     * @param id 根据id删除商品品牌
     */
    @Transactional
    public void deleteBrandById(Long id) {

        int row = brandMapper.deleteByPrimaryKey(id);
        if(row != 1){
                throw  new LyException(ExceptionEnum.DELETE_BRAND_FAIL);
        }
        categoryBrandMapper.deleteByBid(id);
    }


    /**
     * 修改品牌
     * @param brand
     * @param categories
     */
    public void updateaBrand(Brand brand, List<Long> categories) {
        int row = brandMapper.updateBrand(brand);
        if(row != 1){
            throw  new LyException(ExceptionEnum.BRAND_UPDATE_FAIL);
        }
        for (long cid:categories) {
            CategoryBrand categoryBrand  = new CategoryBrand();
            categoryBrand.setBrandId(brand.getId());
            categoryBrand.setCategoryId(cid);
            int insert = categoryBrandMapper.insert(categoryBrand);
            if(insert != 1){
                throw  new LyException(ExceptionEnum.BRAND_UPDATE_FAIL);
            }
        }
    }

    /**
     *
     * @param bid 修改时候，先删除中间表
     */
    public void deleteBrandCategoryByBid(long bid) {
        categoryBrandMapper.deleteByBid(bid);
    }

    public List<Brand> qeryBrandByCid(Long cid) {
        return brandMapper.qeryBrandByCid(cid);
    }
}
