package com.github.gungnirlaevatain.desensitization.pointcut;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.MethodClassKey;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DesensitizationAdvice implements MethodInterceptor {

    private Map<MethodClassKey, Boolean> cache = new ConcurrentHashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result == null) {
            return result;
        }
        Method method = invocation.getMethod();
        Class<?> targetClass = invocation.getThis().getClass();
        MethodClassKey methodClassKey = new MethodClassKey(method, targetClass);
        if (cache.computeIfAbsent(methodClassKey, (key) -> shouldProcess(result))) {
            // TODO: 2020/3/19
        }
        return result;
    }

    private boolean shouldProcess(Object result) {
        // TODO: 2020/3/19
        return false;
    }

}
