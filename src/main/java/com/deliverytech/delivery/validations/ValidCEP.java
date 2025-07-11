package com.deliverytech.delivery.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CEPValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCEP {

	String message() default "CEP deve ter formato válido (00000-000 ou 00000000)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
