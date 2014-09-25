package com.mineznightswatch.cape.Util;

import net.minecraft.client.Minecraft;

public class Utils
{
    protected Minecraft mc;
    boolean SP;
    boolean MP;

    public boolean isSP()
    {
        SP = this.mc.isSingleplayer();
        return SP;
    }

    public boolean WorldHandler()
    {
        if (SP)
        {

        }
        return MP;
    }

    public void CapeIsFound()
    {

    }
}
