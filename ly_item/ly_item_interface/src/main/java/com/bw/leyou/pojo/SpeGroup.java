package com.bw.leyou.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_spec_group") //对应数据库中哪张表
@Data
public class SpeGroup {

    @Id
    private Long id;
    private Long cid;
    private String name;
}
