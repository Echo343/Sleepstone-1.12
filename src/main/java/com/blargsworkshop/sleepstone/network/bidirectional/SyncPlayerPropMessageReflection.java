package com.blargsworkshop.sleepstone.network.bidirectional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.network.AbstractMessage;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class SyncPlayerPropMessageReflection extends AbstractMessage<SyncPlayerPropMessageReflection> {

	private String fieldName;
	private Object value;
	private Class<?> valueType;
	
	public SyncPlayerPropMessageReflection() {}
	
	public SyncPlayerPropMessageReflection(EntityPlayer player, String fieldName) {
		this.fieldName = fieldName;
		try {
			Method getMethod = ExtendedPlayer.class.getMethod("get" + fieldName, (Class<?>[]) null);
			value = getMethod.invoke(ExtendedPlayer.get(player), (Object[]) null);
			valueType = getMethod.getReturnType();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process(EntityPlayer player, Side side) {
		try {
			Method setMethod = ExtendedPlayer.class.getMethod("set" + fieldName, new Class<?>[] {valueType});
			setMethod.invoke(ExtendedPlayer.get(player), new Object[] {value});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
