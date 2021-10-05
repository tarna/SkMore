package io.github.tarna.skmore.elements.other.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.UUID;

@Name("Player from UUID")
@Description("gets player with a certain uuid")
@Examples("set {_player} to player from uuid \"07400f83-d5de-408f-92a2-aea494fa21eb\"")
@Since("1.0.0")
public class ExprPlayerFromUUID extends SimpleExpression<Player> {

    static {
        Skript.registerExpression(ExprPlayerFromUUID.class, Player.class, ExpressionType.COMBINED, "[the] player from [uuid] %string%");
    }

    private Expression<String> uuid;

    @Override
    protected Player[] get(Event e) {
        String uid = uuid.getSingle(e);
        if(uid != null) {
            UUID u = UUID.fromString(uid);
            if(u != null) {
                return new Player[]{Bukkit.getPlayer(u)};
            }
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return String.format("player from uuid %s is %s", uuid.getSingle(e), Bukkit.getPlayer(UUID.fromString(uuid.getSingle(e))));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        uuid = (Expression<String>) exprs[0];
        return true;
    }
}
