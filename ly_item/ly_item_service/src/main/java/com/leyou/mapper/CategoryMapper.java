package com.leyou.mapper;

import com.bw.leyou.pojo.Category;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category,Long> {


    @Select("SELECT * FROM tb_category WHERE id IN(SELECT b.category_id FROM tb_category_brand b WHERE brand_id = #{bid})")
    List<Category> queryCategoryByBid(Long bid);
}
