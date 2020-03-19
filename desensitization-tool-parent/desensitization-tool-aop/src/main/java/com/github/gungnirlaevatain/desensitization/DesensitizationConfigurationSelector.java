package com.github.gungnirlaevatain.desensitization;

import com.github.gungnirlaevatain.desensitization.annoation.EnableDesensitization;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

public class DesensitizationConfigurationSelector extends AdviceModeImportSelector<EnableDesensitization> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case ASPECTJ:
                return getAspectJImports();
            case PROXY:
            default: {
                return getProxyImports();
            }
        }
    }

    private String[] getProxyImports() {
        return new String[]{
                AutoProxyRegistrar.class.getName(),
                DesensitizationConfiguration.class.getName()
        };
    }

    private String[] getAspectJImports() {
        throw new UnsupportedOperationException("can not supported aspectJ proxy now");
    }

}
