package com.leyou.search.respository;


import com.leyou.search.feginclient.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.service.GoodsService;
import com.leyou.vo.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRespositoryTest {

    @Autowired
    private GoodsRespository goodsRespository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private GoodsService goodsService;
    //创建索引
    @Test
    public void createIndex(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }

    @Test
    public void loadSpu(){
        //查询spu信息
        int page = 1;
        int rowPage = 100;
        int size = 0;
        do {
            PageResult<com.bw.leyou.pojo.Goods> result = goodsClient.queryGoods(page, rowPage, true, null);
            //构建good对象
            List<com.bw.leyou.pojo.Goods> items = result.getItems();
            if(CollectionUtils.isEmpty(items)){
                break;
            }
            List<Goods> collect = items.stream().map(goodsService::searchGoods).collect(Collectors.toList());
            //将good对象放入到索引库中
            goodsRespository.saveAll(collect);
            page ++;
            size = items.size();
        }while (size == 100);
    }
}
