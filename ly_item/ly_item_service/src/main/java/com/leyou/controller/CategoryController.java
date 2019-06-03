package com.leyou.controller;

import com.bw.leyou.pojo.Brand;
import com.bw.leyou.pojo.Category;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryByParentId(@RequestParam("pid") Long pid){

        return ResponseEntity.ok(categoryService.queryCategoryByParentId(pid));
    }

    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> querBrandByBid(@PathVariable("bid") Long bid){

        return ResponseEntity.ok(categoryService.queryCategoryByBid(bid));
    }


    /**
     * 根据id查询商品分类
     * @param ids
     * @return
     */
   @GetMapping("/list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){

        return ResponseEntity.ok(categoryService.queryCategoryCids(ids));
    }
}
