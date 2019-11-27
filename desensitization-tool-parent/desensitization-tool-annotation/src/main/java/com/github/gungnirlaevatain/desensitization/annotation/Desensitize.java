package com.github.gungnirlaevatain.desensitization.annotation;

import java.lang.annotation.*;

/**
 * The class Desensitize.
 * 限于对String类型的字符串进行脱敏
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 14:57:41
 * @since 1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface Desensitize {
    /**
     * Key.
     * 使用的脱敏配置对应的标识符
     *
     * @return the string
     */
    String key() default "";

    /**
     * Ref.
     * 取该名称的字段的值作为初始值进行脱敏
     * 要求同一层对象内
     * 默认选用自身值
     *
     * @return the string
     */
    String ref() default "";
}
