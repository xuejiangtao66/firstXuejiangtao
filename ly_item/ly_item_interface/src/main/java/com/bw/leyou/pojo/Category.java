package com.bw.leyou.pojo;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品分类表
 */
@Table(name="tb_category") //对应数据库中哪张表
@Data
public class Category {

    @Id
    @KeySql(useGeneratedKeys = true) //是否返回主键
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;

}
