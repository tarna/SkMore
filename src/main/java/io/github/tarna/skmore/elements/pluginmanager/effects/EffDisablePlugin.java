package io.github.tarna.skmore.elements.pluginmanager.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@Name("Disable Plugin")
@Description("disables a certain plugin")
@Examples("disable plugin \"SkMore\"")
@Since("1.0.0")
public class EffDisablePlugin extends Effect {

    static {
        Skript.registerEffect(EffDisablePlugin.class, "(deactivate|disable) plugin %string%");
    }

    private Expression<String> plugin;

    @Override
    protected void execute(Event e) {
        PluginManager pm = Bukkit.getPluginManager();
        Plugin pl = pm.getPlugin(plugin.getSingle(e));
        if(pl != null) pm.disablePlugin(pl);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        plugin = (Expression<String>) exprs[0];
        return true;
    }
}
