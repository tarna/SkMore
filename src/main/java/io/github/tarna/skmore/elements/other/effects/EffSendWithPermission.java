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
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Send With Permission")
@Description("sends a message to players with a certain permission")
@Examples("send \"hello staff\" to players with permission \"skmore.staff\"")
@Since("1.0.0")
public class EffSendWithPermission extends Effect {

    static {
        Skript.registerEffect(EffSendWithPermission.class, "send %strings% to players with perm[ission] %string%");
    }

    private Expression<String> messages;
    private Expression<String> permission;

    @Override
    protected void execute(Event e) {
        for(String msg : messages.getArray(e)) {
            Bukkit.broadcast(Component.text(msg), permission.getSingle(e));
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        messages = (Expression<String>) exprs[0];
        permission = (Expression<String>) exprs[1];
        return true;
    }
}
