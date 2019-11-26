package com.github.gungnirlaevatain.desensitization.properties;

import com.github.gungnirlaevatain.desensitization.model.DesensitizationRule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = DesensitizationRuleProperty.PREFIX)
public class DesensitizationRuleProperty {
    public static final String PREFIX = "commons.desensitization";
    /**
     * The Max length.
     * 提前生成的替换字符串长度
     */
    private int maxLength = 1024;
    /**
     * The Replace char.
     * 替换用的字符
     */
    private char replaceChar = '*';
    /**
     * The Rules.
     * 脱敏规则
     */
    private List<DesensitizationRule> rules = new ArrayList<>();

}
