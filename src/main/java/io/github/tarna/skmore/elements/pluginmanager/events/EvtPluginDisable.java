package io.github.tarna.skmore.elements.pluginmanager.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.Checker;
import org.bukkit.event.Event;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public class EvtPluginDisable extends SkriptEvent {

    static {
        Skript.registerEvent("On Plugin Disable", EvtPluginDisable.class, PluginDisableEvent.class, "[on] [plugin] disable [of %plugin%]");
        EventValues.registerEventValue(PluginDisableEvent.class, Plugin.class, new Getter<Plugin, PluginDisableEvent>() {
            @Override
            public Plugin get(PluginDisableEvent event) {
                return event.getPlugin();
            }
        }, 0);
    }

    Literal<Plugin> plugins;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parser) {
        plugins = (Literal<Plugin>) args[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        if(plugins != null) {
            Plugin plugin = ((PluginDisableEvent)e).getPlugin();
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
    public String toString(@Nullable Event e, boolean debug) {
        return "Plugin disable event " + plugins.toString(e, debug);
    }
}
