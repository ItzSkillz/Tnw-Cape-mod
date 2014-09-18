package com.mineznightswatch.cape;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.net.HttpURLConnection;
import java.net.URL;

public class CustomNameTagMod extends PlayerEvent
{
    Tnw_Cape_Mod TNW = new Tnw_Cape_Mod();
    public CustomNameTagMod(EntityPlayer player)
    {
        super(player);
    }

    @SubscribeEvent
    public void NameFormat(PlayerEvent.NameFormat event)
    {
        if (event.username == TNW.MemberPlayers)
    }
}
