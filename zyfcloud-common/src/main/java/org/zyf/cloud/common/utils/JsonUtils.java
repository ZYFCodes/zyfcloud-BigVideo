package org.zyf.cloud.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 描述：基本的Json操作封装
 *
 * @author yanfengzhang
 * @date 2019-11-22 13:40
 */
public class JsonUtils {

    public static String beanToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     *                        ex:  List<User> list = readJson(json, List.class, User.class);
     */
    public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        return mapper.readValue(jsonStr, javaType);
    }

    public static String getValue(String json, String key) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(json).get(key).asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
    }
}
