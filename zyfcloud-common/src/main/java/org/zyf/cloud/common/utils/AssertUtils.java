package org.zyf.cloud.common.utils;

import org.springframework.util.StringUtils;
import org.zyf.cloud.common.exception.IllegalBizArgumentException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 描述：用于基本参数的判断和对应返回基本的错误异常信息描述
 *
 * @author yanfengzhang
 * @date 2019-11-22 18:50
 */
public class AssertUtils {
    public static class StringAssert {
        public static boolean isNotEmpty(String string) {
            return !isEmpty(string);
        }

        public static boolean isEmpty(String string) {
            return StringUtils.isEmpty(string);
        }

        public static void isNotEmpty(String string, String message) {
            if (isNotEmpty(string)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isNotEmpty(String string, String errorCode, String message) {
            if (isNotEmpty(string)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isNotEmpty(String string, int errorCode, String message) {
            if (isNotEmpty(string)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isEmpty(String string, String message) {
            if (isEmpty(string)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isEmpty(String string, String errorCode, String message) {
            if (isEmpty(string)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isEmpty(String string, int errorCode, String message) {
            if (isEmpty(string)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }
    }

    public static class ObjectAssert {
        public static boolean isNotNull(Object object) {
            return !Objects.isNull(object);
        }

        public static boolean isNull(Object object) {
            return Objects.isNull(object);
        }

        public static void isNull(Object object, String message) {
            if (isNull(object)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isNull(Object object, String errorCode, String message) {
            if (isNull(object)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isNull(Object object, int errorCode, String message) {
            if (isNull(object)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isNotNull(Object object, String message) {
            if (isNotNull(object)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isNotNull(Object object, String errorCode, String message) {
            if (isNotNull(object)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isNotNull(Object object, int errorCode, String message) {
            if (isNotNull(object)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }
    }

    public static class ListAssert {
        public static boolean isNotEmpty(List list) {
            return list != null && list.size() != 0;
        }

        public static boolean isEmpty(List list) {
            return !isNotEmpty(list);
        }

        public static void isNotEmpty(List list, String message) {
            if (isNotEmpty(list)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isNotEmpty(List list, String errorCode, String message) {
            if (isNotEmpty(list)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isNotEmpty(List list, int errorCode, String message) {
            if (isNotEmpty(list)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isEmpty(List list, String message) {
            if (isEmpty(list)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isEmpty(List list, String errorCode, String message) {
            if (isEmpty(list)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isEmpty(List list, int errorCode, String message) {
            if (isEmpty(list)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }
    }

    public static class MapAssert {
        public static boolean isEmpty(Map map) {
            if (map == null || map.size() == 0) {
                return true;
            }
            return false;
        }

        public static boolean isNotEmpty(Map map) {
            return !isEmpty(map);
        }

        public static void isNotEmpty(Map map, String message) {
            if (isNotEmpty(map)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isNotEmpty(Map map, String errorCode, String message) {
            if (isNotEmpty(map)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isNotEmpty(Map map, int errorCode, String message) {
            if (isNotEmpty(map)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isEmpty(Map map, String message) {
            if (isEmpty(map)) {
                throw new IllegalBizArgumentException(message);
            }
        }

        public static void isEmpty(Map map, String errorCode, String message) {
            if (isEmpty(map)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }

        public static void isEmpty(Map map, int errorCode, String message) {
            if (isEmpty(map)) {
                throw new IllegalBizArgumentException(errorCode, message);
            }
        }
    }

    public static void isTrue(boolean value, String message) {
        if (value) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertState(boolean condition, String message) {
        if (condition) {
            throw new IllegalStateException(message);
        }
    }

    public static void main(String[] args) {

    }
}

