package com.leyou.controller;

import com.bw.leyou.pojo.Goods;
import com.bw.leyou.pojo.Sku;
import com.bw.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import com.leyou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class SpuController {


    @Autowired
    private SpuService spuService;

    /**
     * 分页查询商品列表
     */
    @GetMapping("/spu/page")
   /* params:{
        page:this.pagination.page, //当前页
              rows:this.pagination.rowsPerPage, //每页大小
              sortBy:this.pagination.sortBy,  //排序字段
              desc:this.pagination.descending, //是否降序
              key:this.filter.search, //搜索条件
              saleable:this.filter.saleable ===0 ? true: this.filter.saleable//上下架*/
    public ResponseEntity<PageResult<Goods>> queryGoods(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rowsPerPage",defaultValue = "5") Integer rowsPerPage,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "search",required = false) String search
    ){

        return ResponseEntity.ok( spuService.queryGoods(page,rowsPerPage,saleable,search));
    }

    /**
     * 商品新增
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveGoods(@RequestBody Goods goods){
        spuService.saveGoods(goods);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**'
     *  修改商品的时候，先根据id，查找商品进行回显
     * @param gid
     * @return
     */
    @GetMapping("/spu/{gid}")
    public ResponseEntity<Goods> queryGoodsById(@PathVariable("gid") Long gid){
        Goods goods = spuService.queryGoodsById(gid);

        return ResponseEntity.ok(goods);
    }


    /**
     * 根據spuid查詢详情
     * @param spuid
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> queryDetailById(@PathVariable("id") Long spuid){
        return ResponseEntity.ok(spuService.queryDetailById(spuid));
    };

    /**
     * 根據spuid查詢sku
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>>  querySkuBySpuId(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(spuService.querySkuBySpuId(spuId));
    }
}
