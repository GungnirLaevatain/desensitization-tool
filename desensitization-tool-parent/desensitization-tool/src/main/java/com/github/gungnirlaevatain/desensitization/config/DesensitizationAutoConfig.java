package com.github.gungnirlaevatain.desensitization.config;

import com.github.gungnirlaevatain.desensitization.DesensitizationTool;
import com.github.gungnirlaevatain.desensitization.properties.DesensitizationRuleProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * The class Desensitization auto config.
 * 脱敏规则的自动配置类
 *
 * @author gungnirlaevatain
 * @version 2019-11-26 10:11:33
 * @since 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DesensitizationRuleProperty.class)
public class DesensitizationAutoConfig {

    @Autowired
    private DesensitizationRuleProperty desensitizationRuleProperty;

    @PostConstruct
    public void init() {
        // 初始化脱敏工具类
        DesensitizationTool.initializeReplaceString(desensitizationRuleProperty.getReplaceChar(),
                desensitizationRuleProperty.getMaxLength());
        desensitizationRuleProperty.getRules()
                .forEach(DesensitizationTool::registerDesensitizationRule);
    }
}
