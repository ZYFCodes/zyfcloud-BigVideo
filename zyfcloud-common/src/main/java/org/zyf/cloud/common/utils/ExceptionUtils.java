package org.zyf.cloud.common.utils;

import org.apache.commons.lang3.Validate;
import org.zyf.cloud.common.exception.InnerException;

/**
 * 描述：基本异常操作封装
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:24
 */
public class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {
    public static void throwException(Exception ex) {
        Validate.notNull(ex);
        if ((ex instanceof InnerException)) {
            throw ((InnerException) ex);
        }
        throw new InnerException(ex);
    }

    public static String getInfo(Exception ex) {
        if (ex == null) {
            return "empty Exception";
        }
        return ex.getClass() + "," + ex.getMessage();
    }
}
