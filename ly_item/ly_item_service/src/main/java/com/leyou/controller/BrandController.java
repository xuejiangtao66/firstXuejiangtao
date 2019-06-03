package com.leyou.controller;

import com.bw.leyou.pojo.Brand;
import com.leyou.service.BrandService;
import com.leyou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> quereyAllBrand(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rowsPerPage",defaultValue = "25")  Integer rowsPerPage,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "descending",defaultValue = "false") Boolean descending,
            @RequestParam(value = "search",required = false) String search

    ){
      /*  page:this.pagination.page,  //当前页
                rows:this.pagination.rowsPerPage, //每页大小
                sortBy:this.pagination.sortBy,//根据什么排序
                desc:this.pagination.descending,//是否升序
                key:this.search, //模糊查询关键字*/

      return ResponseEntity.ok(brandService.queryAllBrand(page,rowsPerPage,sortBy,descending,search));
    }


    /**
     *
     * @param brand
     * @param categories 添加品牌
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> insertBrand(Brand brand,@RequestParam("categories") Long[] categories){
        brandService.insertBrand(brand,categories);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param id 删除品牌
     * @return
     */
    @DeleteMapping("/bid/{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable("id") Long id){
        brandService.deleteBrandById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 修改品牌
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("categories")List<Long> categories){
        brandService.updateaBrand(brand,categories);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改品牌时候，先删除关系表中之前所关联的分类
     */
    @DeleteMapping("cid_bid/{bid}")
    public ResponseEntity<Void> deleteBrandCategoryByBid(@PathVariable("bid") Long bid){
        brandService.deleteBrandCategoryByBid(bid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据cid查询品牌
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> qeryBrandByCid(@PathVariable("cid") Long cid){

        List<Brand> list = brandService.qeryBrandByCid(cid);
        return ResponseEntity.ok(list);
    }

    /**
     * 根据品牌id查询品牌
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){

        return ResponseEntity.ok(brandService.querBrandByBid(id));
    }
}
