package com.github.gungnirlaevatain.desensitization.handler;

import com.github.gungnirlaevatain.desensitization.model.DesensitizationRule;

/**
 * The class Desensitization handler.
 * 脱敏处理器接口
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 10:58:56
 * @since 1.0
 */
public interface DesensitizationHandler {

    /**
     * Init.
     * 初始化
     *
     * @param desensitizationRule the desensitization rule
     *                            脱敏规则
     * @author gungnirlaevatain
     */
    void init(DesensitizationRule desensitizationRule);

    /**
     * Desensitize.
     * 进行脱敏操作
     *
     * @param origin the origin
     *               原始字符串
     * @return the string
     * 脱敏后的字符串
     * @author gungnirlaevatain
     */
    String desensitize(String origin);
}
