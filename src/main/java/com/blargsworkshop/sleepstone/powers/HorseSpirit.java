package com.blargsworkshop.sleepstone.powers;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class HorseSpirit {

    public static final IAttribute STEP_HEIGHT = new RangedAttribute(null, "generic.stepHeight", 0.6D, 0.0D, 1.6D).setDescription("Step Height").setShouldWatch(true);

}
