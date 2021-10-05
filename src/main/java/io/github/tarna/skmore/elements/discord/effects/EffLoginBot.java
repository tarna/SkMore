package io.github.tarna.skmore.elements.discord.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.events.bukkit.SkriptStartEvent;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import io.github.tarna.skmore.elements.discord.managers.BotManager;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.event.Event;

@Name("Login Bot")
@Description("logins to a discord bot with token")
@Examples("login to \"TOKEN_HERE\" named \"SkMore\"")
@Since("1.0.0")
public class EffLoginBot extends Effect {

    static {
        Skript.registerEffect(EffLoginBot.class, "login to bot with [token] %string% named %string%");
    }

    private Expression<String> exprName;
    private Expression<String> exprToken;


    @Override
    protected void execute(Event e) {
        String name = exprName.getSingle(e);
        String token = exprToken.getSingle(e);
        if (name == null || token == null) return;
        BotManager.addBot(name, token, JDABuilder.createDefault(token));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "created new discord bot from token " + exprToken.toString(e, debug) + " named " + exprName.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if(!ScriptLoader.isCurrentEvent(SkriptStartEvent.class)) {
            Skript.error("We don't recommend using the login effect in a " + ScriptLoader.getCurrentEventName() + " event. Use 'on load:' instead!", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        exprToken = (Expression<String>) exprs[0];
        exprName = (Expression<String>) exprs[1];
        return true;
    }
}
