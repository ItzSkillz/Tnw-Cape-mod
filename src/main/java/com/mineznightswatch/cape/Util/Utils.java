package com.mineznightswatch.cape.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Utils
{
    protected Minecraft mc;
    private boolean checked = false;
    public ArrayList<String> PL = null;
    public static boolean EntityIsMe = false;

    public boolean isMP()
    {
        boolean MpOrSp;
        MpOrSp = !this.mc.isSingleplayer();
        return MpOrSp;
    }

    public void PlayerList(String Name)
    {
        ArrayList<String> playerEntities = null;
        if(!Name.equals(null))
        {
            if (Name.equals(mc.thePlayer.getName()))
            {
                EntityIsMe = true;
                playerEntities.addAll(mc.theWorld.playerEntities);
                playerEntities.clear();

            } else
            {
                EntityIsMe = false;
                playerEntities.add(Name);
            }
        }
        PL = playerEntities;
    }

    public boolean ShouldCheck()
    {
        if (isMP())
        {
            return true;
        }
        else if (!checked)
        {
            checked = true;
            return true;
        }
        else
        {
            return false;
        }
    }

}
