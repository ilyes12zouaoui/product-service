package ilyes.de.simpleproductcrud.config.aop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface LogResource {
    String logTypeRequest() default "PRODUCT-SERVICE-REQUEST";
    String logTypeResponse() default  "PRODUCT-SERVICE-RESPONSE";
}