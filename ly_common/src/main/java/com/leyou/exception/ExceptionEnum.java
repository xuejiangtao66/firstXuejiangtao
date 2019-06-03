package com.leyou.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    PRICE_CANNOT_BY_NULL(400,"价格不能为空!"),
    CATEGORY_IS_NO_EXITS(401,"分类不存在!"),
    BRAND_IS_NOT_EXITS(402,"品牌不存在!"),
    INSERT_BRAND_ERRO(501,"品牌添加失败!"),
    UPLOAD_FAIL(502,"文件上传失败"),
    INVALID_FILE_TYPE (503,"文件类型不匹配"),
    DELETE_BRAND_FAIL (504,"删除品牌失败"),
    BRAND_UPDATE_FAIL(505,"修改品牌失败"),
    GOODS_SPEC_NOT_EXITS(505,"商品规格不存在"),
   GOODS_IS_NOT_EXTIS(506,"商品不存在"),
    INSERT_GOODS_ERROR (507,"商品新增失败") ;

    int code;
    String msg;
}
