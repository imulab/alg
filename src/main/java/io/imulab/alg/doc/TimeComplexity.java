package io.imulab.alg.doc;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.SOURCE)
public @interface TimeComplexity {

    /**
     * @return  the expression of the complexity.
     */
    String value();

    /**
     * @return  the name of the operation.
     */
    String op();

    /**
     * @return  comment.
     */
    String comment() default "";
}
