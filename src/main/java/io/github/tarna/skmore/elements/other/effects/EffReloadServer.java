package io.github.tarna.skmore.elements.other.effects;

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

@Name("Reload Server")
@Description("reloads the server")
@Examples("reload the server")
@Since("1.0.0")
public class EffReloadServer extends Effect {

    static {
        Skript.registerEffect(EffReloadServer.class, "(rl|reload) [the] server");
    }

    @Override
    protected void execute(Event e) {
        Bukkit.reload();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "reloaded the server with skmore";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
