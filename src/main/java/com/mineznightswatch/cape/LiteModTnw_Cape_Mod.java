package com.mineznightswatch.cape;

@ExposableOptions(strategy = ConfigStrategy.Versioned, filename="TNW_cape_mod.json")
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
