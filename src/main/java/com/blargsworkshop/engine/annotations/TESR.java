/**
 * 
 */
package com.blargsworkshop.engine.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@SideOnly(Side.CLIENT)
public @interface TESR {
	Class<? extends TileEntitySpecialRenderer<?>> value();
}
