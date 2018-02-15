package com.blargsworkshop.sleepstone.abilities;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class Windwalker {

    public static final IAttribute STEP_HEIGHT = new RangedAttribute(null, "generic.stepHeight", 0.6D, 0.0D, 1.6D).setDescription("Step Height").setShouldWatch(true);
    public static final IAttribute JUMP_MOVEMENT_FACTOR = new RangedAttribute(null, "generic.jumpMovementFactor", 0.02D, 0.02D, 50D).setDescription("Jump Movement Factor").setShouldWatch(true);
    
}
