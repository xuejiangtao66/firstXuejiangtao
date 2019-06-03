package com.leyou.service;

import com.bw.leyou.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.exception.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mapper.*;
import com.leyou.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    public PageResult<Goods> queryGoods(Integer page, Integer rowsPerPage, Boolean saleable, String search) {

        //分页
        PageHelper.startPage(page,rowsPerPage);
        //过滤
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(search)){
            criteria.andLike("title","%"+search+"%");
        }

        //是否上架
        if(saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }
        //排序
        /*example.setOrderByClause("lastUpdatTime DESC");*/
        //查询
        List<Goods> goods = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(goods)){
            throw new LyException(ExceptionEnum.GOODS_IS_NOT_EXTIS);
        }

        //分类和品牌
        getGategoryAndBrand(goods);
        //解析
        PageInfo<Goods> info = new PageInfo<>(goods);

        return new PageResult<>(info.getTotal(),goods);
    }

    private void getGategoryAndBrand(List<Goods> goods) {
        goods.forEach(list->{
            //获取分类名称
            List<String> names = categoryService.queryCategoryCids(Arrays.asList(list.getCid1(), list.getCid2(), list.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            list.setCname(StringUtils.join(names,"/"));
            //获取品牌名称
            String name = brandMapper.selectByPrimaryKey(list.getBrandId()).getName();
            list.setBname(name);
        });
    }

    /**
     * 保存商品
     * @param goods
     */
    @Transactional
    public void saveGoods(Goods goods) {
        List<Stock> stockList = new ArrayList<>();


        goods.setCreateTime(new Date());
        goods.setLastUpdateTime(goods.getCreateTime());
        goods.setValid(false); //是否有效
        goods.setSaleable(true); //是否上下架
        //新增spu
        int row = spuMapper.insert(goods);
        if(row !=1){
            throw  new LyException(ExceptionEnum.INSERT_GOODS_ERROR);
        }
        //新增商品详情
        SpuDetail spuDetail = goods.getSpuDetail();
        spuDetail.setSpuId(goods.getId());
        spuDetailMapper.insert(spuDetail);
        //新增sku
        List<Sku> skus = goods.getSkus();
        for (Sku sku :skus){
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(goods.getId());
            int insert = skuMapper.insert(sku);
            if(insert != 1){
                throw  new LyException(ExceptionEnum.INSERT_GOODS_ERROR);
            }
            //新增库存
            Stock stock = new Stock();
            stock.setStock(sku.getStock());
            stock.setSkuId(sku.getId());
            stockList.add(stock);
        }

        //批量新增库存
        stockMapper.insertList(stockList);
    }

    /**
     * 修改商品时候，先回显商品
     * spu sku detail bname, cname
     * @param gid
     * @return
     */
    public Goods queryGoodsById(Long gid) {
        Goods goods = spuMapper.selectByPrimaryKey(gid);
        if(goods == null){
            throw  new LyException(ExceptionEnum.GOODS_IS_NOT_EXTIS);
        }


        /*  Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
        goods.setBname(brand.getName());
*/

        //cname 根据商品的cid查询多级分类  //bname根据商品表查询品牌
        List<Goods>  list = new ArrayList<>();
        list.add(goods);
        getGategoryAndBrand(list);

        /* sku*/
        //根据spuid查找sku
        List<Sku> skuList =  querySkuBySpuId(goods.getId());
        goods.setSkus(skuList);
       /* detail*/
        SpuDetail detail = spuDetailMapper.selectByPrimaryKey(goods.getId());
        goods.setSpuDetail(detail);
        goods.getBname();
        goods.getCname();
        return goods;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
      return skuMapper.select(sku);
    }

    public SpuDetail queryDetailById(Long spuid) {

        return  spuDetailMapper.selectByPrimaryKey(spuid);
    }
}
