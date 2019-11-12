package org.zyf.cloud.common.exception;

import org.zyf.cloud.common.constant.funcode.ErrorCode;

/**
 * 描述：文件异常类
 *
 * @author yanfengzhang
 * @date 2019-11-11 18:12
 */
public class FileException extends InnerException {
    private static final long serialVersionUID = 5209146001069745084L;

    public FileException(String message, String errorCode) {
        super(message, errorCode);
    }

    public static FileException makeFileFailException(String filePath) {
        return new FileException("FileException try make file:" + filePath + ", but failed!", ErrorCode.FileErrorCode.MAKE_FILE_FAIL);
    }

    public static FileException fileIsNotExistException(String filePath) {
        return new FileException("FileException " + filePath + " is not exist", ErrorCode.FileErrorCode.FILE_NOT_EXIET);
    }

    public static FileException writeFileFailException(String filePath) {
        return new FileException("FileException try write file:" + filePath + ", but failed!", ErrorCode.FileErrorCode.WRITE_FILE_FAIL);
    }

    public static FileException readFileFailException(String filePath) {
        return new FileException("FileException try read file:" + filePath + ", but failed!", ErrorCode.FileErrorCode.READ_FILE_FAIL);
    }

    public static FileException closeFileFailException(String filePath) {
        return new FileException("FileException try close file:" + filePath + ", but failed!", ErrorCode.FileErrorCode.cLOSE_FILE_FAIL);
    }
}
