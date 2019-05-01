package com.zhongjinlang.vo;

import lombok.Data;

/**
 * @author: zhongjinlang
 * @date: Created in 2019/4/30 16:49
 * @description:
 */
@Data
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResultVo() {
    }

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
