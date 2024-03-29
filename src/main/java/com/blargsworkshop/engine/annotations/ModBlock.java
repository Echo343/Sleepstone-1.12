package com.blargsworkshop.engine.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Put this tag on your blocks in your IModItem class to have them auto registered.
 * Don't forget to set the registry name
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, FIELD })
public @interface ModBlock { }
