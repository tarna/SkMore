package io.github.tarna.skmore.elements.pluginmanager.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.Checker;
import org.bukkit.event.Event;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

@Name("Plugin Enable")
@Description("Triggers when a plugin enables")
@Examples("on plugin enable:")
@Since("1.0.0")
public class EvtPluginEnable extends SkriptEvent {

    static {
        Skript.registerEvent("On Plugin Enable", EvtPluginEnable.class, PluginEnableEvent.class, "[on] [plugin] enable [of %plugin%]");
        EventValues.registerEventValue(PluginEnableEvent.class, Plugin.class, new Getter<Plugin, PluginEnableEvent>() {
            @Override
            public Plugin get(PluginEnableEvent event) {
                return event.getPlugin();
            }
        }, 0);
    }

    Literal<Plugin> plugins;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        plugins = (Literal<Plugin>) args[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        if(plugins != null) {
            Plugin plugin = ((PluginEnableEvent)e).getPlugin();
            return plugins.check(e, new Checker<Plugin>() {
                @Override
                public boolean check(Plugin data) {
                    return data == plugin;
                }
            });
        }
        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }
}
