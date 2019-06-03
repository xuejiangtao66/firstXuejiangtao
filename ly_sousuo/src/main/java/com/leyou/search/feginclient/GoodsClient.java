package com.leyou.search.feginclient;

import com.bw.leyou.api.GoodsApi;
import com.bw.leyou.pojo.Sku;
import com.bw.leyou.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
