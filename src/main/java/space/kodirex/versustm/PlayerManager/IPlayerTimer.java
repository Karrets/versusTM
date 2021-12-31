package space.kodirex.versustm.PlayerManager;

//https://www.planetminecraft.com/blog/forge-tutorial-capability-system/
//This tutorial was very helpful!

public interface IPlayerTimer {
    void setLastRefresh(int[] date);
    void setTimeSpent(double completionPercent);
    int[] getLastRefresh();
    double getTimeSpent();
    void progress();
    void reset();
    boolean isPlayable();
}
