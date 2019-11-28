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
    @Desensitize(ref = "from", pattern = "@RS;1;@RS;2;(@L-5)@RS")
    private String to2;
    @Desensitize(pattern = "@L/3;(@L-@L*2/3)@RS;@L/3")
    private String to3;
}
