package com.Social.application.DG2.util.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = {LoginCheckAspect.class})
public @interface CheckLogin {
}
