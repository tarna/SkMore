package io.github.tarna.skmore.elements.other.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.tarna.skmore.SkMore;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Is Owner")
@Description("checks if a player is an owner")
@Examples("if (\"_Tarna_\" parsed as an offline player) is owner:")
@Since("1.0.0")
public class CondIsOwner extends Condition {

    static {
        Skript.registerCondition(CondIsOwner.class, "%player% (0¦is|1¦(is not|isn't) owner");
    }

    private Expression<Player> player;

    @Override
    public boolean check(Event e) {
        return player.check(e, p -> SkMore.instance.getConfig().getStringList("owners").contains(p.getUniqueId().toString()), isNegated());
    }

    @Override
    public String toString(Event e, boolean d) {
        return String.format("%s%s", this.player.toString(e, d), isNegated() ? " is not an owner" : " is owner");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        setNegated(parseResult.mark == 1);
        return true;
    }
}
