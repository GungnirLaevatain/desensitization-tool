package com.github.gungnirlaevatain.desensitization;

import com.github.gungnirlaevatain.desensitization.annoation.EnableDesensitization;
import com.github.gungnirlaevatain.desensitization.pointcut.DesensitizationAdvice;
import com.github.gungnirlaevatain.desensitization.pointcut.DesensitizationPointcut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

@Slf4j
public class DesensitizationConfiguration implements ImportAware {

    private Map<String, Object> values;

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        values = annotationMetadata
                .getAnnotationAttributes(EnableDesensitization.class.getName(), false);
        if (values == null) {
            throw new IllegalArgumentException("@EnableDesensitize is not present on importing class " + annotationMetadata.getClassName());
        }
    }

    @Bean
    public DesensitizationPointcut pointcut() {
        String expression = (String) values.get("pointcutExpression");
        DesensitizationPointcut pointcut = new DesensitizationPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PointcutAdvisor desensitizationPointcutAdvisor(DesensitizationPointcut pointcut) {
        int order = (int) values.getOrDefault("order", Ordered.LOWEST_PRECEDENCE);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(new DesensitizationAdvice());
        advisor.setOrder(order);
        return advisor;
    }
}
