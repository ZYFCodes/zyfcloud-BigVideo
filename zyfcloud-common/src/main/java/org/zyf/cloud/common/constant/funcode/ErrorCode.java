package org.zyf.cloud.common.constant.funcode;

/**
 * 描述：公用错误码定义
 *
 * @author yanfengzhang
 * @date 2019-11-11 18:20
 */
public class ErrorCode {

    /**
     * 常规错误码
     */
    public static class Common {
        public static String SUCCESS = "0";
        public static String FAILED = "1";
        public static String UNKNOWN_ERROR = "-999";
    }

    /**
     * 文件相关错误码
     */
    public static class FileErrorCode {
        public static String MAKE_FILE_FAIL = "00010001";
        public static String FILE_NOT_EXIET = "00010002";
        public static String WRITE_FILE_FAIL = "00010003";
        public static String READ_FILE_FAIL = "00010004";
        public static String cLOSE_FILE_FAIL = "00010005";
    }
}
