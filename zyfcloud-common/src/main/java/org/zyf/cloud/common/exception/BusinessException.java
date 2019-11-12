package org.zyf.cloud.common.exception;

import lombok.Data;

/**
 * 描述：业务异常
 *
 * @author yanfengzhang
 * @date 2019-11-11 17:56
 */
@Data
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
