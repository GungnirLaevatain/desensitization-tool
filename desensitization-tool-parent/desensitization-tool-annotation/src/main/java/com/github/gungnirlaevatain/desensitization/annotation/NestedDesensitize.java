package com.github.gungnirlaevatain.desensitization.annotation;

import java.lang.annotation.*;

/**
 * The class Desensitize.
 * 对象字段拥有该注解则表示该对象内部字段需要被脱敏
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 14:57:51
 * @since 1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface NestedDesensitize {
}
