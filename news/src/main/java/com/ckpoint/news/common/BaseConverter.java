package com.ckpoint.news.common;

import hsim.checkpoint.util.ValidationObjUtil;
import org.springframework.stereotype.Component;

@Component
public class BaseConverter {

    public <T> T converter(Object from, Class<T> to) {
        return ValidationObjUtil.objectDeepCopyWithBlackList(from, to);
    }

    public <T> T converterWithoutClass(Object from, Class<T> to, Class excludeClass) {
        return ValidationObjUtil.objectDeepCopyWithBlackList(from, to, excludeClass);
    }
}
