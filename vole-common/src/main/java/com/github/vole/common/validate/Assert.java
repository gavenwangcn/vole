package com.github.vole.common.validate;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 数据校验
 */
public class Assert {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws RuntimeException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws RuntimeException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new RuntimeException(msg.toString());
        }
    }

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RuntimeException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }
}
