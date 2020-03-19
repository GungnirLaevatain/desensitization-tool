package com.github.gungnirlaevatain.desensitization.annoation;

import com.github.gungnirlaevatain.desensitization.DesensitizationConfigurationSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * The interface Enable desensitization.
 * 开启脱敏切面处理
 *
 * @author gungnirlaevatain
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DesensitizationConfigurationSelector.class)
public @interface EnableDesensitization {
    /**
     * AOP切面表达式,例如execution( int xx.yy.zz.sum() )
     */
    String pointcutExpression();

    int order() default Ordered.LOWEST_PRECEDENCE;
}
