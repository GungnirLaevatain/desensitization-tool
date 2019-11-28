package com.github.gungnirlaevatain.desensitization;

import com.github.gungnirlaevatain.desensitization.annotation.Desensitize;
import com.github.gungnirlaevatain.desensitization.annotation.NestedDesensitize;
import com.github.gungnirlaevatain.desensitization.handler.CommonDesensitizationHandler;
import com.github.gungnirlaevatain.desensitization.handler.DesensitizationHandler;
import com.github.gungnirlaevatain.desensitization.handler.RegularDesensitizationHandler;
import com.github.gungnirlaevatain.desensitization.handler.SpelDesensitizationHandler;
import com.github.gungnirlaevatain.desensitization.model.DesensitizationField;
import com.github.gungnirlaevatain.desensitization.model.DesensitizationRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class Desensitization tool.
 * 用于脱敏的工具类
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 10:36:10
 * @since 1.0
 */
@Slf4j
public class DesensitizationTool {
    private static volatile char replaceChar;
    private static volatile String replaceString;

    private static Map<String, DesensitizationHandler> handlers = new ConcurrentHashMap<>(16);
    private static Map<Class<?>, Map<String, DesensitizationField>> fieldCache = new ConcurrentHashMap<>(64);

    /**
     * Reinitialize replace string.
     * 初始化缓存的脱敏用的字符串
     *
     * @param replaceChar the replace char
     *                    脱敏字符
     * @param length      the length
     *                    缓存的长度
     * @author gungnirlaevatain
     */
    public static void initializeReplaceString(char replaceChar, int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(replaceChar);
        }
        DesensitizationTool.replaceChar = replaceChar;
        DesensitizationTool.replaceString = sb.toString();
    }

    /**
     * Register desensitization rule.
     * 注册脱敏规则
     *
     * @param rule the rule
     *             脱敏规则
     * @author gungnirlaevatain
     */
    public static void registerDesensitizationRule(DesensitizationRule rule) {
        if (StringUtils.isEmpty(rule.getKey())) {
            log.error("key is required for rule that spelPattern is {}, matchPattern is {}",
                    rule.getSpelPattern(), rule.getMatchPattern());
            return;
        }
        DesensitizationHandler handler;
        if (!StringUtils.isEmpty(rule.getPattern())) {
            handler = new CommonDesensitizationHandler();
        } else if (rule.isUseSpel()) {
            handler = new SpelDesensitizationHandler();
        } else {
            handler = new RegularDesensitizationHandler();
        }
        handler.init(rule);
        handlers.put(rule.getKey(), handler);
    }

    /**
     * Desensitize.
     * 对输入的字符串进行脱敏
     *
     * @param key    the key
     *               采用的规则
     * @param origin the origin
     *               源字符串
     * @return the string
     * 脱敏后的字符串
     * @author gungnirlaevatain
     */
    public static String desensitize(String key, String origin) {
        if (StringUtils.isEmpty(origin)) {
            return origin;
        }
        DesensitizationHandler handler = handlers.get(key);
        if (handler == null) {
            log.error("can not found desensitization handler from handlers, key is {}", key);
            return origin;
        } else {
            return handler.desensitize(origin);
        }
    }

    /**
     * Desensitize.
     * 根据注解对对象进行脱敏处理
     *
     * @param origin the origin
     *               源对象
     * @author gungnirlaevatain
     */
    public static void desensitize(Object origin) {
        if (origin == null) {
            return;
        }
        Class<?> cls = origin.getClass();
        Map<String, DesensitizationField> fieldMap = getFieldFromLocalCache(cls);
        fieldMap.forEach((name, field) -> {
            try {
                if (field.getNestedDesensitize() != null) {
                    Object value = field.getField().get(origin);
                    desensitize(value);
                } else if (field.getDesensitize() != null) {
                    Desensitize desensitize = field.getDesensitize();
                    Object source = null;
                    if (StringUtils.isEmpty(desensitize.ref())) {
                        source = field.getField().get(origin);
                    } else {
                        DesensitizationField refField = fieldMap.get(desensitize.ref());
                        if (refField != null) {
                            source = refField.getField().get(origin);
                        }
                    }
                    String result = desensitize(desensitize.key(), (String) source);
                    field.getField().set(origin, result);
                }
            } catch (IllegalAccessException e) {
                log.error("can not desensitize this field named[{}] for class named[{}], because ",
                        field.getField().getName(), cls.getName(), e);
            }
        });

    }

    private static Map<String, DesensitizationField> getFieldFromLocalCache(Class<?> cls) {
        return fieldCache.computeIfAbsent(cls, key -> createCache(cls));
    }

    private static Map<String, DesensitizationField> createCache(Class<?> cls) {
        Map<String, Field> allFieldMap = new HashMap<>(16);
        while (cls != null) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                allFieldMap.put(field.getName(), field);
            }
            cls = cls.getSuperclass();
        }
        Map<String, DesensitizationField> fieldCacheMap = new HashMap<>(16);
        allFieldMap.forEach((name, field) -> {
            if (field.getType() == String.class) {
                Desensitize desensitize = field.getAnnotation(Desensitize.class);
                if (desensitize != null && !StringUtils.isEmpty(desensitize.key())) {
                    fieldCacheMap.put(name, new DesensitizationField(field, desensitize, null));
                    if (!StringUtils.isEmpty(desensitize.ref()) && !fieldCacheMap.containsKey(desensitize.ref())) {
                        Field refField = allFieldMap.get(desensitize.ref());
                        if (refField != null) {
                            fieldCacheMap.put(desensitize.ref(), new DesensitizationField(refField, null, null));
                        }
                    }
                }
            } else {
                NestedDesensitize nestedDesensitize = field.getAnnotation(NestedDesensitize.class);
                if (nestedDesensitize != null) {
                    fieldCacheMap.put(name, new DesensitizationField(field, null, nestedDesensitize));
                }
            }
        });
        return fieldCacheMap;
    }

    /**
     * Assemble replace string.
     * 组装指定长度的由脱敏字符组成的字符串
     *
     * @param length the length
     *               截取的长度
     * @return the string
     * @author gungnirlaevatain
     */
    public static String assembleReplaceString(int length) {
        if (length <= 0) {
            return "";
        }
        if (length < replaceString.length()) {
            // 若长度小于缓存的长度,则直接进行截取,避免重新构建
            return replaceString.substring(0, length);
        } else {
            StringBuilder sb = new StringBuilder(replaceString);
            while (sb.length() < length) {
                sb.append(replaceChar);
            }
            return sb.toString();
        }
    }
}
