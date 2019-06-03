package com.leyou.search.feginclient;

import com.bw.leyou.api.CategoryApi;
import com.bw.leyou.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
