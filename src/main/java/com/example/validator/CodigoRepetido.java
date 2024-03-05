
package com.example.validator;

import jakarta.validation.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CodigoRepetidoValidator.class)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CodigoRepetido {
    public String message() default "{productoCodigoValido.mensajePorDefecto}";
    public Class<?>[] groups() default{};
    public Class<? extends Payload>[] payload() default{};
    
}
