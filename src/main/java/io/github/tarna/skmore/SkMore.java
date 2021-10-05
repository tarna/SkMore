package io.github.tarna.skmore;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import io.github.tarna.skmore.bstats.bStatsHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class SkMore extends JavaPlugin {

    public static SkMore instance;
    public static SkriptAddon addon;
    private static Boolean loaded = false;
    public static bStatsHandler bStats;

    private Set<String> hookedPlugins = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);


        registerPluginSyntaxes();


        getLogger().info(hookedPlugins.isEmpty() ? "No Hooked Plugins Found" : "Hooked Plugins: " + hookedPlugins);

        instance = this;
        loaded = true;
        bStats = new bStatsHandler(this, 12421);
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("SkMore fully loaded");
    }

    public void registerSyntaxes() {
        try {
            addon.loadClasses("io.github.tarna.skmore.elements.other");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String type : new String[] {
                "other", "nbt", "scoreboard", "recipe", "bound", "structure",
                "text-component", "pathfinding", "world-creator", "discord",
                "math", "pluginmanager", "permissionmanager", "gui", "particles"}) {
            if(getConfig().getBoolean("elements." + type, true)) {
                try {
                    addon.loadClasses("io.github.tarna.skmore.elements." + type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerPluginSyntaxes() {
        for(String plugin : new String[]{"AdvancedBan", "AdvancedSurvivalGames", "Autorank", "Bedwars", "Bitcoin", "Cannons",
                "ClearLag", "CombatLog", "GriefPrevention", "Lockette", "LockettePro", "LuckPerms", "LWC", "MinePacks", "Parties",
                "PlayerPoints", "PlotSquared", "PrisonMines", "PvPLevels", "Shopkeepers", "ShopChest", "Slimefun",
                "WorldEdit", "WorldGuard"}) {
            if(getConfig().getBoolean("elements.plugins." + plugin, true) && Bukkit.getPluginManager().getPlugin(plugin) != null) {
                try {
                    addon.loadClasses("io.github.tarna.skmore.elements.plugins." + plugin.toLowerCase());
                    hookedPlugins.add(plugin);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
