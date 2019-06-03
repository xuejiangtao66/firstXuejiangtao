package com.bw.leyou.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "tb_spu")
@Data
public class Goods {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;


    private Long brandId;
    private String title;

    private String subTitle;

    private Long cid1;
    /**
     * 2级类目
     */
    private Long cid2;
    /**
     * 3级类目
     */
    private Long cid3;

    private Boolean saleable;

    @JsonIgnore
    private Boolean valid;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    @JsonIgnore
    private Date lastUpdateTime;

    @Transient //告诉通用mapper不是数据库字段
    private String cname;

    @Transient
    private String bname;

    @Transient
    private List<Sku> skus;

    @Transient
    private SpuDetail spuDetail;

    /*id	bigint
    title	varchar
    sub_title	varchar
    cid1	bigint
    cid2	bigint
    cid3	bigint
    brand_id	bigint
    saleable	tinyint
    valid	tinyint
    create_time	datetime
    last_update_time	datetime*/

}
