package org.zyf.cloud.common.exception;

/**
 * 描述：业务异常
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:20
 */
public class BusinessException extends RuntimeException {
    String returncode;
    String errormsg;

    public BusinessException() {
    }

    public BusinessException(int errorCode, String message) {
        super("[" + errorCode + "] " + message);
    }

    public BusinessException(String errorCode, String message) {
        super("[" + errorCode + "] " + message);
    }

    public BusinessException(String message) {
        super(message);
    }
}
