package com.sea.whale.aop;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chengyunbo@gyyx.cn
 * @since 2023-03-09
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue,Object>{

    private String[] strValues;
    private int[] intValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        strValues = constraintAnnotation.strValues();
        intValues = constraintAnnotation.intValues();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 当不传递 flag 值的时候，直接验证通过
        if (value == null) return true;
        if (value instanceof String) {
            for (String strValue : strValues) {
                if (strValue.equals(value)) {
                    return true;
                }
            }
        } else if (value instanceof Integer) {
            for (int intValue : intValues) {
                if (intValue == (Integer) value) {
                    return true;
                }
            }
        }
        return false;
    }

}
