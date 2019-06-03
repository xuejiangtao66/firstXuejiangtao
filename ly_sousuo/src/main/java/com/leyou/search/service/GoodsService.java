package com.leyou.search.service;

import com.bw.leyou.pojo.Brand;
import com.bw.leyou.pojo.Category;
import com.bw.leyou.pojo.Sku;
import com.leyou.exception.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.search.feginclient.BrandClient;
import com.leyou.search.feginclient.CategoryClient;
import com.leyou.search.feginclient.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    /**
     * 创建一个索引对象，并返回对象，（最后将索引对象放入到索引库中）
     * @param goods
     * @return
     */
    public Goods searchGoods(com.bw.leyou.pojo.Goods goods){
        Goods good = new Goods();

        //查询品牌
        Brand brand = brandClient.queryBrandById(goods.getBrandId());
        //查询分类
        List<Long> cids = new ArrayList<>();
        cids.add(goods.getCid1());
        cids.add(goods.getCid2());
        cids.add(goods.getCid3());
        List<Category> categories = categoryClient.queryCategoryByIds(cids);

        if(CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_IS_NO_EXITS);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
        //索索字段
        String all = goods.getTitle()+brand.getName()+ StringUtils.join(names," ");


        //根据spuid查询sku价格
        List<Sku> skus = goodsClient.querySkuBySpuId(goods.getId());
        //List<Long> priceList = skus.stream().map(Sku::getPrice).collect(Collectors.toList());

        //sku进行处理,顺便将价格一起处理
        List<Long> priceList = new ArrayList<>();
        List<Map<String,Object>> skuList = new ArrayList<>();
        for (Sku sku: skus) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("image",StringUtils.substringBefore(sku.getImages(),","));
            map.put("price",sku.getPrice());
            skuList.add(map);
            priceList.add(sku.getPrice());
        }

        good.setBrandId(goods.getBrandId());
        good.setCid1(goods.getCid1());
        good.setCid2(goods.getCid2());
        good.setCid3(goods.getCid3());
        good.setCreateTime(goods.getCreateTime());
        good.setId(goods.getId());
        good.setSubTitle(goods.getSubTitle());


        good.setSkus(JsonUtils.serialize(skuList)); //skus的json
        good.setPrice(priceList); //价格的集合
        good.setAll(all); //可搜索的字段 分类，标题，品牌
        good.setSpecs(null); //可搜索的参数规格，key是参数名，value是参数值:es:{“内存”.[4G,6G]}  //TODO:
         return good;
    }
}
