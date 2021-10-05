package io.github.tarna.skmore.elements.pluginmanager.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Plugin Loaded")
@Description("returns if a certain plugin is loaded")
@Examples("if plugin \"SkMore\" is loaded:")
@Since("1.0.0")
public class CondPluginLoaded extends Condition {

    static {
        Skript.registerCondition(CondPluginLoaded.class, "plugin %string% (0¦is|1¦(is not|isn't) (enabled|activated|loaded)");
    }

    private Expression<String> plugin;

    @Override
    public boolean check(Event e) {
        return plugin.check(e, p -> {
            if(Bukkit.getPluginManager().getPlugin(p) != null) return false;
            return Bukkit.getPluginManager().getPlugin(p).isEnabled();
        }, isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        plugin = (Expression<String>) exprs[0];
        setNegated(parseResult.mark == 1);
        return true;
    }
}
