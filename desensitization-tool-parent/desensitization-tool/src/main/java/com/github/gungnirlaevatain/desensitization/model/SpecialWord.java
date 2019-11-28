package com.github.gungnirlaevatain.desensitization.model;

import lombok.Getter;

/**
 * The class Special word.
 * 保留字
 *
 * @author gungnirlaevatain
 * @version 2019 -11-26 14:05:59
 * @since 1.0
 */
public enum SpecialWord {

    /**
     * 标志该段应该被脱敏
     */
    REPLACE_CHAR("@RS", ""),
    /**
     * 获取字符串长度
     */
    STRING_LENGTH("@L", "length()"),
    /**
     * 组装替换用的脱敏字符串
     */
    ASSEMBLE_REPLACE_STRING("@ARS", "T(com.github.gungnirlaevatain.desensitization.DesensitizationTool).assembleReplaceString");

    @Getter
    private String word;
    @Getter
    private String targetWord;

    SpecialWord(String word, String targetWord) {
        this.word = word;
        this.targetWord = targetWord;
    }

    public static String replaceAllSpecialWord(String source) {
        for (SpecialWord value : SpecialWord.values()) {
            source = source.replace(value.getWord(), value.getTargetWord());
        }
        return source;
    }

    public static boolean hasReplaceChar(String source) {
        return source != null && source.contains(REPLACE_CHAR.word);
    }

    public static boolean hasSpecialWordExcludeReplaceChar(String source) {
        for (SpecialWord specialWord : SpecialWord.values()) {
            if (specialWord == REPLACE_CHAR) {
                continue;
            }
            if (source.contains(specialWord.word)) {
                return true;
            }
        }
        return false;
    }

    public static String replaceReplaceChar(String source) {
        return source.replace(REPLACE_CHAR.word, REPLACE_CHAR.targetWord);
    }

    public static String replaceSpecialWordExcludeReplaceChar(String source) {
        for (SpecialWord specialWord : SpecialWord.values()) {
            if (specialWord == REPLACE_CHAR) {
                continue;
            }
            source = source.replace(specialWord.word, specialWord.targetWord);
        }
        return source;
    }
}
