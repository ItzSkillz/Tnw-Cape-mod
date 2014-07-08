package com.mineznightswatch.cape;

import java.io.File;

import net.minecraft.client.Minecraft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy = ConfigStrategy.Versioned, filename="cape.json")
public class LiteModTnw_Cape_Mod implements Tickable{

	@Override
	public String getName() {
		return "TNW_cape_mod";
	}

	@Override
	public String getVersion() {
		return "1.0.0";
	}
}

//yes i know this doesn't work but i am not able to fix it right now... deal with it.
