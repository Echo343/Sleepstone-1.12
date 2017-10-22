package com.blargsworkshop.sleepstone.utility;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class SimpleTeleporter extends Teleporter {

	private final WorldServer worldServerInstance;
	private double x;
	private double y;
	private double z;
	
	public SimpleTeleporter(WorldServer worldServer, double x, double y, double z) {
		super(worldServer);
		this.worldServerInstance = worldServer;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void placeInPortal(Entity entity, double pX, double pY, double pZ, float rotYaw) {
		// TODO make actual gen chunk
		// Dummy load to maybe gen chunk
		this.worldServerInstance.getBlock((int) this.x, (int) this.y, (int) this.z);
		
		entity.setPosition(x, y, z);
		entity.motionX = 0.0f;
		entity.motionY = 0.0f;
		entity.motionZ = 0.0f;
	}
	
}
