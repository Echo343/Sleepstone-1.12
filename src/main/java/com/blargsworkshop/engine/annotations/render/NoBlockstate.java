package com.blargsworkshop.engine.annotations.render;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <br><br>
 * Use value = "TESR" to cause the block to not need a blockstate file.
 * This is useful when the block will be drawn from a TESR.
 * <br>
 * <b>Note</b> - if you do this, it is suggested you turn off the block particles
 * as they won't have a texture.  To specify a texture for the block particles
 * you need a blockstate file.
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface NoBlockstate {

}
