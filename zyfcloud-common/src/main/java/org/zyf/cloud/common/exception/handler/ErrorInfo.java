package org.zyf.cloud.common.exception.handler;

import lombok.Data;

/**
 * 描述：错误信息类
 *
 * @author yanfengzhang
 * @date 2019-11-11 18:25
 */
@Data
public class ErrorInfo<T> {
    public static final Integer SUCCESS = 200;
    public static final Integer ERROR = 100;

    /**
     * 错误码
     */
    public Integer code;
    public String errorCode;

    /**
     * 错误信息
     */
    public String message;
    public String url;
    public T data;

    public ErrorInfo() {
    }

    public ErrorInfo(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorInfo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
