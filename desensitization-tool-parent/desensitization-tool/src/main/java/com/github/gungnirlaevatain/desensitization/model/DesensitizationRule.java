package com.github.gungnirlaevatain.desensitization.model;

import lombok.Data;

/**
 * The class Desensitization rule.
 * 脱敏规则类
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 10:23:56
 * @since 1.0
 */
@Data
public class DesensitizationRule {
    /**
     * The Key.
     * 唯一标识符
     */
    private String key;
    /**
     * The Desc.
     * 描述
     */
    private String desc;
    /**
     * The Use spel.
     * 是则使用spel语法,否则使用正则表达式
     */
    private boolean useSpel;
    /**
     * The Match pattern.
     * 用于匹配分组的正则表达式
     */
    private String matchPattern;
    /**
     * The Target pattern.
     * 正则表达式目标映射的表达式
     */
    private String targetPattern;
    /**
     * The Spel pattern.
     * 基于spel语法的表达式
     */
    private String spelPattern;

}
