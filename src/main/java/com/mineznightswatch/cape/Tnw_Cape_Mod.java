package com.mineznightswatch.cape;

import com.mineznightswatch.cape.Util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod(modid = references.MOD_ID, name = references.MOD_NAME, version = references.MOD_VERSION)
public class Tnw_Cape_Mod {

    int CapeTick = 0;
    int CapeSweep = 20;

    WebIO IO = new WebIO();
    Utils Ut = new Utils();

    private HashMap<String, ThreadDownloadImageData> CapeChecked = new HashMap<String, ThreadDownloadImageData>();
    private ArrayList<String> MembersIgnored = new ArrayList<String>();
    private ArrayList<String> MembersChecked = new ArrayList<String>();
    private ArrayList<String> Membersfound = new ArrayList<String>();

    boolean CapeChecking = false;

    @Mod.Instance
    public static Tnw_Cape_Mod instance;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        WebIO.findCapesDirectories();
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void tick(ClientTickEvent event) {
        if (event.phase == Phase.END && Ut.ShouldCheck())
        {
            ApplyCapes();
        }
    }

    @SubscribeEvent
    public void NameFormat(PlayerEvent.NameFormat event)
    {
        if ( Membersfound.contains(event.username))
        {
            event.displayname = "[TNW]" + event.username;
        }
    }

    @SubscribeEvent
    public void EntityJoinWorldEvent(EntityJoinWorldEvent event)
    {
        Ut.PlayerList();
        checkMembers();
    }

    public void ApplyCapes() {
        Minecraft mc = Minecraft.getMinecraft();

        if (references.capeDIRs == null || references.capeDIRs.isEmpty()) return;
        if (CapeChecking) return;

        if (CapeTick >= CapeSweep) {
            CapeTick = 0;

            if (mc == null || mc.theWorld == null || mc.theWorld.playerEntities == null || mc.renderEngine == null) {
                return;
            }

            final List<EntityPlayer> playerEntities = mc.theWorld.playerEntities; //get the players

//            apply found cloaks / find players
            for (EntityPlayer entityplayer : playerEntities) {
                String playerName = entityplayer.getCommandSenderName();

                ThreadDownloadImageData usersCape = CapeChecked.get(playerName);

                if (usersCape != null) {

                    AbstractClientPlayer aPlayer = (AbstractClientPlayer) entityplayer;
                    ThreadDownloadImageData currentCape = null;
                    Field downloadImageCape = null;

                    // make cloak resource field accessible and get current cape
                    try {
                        downloadImageCape = AbstractClientPlayer.class.getDeclaredField("field_110315_c");

                        if (!downloadImageCape.isAccessible()) downloadImageCape.setAccessible(true);

                        currentCape = (ThreadDownloadImageData) downloadImageCape.get(aPlayer);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // check if needs update
                    if (downloadImageCape != null && usersCape != currentCape) {

                        LogHelper.info("[TNW] Applying (new) cape for: " + playerName);

                        // set as users cloak resource
                        try {
                            downloadImageCape.set(aPlayer, usersCape);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // run cloak find in another thread
            CapeChecking = true;
            Thread checkThread = new Thread() {
                public void run() {
                    checkCloakURLs(Ut.PL);
                    CapeChecking = false;
                }
            };
            checkThread.setPriority(Thread.MIN_PRIORITY);
            checkThread.start();

        } else {
            CapeTick++;
        }
    }

    public String removeColorFromString(String string) {
        string = string.replaceAll("\\xa4\\w", "");
        string = string.replaceAll("\\xa7\\w", "");

        return string;
    }

    protected void checkCloakURLs(List<String> playerNames)
    {
        WebIO Web = new WebIO();
        for (String playerName : playerNames) {
            String found = null;
            if (MembersIgnored.contains(playerName) || MembersChecked.contains(playerName)) break;

            LogHelper.info("[TNW] Found new player: " + playerName);

            for (String capeURLcheck : references.capeDIRs) {

                found = Web.GetCapesUrl(playerName, capeURLcheck);
                if (found!= null) break;
            }

            if (found == null) {
                MembersIgnored.add(playerName);
                LogHelper.warn("[TNW] Could not find any cloak, ignoring ...");

            } else {
                AbstractClientPlayer aPlayer = (AbstractClientPlayer) Minecraft.getMinecraft().theWorld.getPlayerEntityByName(playerName);

                // get cloak
                ResourceLocation resourcePackCloak = AbstractClientPlayer.getLocationCape(aPlayer.getCommandSenderName());

                TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
                ThreadDownloadImageData object = new ThreadDownloadImageData(found, null, null);
                texturemanager.loadTexture(resourcePackCloak, (ITextureObject) object);

                CapeChecked.put(playerName, object);
                LogHelper.info("[TNW] Found cloak: " + found);
            }
        }
    }
    protected void checkMembers()
    {
        for (String playerName : Ut.PL) {

            if (MembersIgnored.contains(playerName) || MembersChecked.contains(playerName) || Membersfound.contains(playerName) || MembersChecked.contains(playerName)) break;
            if(IO.CheckIfUser(playerName))
            {
                Membersfound.add(playerName);
                MembersChecked.add(playerName);
            }
        }
    }
}
