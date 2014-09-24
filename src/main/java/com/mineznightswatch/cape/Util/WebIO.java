package com.mineznightswatch.cape.Util;

import com.mineznightswatch.cape.Tnw_Cape_Mod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WebIO
{
    public static void findCapesDirectories() {
        new Thread() {
            public void run() {
                LogHelper.info("[TNW] Searching for capes directories ...");

                ArrayList<String> _capeDIRs = new ArrayList<String>();
                try {
                    URL dirList = new URL("http://minez-nightswatch.com/capesDirectory.list");
                    BufferedReader in = new BufferedReader(new InputStreamReader(dirList.openStream()));

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) _capeDIRs.add(inputLine);
                    in.close();

                } catch (Exception e) {
                    LogHelper.info("[TNW] External cape directories could not be found. Try again on restart...");
                }

                _capeDIRs.add(0, references.CAPES_DIR);

                LogHelper.info("[TNW] " + _capeDIRs.size() + " directories loaded!");
                references.capeDIRs = _capeDIRs;
            }

        }.start();
    }

    public String GetCapesUrl(String playerName, String capeURLcheck)
    {
        Tnw_Cape_Mod TNW = new Tnw_Cape_Mod();
        String CapesFound = null;
        String CapesUrl = capeURLcheck + TNW.removeColorFromString(playerName) + ".png";
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(CapesUrl).openConnection();
            con.setRequestMethod("HEAD");
            con.setRequestProperty("User-agent", "MineCapes " + references.MOD_VERSION);
            con.setRequestProperty("Java-Version", System.getProperty("java.version"));
            con.setConnectTimeout(2000);
            con.setUseCaches(false);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) CapesFound = CapesUrl;

            con.disconnect();

        } catch (Exception e) {
            CapesFound = null;
            // Expected Failure if no cape
        }
        return CapesFound;
    }

    public String GetTagFound(String playerName)
    {
        Tnw_Cape_Mod TNW = new Tnw_Cape_Mod();
        String TagFound = null;
        String url = references.MEMBERS_DIR + TNW.removeColorFromString(playerName);
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            con.setRequestProperty("User-agent", "MineCapes " + references.MOD_VERSION);
            con.setRequestProperty("Java-Version", System.getProperty("java.version"));
            con.setConnectTimeout(2000);
            con.setUseCaches(false);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) TagFound = playerName;

            con.disconnect();

        } catch (Exception e) {
            // Expected Failure if not a member
            System.out.println("playerName: " + playerName);
            TagFound = null;
        }
        return TagFound;
    }
}
