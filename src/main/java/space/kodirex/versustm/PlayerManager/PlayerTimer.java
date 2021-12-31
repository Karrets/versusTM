package space.kodirex.versustm.PlayerManager;

import net.minecraft.entity.player.EntityPlayer;
import space.kodirex.versustm.ModConfig;

import java.time.LocalDate;

public class PlayerTimer implements IPlayerTimer {
    private LocalDate lastRefresh = LocalDate.now();
    private double timeSpent = 0; //Stored as a percentage.

    @Override
    public void setLastRefresh(int[] date) {
        lastRefresh = LocalDate.of(date[0], date[1], date[2]);
    }

    @Override
    public void setTimeSpent(double completionPercent) {
        timeSpent = completionPercent;
    }

    public int[] getLastRefresh() {
        return new int[]{ //Given as year, month, day.
                lastRefresh.getYear(),
                lastRefresh.getMonthValue(),
                lastRefresh.getDayOfMonth()
        };
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    @Override
    public double getTimeSpentAsMinutes() {
        return ((1 - timeSpent) * ModConfig.timeLimit) * 60;
    }

    @Override
    public void progress() { //This should be called once a minute for every active player!
        double increment = 1 / (ModConfig.timeLimit * 3600);
        timeSpent += increment;
    }

    @Override
    public void reset() {
        lastRefresh = LocalDate.now();
        timeSpent = 0;
    }

    @Override
    public boolean isPlayable() {
        if(!lastRefresh.equals(LocalDate.now())) { //Check if it is not the same day as the latest refresh.
            //If this is the case, we should allow the user, and reset the clock too!
            reset();
            return true;
        }

        return (timeSpent <= 1);
    }

    public static IPlayerTimer get(EntityPlayer player) {
        return player.getCapability(TimerProvider.TIMER_CAPABILITY, null);
    }

    public static void copy(IPlayerTimer oldTimer, EntityPlayer player) {
        IPlayerTimer newTimer = get(player);

        newTimer.setTimeSpent(oldTimer.getTimeSpent());
        newTimer.setLastRefresh(oldTimer.getLastRefresh());
    }
}