package com.leyou.mapper;

import com.bw.leyou.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {

    @Update("UPDATE tb_brand SET name=#{brand.name},image = #{brand.image},letter = #{brand.letter} WHERE ID = #{brand.id}")
    int updateBrand(@Param("brand") Brand brand);
    @Select("SELECT b.* FROM tb_brand b LEFT JOIN tb_category_brand cb ON b.id = cb.brand_id WHERE cb.category_id = #{cid}")
    List<Brand> qeryBrandByCid(@Param("cid") Long cid);
}
