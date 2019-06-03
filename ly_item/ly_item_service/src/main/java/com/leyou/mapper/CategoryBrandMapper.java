package com.leyou.mapper;

import com.bw.leyou.pojo.CategoryBrand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface CategoryBrandMapper extends Mapper<CategoryBrand> {
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{id} ")
    int deleteByBid(@Param("id") Long id);
}
