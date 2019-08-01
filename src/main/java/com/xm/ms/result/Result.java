package com.xm.ms.result;

import lombok.Getter;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-30 - 17:28
 * description:
 */
@Getter
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**  
     * 成功时候调用
     */
    public static <T> Result<T> success(T data) {
        return  new Result<T>(data);
    }

    /**
     * 失败时候调用
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return  new Result<T>(cm);
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }
}

