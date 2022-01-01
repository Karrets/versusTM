package space.kodirex.versustm.PlayerManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import space.kodirex.versustm.ModConfig;
import space.kodirex.versustm.VersusTM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public double getTimeRemainingAsHours() {
        return ((1 - timeSpent) * ModConfig.timeLimit);
    }

    @Override
    public double getTimeRemainingAsMinutes() { //Returns as minutes...
        return ((1 - timeSpent) * ModConfig.timeLimit) * 60;
    }

    @Override
    public double getTimeRemainingAsSeconds() {
        return ((1 - timeSpent) * ModConfig.timeLimit) * 3600;
    }

    @Override
    public String getTimeRemainingAsHumanReadable() { //Returns as string formatted for people to read
                                                      //EX: "1 hour, 32 minutes, and 8 seconds"
        int totalSecs = (int) getTimeRemainingAsSeconds();

        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public void progress() { //This should be called once a minute for every active player!
        switch(ModConfig.mode) {
//            case "multiplier": //Redundant, but here for clarity!
//                multiplierProgress();
//                break;
            case "all-or-nothing":
                allOrNothingProgress();
                break;
            case "basic":
                basicProgress();
                break;
            default:
                multiplierProgress();
                break;
        }
    }

    private void multiplierProgress() {
        List<EntityPlayerMP> allPlayers = VersusTM.SERVER.getPlayerList().getPlayers();
        List<EntityPlayerMP> onlineEnforcedPlayers = new ArrayList<>();
        String[] enforcedPlayers = ModConfig.getEnforcedUsers();

        for(EntityPlayerMP online : allPlayers) {
            for(String enforcedUN : enforcedPlayers) {
                if(online.getName().equals(enforcedUN)) {
                    onlineEnforcedPlayers.add(online);
                }
            }
        }

        double multiplier = 1 - (((double) onlineEnforcedPlayers.size()) / enforcedPlayers.length);


        double increment = 1 / (ModConfig.timeLimit * 3600);
        timeSpent += (increment * multiplier);
    }

    private void allOrNothingProgress() {
        List<EntityPlayerMP> allPlayers = VersusTM.SERVER.getPlayerList().getPlayers();
        List<EntityPlayerMP> onlineEnforcedPlayers = new ArrayList<>();
        String[] enforcedPlayers = ModConfig.getEnforcedUsers();

        for(EntityPlayerMP online : allPlayers) {
            for(String enforcedUN : enforcedPlayers) {
                if(online.getName().toUpperCase().equals(enforcedUN.toUpperCase())) { //Case in-sensitive operation.
                    //TODO: Impl a method of using uuids instead of player names
                    onlineEnforcedPlayers.add(online);
                }
            }
        }

        if(!(onlineEnforcedPlayers.size() == enforcedPlayers.length)) {
            basicProgress();
        } //Dont progress unless someone if offline!
    }

    private void basicProgress() {
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