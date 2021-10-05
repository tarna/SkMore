package io.github.tarna.skmore.elements.math.expressions;

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
import org.bukkit.event.Event;

@Name("Mode")
@Description("returns the mode of a list of numbers")
@Examples("set {_mode} to mode of 1, 1 ,14, 4, 5, 1, 7, 8, 2, and 8")
@Since("1.0.0")
public class ExprMode extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprMode.class, Number.class, ExpressionType.PROPERTY, "mode of %numbers%");
    }

    private Expression<Number> numbers;

    @Override
    protected Number[] get(Event e) {
        int maxValue=0, maxCount=0;
        Number[] numbersToCheck = numbers.getArray(e);
        for (Number value : numbersToCheck) {
            int count = 0;
            for (Number number : numbersToCheck) {
                if (number.equals(value)) ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = (int) value;
            }
        }

        return new Number[] {maxValue};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        numbers = (Expression<Number>) exprs[0];
        return true;
    }
}
