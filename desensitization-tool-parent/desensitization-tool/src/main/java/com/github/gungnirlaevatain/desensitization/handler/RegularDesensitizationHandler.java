package com.github.gungnirlaevatain.desensitization.handler;

import com.github.gungnirlaevatain.desensitization.model.DesensitizationRule;

import java.util.regex.Pattern;

/**
 * The class Regular desensitization handler.
 * 基于正则表达式的脱敏处理者
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 11:03:15
 * @since 1.0
 */
public class RegularDesensitizationHandler implements DesensitizationHandler {
    private Pattern regularPattern;
    private String targetPattern;

    @Override
    public void init(DesensitizationRule desensitizationRule) {
        regularPattern = Pattern.compile(desensitizationRule.getMatchPattern());
        targetPattern = desensitizationRule.getTargetPattern();
    }

    @Override
    public String desensitize(String origin) {
        return regularPattern.matcher(origin).replaceAll(targetPattern);
    }
}
