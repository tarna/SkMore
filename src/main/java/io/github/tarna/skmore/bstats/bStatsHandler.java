package io.github.tarna.skmore.bstats;

import io.github.tarna.skmore.SkMore;
import org.bstats.bukkit.Metrics;

public class bStatsHandler {

    private static Metrics metrics;

    public bStatsHandler(SkMore instance, int pluginid) {
        metrics = new Metrics(instance, pluginid);
    }

}
