package org.zyf.cloud.common.exception;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 描述：内部异常封装
 *
 * @author yanfengzhang
 * @date 2019-11-11 18:13
 */
public class InnerException extends RuntimeException {
    protected int flag = -999;

    public InnerException(int errorCode, String message) {
        super("[" + errorCode + "] " + message);
        this.flag = errorCode;
    }

    public InnerException(String message, String errorCode) {
        super("[" + errorCode + "] " + message);
        this.flag = NumberUtils.toInt(errorCode, -999);
    }

    public InnerException(Exception ex) {
        super("[EXCEPTION:" + (ex == null ? "" : ex.getClass() + "]")
                + (ex == null ? "" : ex.getMessage()));
    }

    public InnerException(Exception ex, String message, String errorCode) {
        super("[" + errorCode + "] " + message + ", caused by <" + (ex == null ? "" : ex.getClass()) + ">"
                + (ex == null ? "" : ex.getMessage()));

        this.flag = NumberUtils.toInt(errorCode, -999);
    }

    public int getErrorCode() {
        return this.flag;
    }
}
