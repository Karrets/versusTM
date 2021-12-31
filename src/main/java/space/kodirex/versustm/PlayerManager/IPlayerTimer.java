package space.kodirex.versustm.PlayerManager;

//https://www.planetminecraft.com/blog/forge-tutorial-capability-system/
//This tutorial was very helpful!

import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerTimer {
    void setLastRefresh(int[] date);
    void setTimeSpent(double completionPercent);
    int[] getLastRefresh();
    double getTimeSpent();
    double getTimeSpentAsMinutes();
    void progress();
    void reset();
    boolean isPlayable();
}
