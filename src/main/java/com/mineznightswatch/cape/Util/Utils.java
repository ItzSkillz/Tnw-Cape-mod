package com.mineznightswatch.cape.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class Utils
{
    public ArrayList<String> PL = new ArrayList<String>();
    protected Minecraft mc;
    final List<EntityPlayer> playerEntities = mc.theWorld.playerEntities;

    public boolean isMP()
    {
        boolean MpOrSp;
        MpOrSp = !this.mc.isSingleplayer();
        return MpOrSp;
    }

    public void PlayerList()
    {
         //get the players
        PL.clear();
        for(EntityPlayer entityplayer : playerEntities)
        {
            String playername = entityplayer.getCommandSenderName();
            PL.add(playername);
        }
    }

}
