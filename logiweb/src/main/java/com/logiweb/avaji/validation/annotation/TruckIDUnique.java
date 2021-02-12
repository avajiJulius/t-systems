package com.logiweb.avaji.validation.annotation;

import com.logiweb.avaji.validation.EmailUniqueValidator;
import com.logiweb.avaji.validation.TruckIDUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TruckIDUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TruckIDUnique {
    String message() default  "Not unique truck id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
