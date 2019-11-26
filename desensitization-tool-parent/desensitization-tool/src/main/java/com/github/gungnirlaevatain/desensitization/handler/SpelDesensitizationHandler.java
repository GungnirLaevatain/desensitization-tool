package com.github.gungnirlaevatain.desensitization.handler;

import com.github.gungnirlaevatain.desensitization.model.DesensitizationRule;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * The class Spel desensitization handler.
 * 基于spel语法的脱敏处理者
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 11:03:18
 * @since 1.0
 */
public class SpelDesensitizationHandler implements DesensitizationHandler {

    /**
     * Instances are reusable and thread-safe
     * 因为实例是可重复使用且线程安全的,所以可设为全局变量
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private String spelPattern;

    @Override
    public void init(DesensitizationRule desensitizationRule) {
        spelPattern = desensitizationRule.getSpelPattern();
    }

    @Override
    public String desensitize(String origin) {
        EvaluationContext context = new StandardEvaluationContext(origin);
        return PARSER.parseExpression(spelPattern).getValue(context, String.class);
    }
}
