package pl.d4nte.scratch;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.d4nte.scratch.command.ScratchCommand;
import pl.d4nte.scratch.listener.ScratchListener;

public class ScratchPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Plugin by D4NTE");

        registerAll();
    }

    void registerAll() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ScratchListener(this), this);

        getCommand("zdrapka").setExecutor(new ScratchCommand(this));
    }
}