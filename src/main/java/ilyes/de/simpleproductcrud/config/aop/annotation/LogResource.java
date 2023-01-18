package ilyes.de.simpleproductcrud.config.aop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface LogResource {
    String logType() default "PRODUCT-SERVICE";
    boolean shouldLogHeaders() default false;
    boolean shouldLogQueryParams() default false;
}