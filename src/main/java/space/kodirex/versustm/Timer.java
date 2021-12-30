package space.kodirex.versustm;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Timer {
    private static int uncountedTicks = 0;

    @SubscribeEvent
    public static void tickEvent(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            uncountedTicks += 1;

            if(uncountedTicks % 20 == 0) {
            }
        }
    }
}
