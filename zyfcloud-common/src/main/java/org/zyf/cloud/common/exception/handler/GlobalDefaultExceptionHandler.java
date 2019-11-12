package org.zyf.cloud.common.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zyf.cloud.common.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：统一业务异常处理类
 *
 * @author yanfengzhang
 * @date 2019-11-11 18:28
 */
@ControllerAdvice(basePackages = {"org.zyf.cloud.common.exception"})
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public ErrorInfo defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ErrorInfo errorInfo = new ErrorInfo();
        if (e instanceof BusinessException) {
            errorInfo.setErrorCode(((BusinessException) e).getReturncode());
        } else {
            errorInfo.setCode(ErrorInfo.ERROR);
        }
        errorInfo.setMessage(e.getMessage());
        errorInfo.setUrl(req.getRequestURI());
        return errorInfo;
    }
}
