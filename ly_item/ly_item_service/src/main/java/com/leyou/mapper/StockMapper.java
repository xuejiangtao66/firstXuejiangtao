package com.leyou.mapper;

import com.bw.leyou.pojo.Stock;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface StockMapper extends Mapper<Stock>, InsertListMapper<Stock> {

}
