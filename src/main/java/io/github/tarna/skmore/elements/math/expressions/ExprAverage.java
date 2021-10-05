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

@Name("Average")
@Description("returns average of a list of numbers")
@Examples("set {_avg} to average of 1, 5, 2, 6, 9, 2, and 6")
@Since("1.0.0")
public class ExprAverage extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprAverage.class, Number.class, ExpressionType.PROPERTY, "(mean|average|avg) of %numbers%");
    }

    private Expression<Number> numbers;

    @Override
    protected Number[] get(Event e) {
        Number[] numbersToCheck = numbers.getArray(e);
        Number result;
        double sum = 0D;
        for (Number n : numbersToCheck) {
            sum += n.doubleValue();
        }
        result = sum / numbersToCheck.length;
        return new Number[]{result};
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
