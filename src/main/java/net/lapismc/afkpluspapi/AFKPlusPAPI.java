package net.lapismc.afkpluspapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class AFKPlusPAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        //Register the hook
        new PAPIHook(this);
        getLogger().info(getName() + " v." + getDescription().getVersion() + " has been enabled!");
    }

}
