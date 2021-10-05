package io.github.tarna.skmore.elements.worldcreator.conditions;

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

import java.io.File;

@Name("World Exists")
@Description("Checks if a world exists in your server directory")
@Examples("if world \"myworld\" exists:")
@Since("1.0.0")
public class CondWorldExists extends Condition {

    static {
        Skript.registerCondition(CondWorldExists.class, "world %string% (0¦exists|1¦(does not|doesn't) exist)");
    }

    private Expression<String> world;

    @Override
    public boolean check(Event e) {
        return world.check(e, w -> {
            File file = new File(Bukkit.getWorldContainer(), w);
            return file.exists() && file.isDirectory();
        }, isNegated());
    }

    @Override
    public String toString(Event e, boolean d) {
        return String.format("%s%s", this.world.toString(e, d), isNegated() ? " does not exist" : " exists");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        world = (Expression<String>) exprs[0];
        setNegated(parseResult.mark == 1);
        return true;
    }
}
