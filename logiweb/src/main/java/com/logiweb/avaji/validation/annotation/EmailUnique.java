package com.logiweb.avaji.validation.annotation;


import com.logiweb.avaji.validation.EmailUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnique {
    String message() default  "Not unique email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
