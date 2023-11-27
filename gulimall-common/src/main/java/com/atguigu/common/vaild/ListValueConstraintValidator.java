package com.atguigu.common.vaild;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.util.CollectionUtils;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {

    private Set  set=new HashSet<Integer>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
         set = Arrays.stream(values)
                .boxed().
        collect(Collectors.toSet());
    }

    /**
     * 是否符合校验集合
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}
