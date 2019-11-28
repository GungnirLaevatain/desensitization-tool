package com.github.gungnirlaevatain.desensitization.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

@AllArgsConstructor
@Getter
public class ExpressionSegment {
    private String expression;
    private boolean hasSpecialWord;
    private boolean hasReplaceChar;

    public static ExpressionSegment analyze(String source) {
        boolean hasSpecialWord = SpecialWord.hasSpecialWordExcludeReplaceChar(source);
        boolean hasReplaceChar = SpecialWord.hasReplaceChar(source);

        if (hasReplaceChar) {
            source = SpecialWord.replaceReplaceChar(source);
        }
        if (!hasSpecialWord) {
            // 如果不存在特殊字符,则应该是普通的数值表达式
            if (source.length() <= 0) {
                // 如果替换后ReplaceChar后为空串,应该填补默认表达式,即1@RS等同于@RS
                source = "1";
            }
            // 计算数值表达式并获取结果
            ExpressionParser parser = new SpelExpressionParser();
            EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
            Integer i = parser.parseExpression(source).getValue(context, Integer.class);
            if (i == null) {
                throw new IllegalArgumentException(source + " is an illegal length pattern");
            }
            source = i.toString();
        } else {
            // 如果存在特殊字符,则进行替换
            source = SpecialWord.replaceSpecialWordExcludeReplaceChar(source);
        }

        return new ExpressionSegment(source, hasSpecialWord, hasReplaceChar);
    }
}
