package com.bw.leyou.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分类表与品牌表的关系表
 */
@Table(name = "tb_category_brand")
@Data
public class CategoryBrand {

    @Id
    private Long categoryId;
    @Id
    private Long  brandId;
}
