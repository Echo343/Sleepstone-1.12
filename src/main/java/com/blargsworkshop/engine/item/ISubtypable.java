package com.blargsworkshop.engine.item;

import java.util.Map;

import net.minecraft.util.ResourceLocation;

public interface ISubtypable {
	/**
	 * Returns a map ResourceLocations for each meta item.
	 * The key of the map must be a non-null Integer that is the meta of the item.
	 * The value of the map is the resource location of the meta item.
	 * <br><br>
	 * <b>For example:</b> an item, fuel, with 2 subtypes
	 * <ul><li>0: gasoline</li><li>1: wood</li></ul>
	 * The map should look like {{0, "mymod:gasoline"}, {1, "mymod:wood"}}
	 * <br>
	 * Or if you use a loop, {{0, "mymod:fuel_0"}, {1, "mymod:fuel_1"}}
	 * <br><br>
	 * Note: The json file names will match the resource locations
	 * (ie. 
	 *  "assets/mod/models/item/gasoline.json",
	 *  "assets/mod/models/item/wood.json"
	 *  -or-
	 * "assets/mod/models/item/fuel_0.json",
	 * "assets/mod/models/item/fuel_1.json"
	 * ) 
	 * @return Map where key = meta, and value = ResourceLocation
	 */
	public Map<Integer, ResourceLocation> getResourceLocationMap();
}
