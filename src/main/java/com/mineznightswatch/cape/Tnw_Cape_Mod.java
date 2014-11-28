package com.mineznightswatch.cape;

import com.mineznightswatch.cape.Util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;

@Mod(modid = references.MOD_ID, name = references.MOD_NAME, version = references.MOD_VERSION)
public class Tnw_Cape_Mod {

    Minecraft mc;

    WebIO IO = new WebIO();
    Utils Ut = new Utils();

    private HashMap<String, ThreadDownloadImageData> CapeChecked = new HashMap<String, ThreadDownloadImageData>();
    private ArrayList<String> MembersIgnored = new ArrayList<String>();
    private ArrayList<String> MembersChecked = new ArrayList<String>();
    private ArrayList<String> Membersfound = new ArrayList<String>();


    @Instance
    public static Tnw_Cape_Mod instance;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        WebIO.findCapesDirectories();
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void NameFormat(PlayerEvent.NameFormat event) //TODO: check if this event does what i think i does
    {
        if ( Membersfound.contains(event.username))
        {
            event.displayname = "[TNW]" + event.username;
        }
    }

    @SubscribeEvent
    public void EntityJoinWorldEvent(EntityJoinWorldEvent event) //TODO: check when this even is fired
    {
        Ut.PlayerList(event.entity.getName());
        CheckMember(event.entity.getName());
    }

    public void ApplyCape(String Username)
    {
        if (references.capeDIRs == null || references.capeDIRs.isEmpty()) return;
        if (mc == null || mc.theWorld == null || mc.theWorld.playerEntities == null || mc.renderEngine == null);

        String url = references.MEMBERS_DIR + Username;
        ThreadDownloadImageData object = new ThreadDownloadImageData(null, url, null, null);
        Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/" + Username), object);
    }

    //TODO: look into better logic
    private void CheckMember(String name)
    {
        if (Utils.EntityIsMe)
        {
            for (String Names : Ut.PL)
            {
                if (MembersIgnored.contains(Names) || MembersChecked.contains(Names) || Membersfound.contains(Names) || MembersChecked.contains(Names))
                    ;
                if (IO.CheckIfUser(Names))
                {
                    ApplyCape(Names);

                    Membersfound.add(Names);
                    MembersChecked.add(Names);
                }
            }
        } else
        {
            if (MembersIgnored.contains(name) || MembersChecked.contains(name) || Membersfound.contains(name) || MembersChecked.contains(name));
            if (IO.CheckIfUser(name))
            {
                ApplyCape(name);

                Membersfound.add(name);
                MembersChecked.add(name);
            }
        }
    }
}
