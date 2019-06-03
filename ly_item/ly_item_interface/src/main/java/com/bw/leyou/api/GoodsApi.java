package com.bw.leyou.api;

import com.bw.leyou.pojo.Goods;
import com.bw.leyou.pojo.Sku;
import com.bw.leyou.pojo.SpuDetail;
import com.leyou.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {
    @GetMapping("goods/spu/detail/{id}")
    SpuDetail queryDetailById(@PathVariable("id") Long spuid);

    @GetMapping("goods/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);

    @GetMapping("goods/spu/page")
     PageResult<Goods> queryGoods(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rowsPerPage",defaultValue = "5") Integer rowsPerPage,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "search",required = false) String search
    );
}
