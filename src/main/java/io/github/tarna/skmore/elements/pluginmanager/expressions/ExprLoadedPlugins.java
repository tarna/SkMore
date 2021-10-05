package io.github.tarna.skmore.elements.pluginmanager.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.io.File;

@Name("Loaded Plugins")
@Description("returns all of the server's loaded plugins")
@Examples("set {_plugins::*} to all loaded plugins")
@Since("1.0.0")
public class ExprLoadedPlugins extends SimpleExpression<Plugin> {

    static {
        Skript.registerExpression(ExprLoadedPlugins.class, Plugin.class, ExpressionType.SIMPLE, "[all [of the]] loaded [server] plugins");
    }

    private final Server server = Bukkit.getServer();
    private final SimpleCommandMap cMap = new SimpleCommandMap(server);
    private final SimplePluginManager manager = new SimplePluginManager(server, cMap);

    @Override
    protected Plugin[] get(Event e) {
        return manager.getPlugins();
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Plugin> getReturnType() {
        return Plugin.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode) {
        Plugin[] pl = (Plugin[]) delta;
        if(mode == ChangeMode.ADD) {
            for(Plugin plugin : pl) {
                if(plugin == null || plugin.isEnabled()) continue;
                File file = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
                try {
                    manager.loadPlugin(file);
                } catch (InvalidPluginException e) {
                    e.printStackTrace();
                }
            }
        }else if(mode == ChangeMode.REMOVE) {
            for (Plugin plugin: pl) {
                if(plugin == null || !plugin.isEnabled()) continue;
                manager.disablePlugin(plugin);
            }
        }else if(mode == ChangeMode.DELETE) {
            manager.clearPlugins();
        }else if(mode == ChangeMode.RESET || mode == ChangeMode.REMOVE_ALL) {
            manager.disablePlugins();
        }else if(mode == ChangeMode.SET) {
            manager.disablePlugins();
            for (Plugin plugin : pl) {
                if(plugin == null) continue;
                File file = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
                try {
                    manager.loadPlugin(file);
                } catch (InvalidPluginException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        return CollectionUtils.array(Plugin.class);
    }
}
