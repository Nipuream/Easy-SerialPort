package com.nipuream.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define the rules of a packet transmission in the protocol.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Protocol {


    /**
     * filed length.
     */
    int length() default -1;

    /**
     * If the field is multibyte, it may have high and low position.
     */
    int type() default -1;

    /**
     * filed position.
     */
    int position() default -1;

}
