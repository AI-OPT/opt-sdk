package com.ai.opt.sdk.test.paas.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testStringNotEmpty() {
        TestVo bean = new TestVo();
        bean.setCustName("zhangc");
        bean.setEmail("zhangchao");
        bean.setMobilePhone("1380009");
        Set<ConstraintViolation<TestVo>> constraintViolations = validator.validate(bean);
        for (ConstraintViolation<TestVo> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }

}
