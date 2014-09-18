package com.mineznightswatch.cape;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CustomNameTagMod
{
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void NameFormat(PlayerEvent.NameFormat event)
    {

    }
}
