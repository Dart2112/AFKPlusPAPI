package net.lapismc.afkpluspapi;

import net.lapismc.afkplus.AFKPlus;
import net.lapismc.afkplus.api.AFKPlusPlayerAPI;
import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplus.util.core.placeholder.PlaceholderAPIExpansion;
import net.lapismc.afkplus.util.core.utils.prettytime.Duration;
import net.lapismc.afkplus.util.core.utils.prettytime.PrettyTime;
import net.lapismc.afkplus.util.core.utils.prettytime.units.JustNow;
import net.lapismc.afkplus.util.core.utils.prettytime.units.Millisecond;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

public class PAPIHook extends PlaceholderAPIExpansion {

    private final AFKPlusPlayerAPI api;
    private final AFKPlusPAPI plugin;
    private final PrettyTime prettyTime;

    public PAPIHook(AFKPlusPAPI plugin) {
        super(AFKPlus.getInstance());
        this.plugin = plugin;
        api = new AFKPlusPlayerAPI();
        prettyTime = new PrettyTime();
        prettyTime.removeUnit(JustNow.class);
        prettyTime.removeUnit(Millisecond.class);
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if ("Status".equalsIgnoreCase(identifier)) {
            return api.getPlayer(player).isAFK() ? plugin.getConfig().getString("status.true")
                    : plugin.getConfig().getString("status.false");
        } else if ("AFKTime".equalsIgnoreCase(identifier)) {
            AFKPlusPlayer p = api.getPlayer(player);
            Long afkStart = p.getAFKStart();
            if (afkStart == null) {
                return plugin.getConfig().getString("afktime.notafk");
            }
            //Get the list of durations for this time difference, reduce that list to the configured amount
            List<Duration> totalTimeDurations = reduceDurationList(prettyTime.calculatePreciseDuration(new Date(afkStart)));
            //Get pretty time to format the remaining durations without future or past context
            return prettyTime.formatDuration(totalTimeDurations);
        } else if ("TotalTimeAFK".equalsIgnoreCase(identifier)) {
            AFKPlusPlayer p = api.getPlayer(player);
            //Get the total time in milliseconds
            long totalTime = p.getTotalTimeAFK();
            //Get the list of durations for this time difference, reduce that list to the configured amount
            List<Duration> totalTimeDurations = reduceDurationList(new PrettyTime(
                    new Date(0)).calculatePreciseDuration(new Date(totalTime)));
            //Get pretty time to format the remaining durations without future or past context
            return prettyTime.formatDuration(totalTimeDurations);
        } else if ("PlayersAFK".equalsIgnoreCase(identifier)) {
            int AFKPlayerCount = 0;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (api.getPlayer(p.getUniqueId()).isAFK())
                    AFKPlayerCount++;
            }
            if (AFKPlayerCount == 0)
                return plugin.getConfig().getString("PlayersCurrentlyAFK.Zero");
            else
                return String.valueOf(AFKPlayerCount);
        }
        return null;
    }

    private List<Duration> reduceDurationList(List<Duration> durationList) {
        while (durationList.size() > plugin.getConfig().getInt("TotalTimeAFK.numberOfTimeUnits")) {
            Duration smallest = null;
            for (Duration current : durationList) {
                if (smallest == null || smallest.getUnit().getMillisPerUnit() > current.getUnit().getMillisPerUnit()) {
                    smallest = current;
                }
            }
            durationList.remove(smallest);
        }
        return durationList;
    }

}
