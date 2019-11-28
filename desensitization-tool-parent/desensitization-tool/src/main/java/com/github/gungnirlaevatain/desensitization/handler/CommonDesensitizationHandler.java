package com.github.gungnirlaevatain.desensitization.handler;

import com.github.gungnirlaevatain.desensitization.DesensitizationTool;
import com.github.gungnirlaevatain.desensitization.model.DesensitizationRule;
import com.github.gungnirlaevatain.desensitization.model.ExpressionSegment;
import com.github.gungnirlaevatain.desensitization.model.SpecialWord;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Common desensitization handler.
 * 基于通用表达式的脱敏处理者
 *
 * @author gungnirlaevatain
 * @version 2019 -11-27 10:27:42
 * @since 1.0
 */
public class CommonDesensitizationHandler implements DesensitizationHandler {

    private DesensitizationHandler desensitizationHandler;

    @Override
    public void init(DesensitizationRule desensitizationRule) {
        String pattern = desensitizationRule.getPattern();
        analyzePattern(pattern);
    }

    @Override
    public String desensitize(String origin) {
        return desensitizationHandler.desensitize(origin);
    }

    private void analyzePattern(String pattern) {
        String[] expressions = pattern.split(";");
        boolean useSpel = false;
        List<ExpressionSegment> expressionSegmentList = new ArrayList<>(expressions.length);
        for (String expression : expressions) {
            ExpressionSegment expressionSegment = ExpressionSegment.analyze(expression);
            expressionSegmentList.add(expressionSegment);
            if (expressionSegment.isHasSpecialWord()) {
                useSpel = true;
            }
        }
        if (useSpel) {
            useSpelDesensitizationHandler(expressionSegmentList);
        } else {
            useRegularDesensitizationHandler(expressionSegmentList);
        }
    }

    private void useRegularDesensitizationHandler(List<ExpressionSegment> expressionSegmentList) {
        StringBuilder match = new StringBuilder();
        StringBuilder target = new StringBuilder();
        for (int i = 0; i < expressionSegmentList.size(); i++) {
            ExpressionSegment expressionSegment = expressionSegmentList.get(i);
            if (expressionSegment.isHasReplaceChar()) {
                // 如果含有ReplaceChar,则代表这是需要被替换成脱敏字符的字符串
                int wordLength = Integer.parseInt(expressionSegment.getExpression());
                target.append(DesensitizationTool.assembleReplaceString(wordLength));
                match.append("(.*)");
            } else {
                // 如果不含有ReplaceChar,则代表这是需要保留的字符串
                match.append("(.{")
                        .append(expressionSegment.getExpression())
                        .append("})");
                target.append("$")
                        .append(i + 1);
            }
        }
        DesensitizationRule rule = new DesensitizationRule()
                .setMatchPattern(match.toString())
                .setTargetPattern(target.toString())
                .setUseSpel(false);
        RegularDesensitizationHandler handler = new RegularDesensitizationHandler();
        handler.init(rule);
        desensitizationHandler = handler;
    }

    private void useSpelDesensitizationHandler(List<ExpressionSegment> expressionSegmentList) {
        StringBuilder length = new StringBuilder("0");
        StringBuilder spel = new StringBuilder();
        for (ExpressionSegment expressionSegment : expressionSegmentList) {
            if (expressionSegment.isHasReplaceChar()) {
                spel.append("+")
                        .append(SpecialWord.ASSEMBLE_REPLACE_STRING.getTargetWord())
                        .append('(')
                        .append(expressionSegment.getExpression())
                        .append(')');
                length.append("+(")
                        .append(expressionSegment.getExpression())
                        .append(')');
            } else {
                spel.append("+")
                        .append("substring(")
                        .append(length)
                        .append(",(")
                        .append(length.append("+(").append(expressionSegment.getExpression()).append(')'))
                        .append("))");
            }
        }
        DesensitizationRule rule = new DesensitizationRule()
                .setSpelPattern(spel.substring(1))
                .setUseSpel(true);
        SpelDesensitizationHandler handler = new SpelDesensitizationHandler();
        handler.init(rule);
        desensitizationHandler = handler;
    }

}
