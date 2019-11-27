package com.github.gungnirlaevatain.desensitization.model;

import com.github.gungnirlaevatain.desensitization.annotation.Desensitize;
import com.github.gungnirlaevatain.desensitization.annotation.NestedDesensitize;
import lombok.Data;

import java.lang.reflect.Field;

@Data
public class DesensitizationField {
    private Field field;
    private Desensitize desensitize;
    private NestedDesensitize nestedDesensitize;

    public DesensitizationField(Field field, Desensitize desensitize, NestedDesensitize nestedDesensitize) {
        field.setAccessible(true);
        this.field = field;
        this.desensitize = desensitize;
        this.nestedDesensitize = nestedDesensitize;
    }
}
