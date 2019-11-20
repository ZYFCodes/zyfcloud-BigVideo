package org.zyf.cloud.common.exception;

import lombok.Data;

/**
 * 描述：业务操作异常返回对应错误码和描述
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:20
 */
@Data
public class BusinessException extends RuntimeException {
    Integer errorCode;
    String returncode;
    String errormsg;

    public BusinessException() {
    }

    public  BusinessException(int errorCode, String message) {
        super("[" + errorCode + "] " + message);
    }

    public BusinessException(String errorCode, String message) {
        super("[" + errorCode + "] " + message);
    }

    public BusinessException(String message) {
        super(message);
    }
}
