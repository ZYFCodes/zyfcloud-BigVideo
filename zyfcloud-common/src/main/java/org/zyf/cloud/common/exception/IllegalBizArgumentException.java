package org.zyf.cloud.common.exception;

import lombok.Data;

/**
 * 描述：业务参数不合法返回对应错误码和描述
 *
 * @author yanfengzhang
 * @date 2019-11-20 15:03
 */
@Data
public class IllegalBizArgumentException extends RuntimeException {
    String returncode;
    Integer errorCode;
    String errormsg;

    public IllegalBizArgumentException() {
    }

    public IllegalBizArgumentException(int errorCode, String message) {
        super("[" + errorCode + "] " + message);
    }

    public IllegalBizArgumentException(String errorCode, String message) {
        super("[" + errorCode + "] " + message);
    }
    public IllegalBizArgumentException(String message) {

        super(message);
    }
}
