package com.github.gungnirlaevatain.desensitization.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class DesensitizationPointcut extends AspectJExpressionPointcut {
    @Override
    protected void onSetExpression(String expression) throws IllegalArgumentException {
        super.onSetExpression(expression);
        log.info("the expression [{}] has been set up", expression);
    }
}
