package com.leyou.vo;

import com.leyou.exception.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {

    private int status; //异常状态码
    private String message;  //异常消息
    private Long timestamp; //时间

    public ExceptionResult(ExceptionEnum em){
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
