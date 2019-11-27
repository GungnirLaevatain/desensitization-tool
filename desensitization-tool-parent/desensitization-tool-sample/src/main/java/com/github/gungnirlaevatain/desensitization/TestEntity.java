package com.github.gungnirlaevatain.desensitization;

import com.github.gungnirlaevatain.desensitization.annotation.Desensitize;
import com.github.gungnirlaevatain.desensitization.annotation.NestedDesensitize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestEntity {

    private String from;
    @Desensitize(ref = "from", key = "idcard")
    private String to;
    @Desensitize(key = "mobilephone")
    private String phone;
    @NestedDesensitize
    private TestEntity entity;
}
