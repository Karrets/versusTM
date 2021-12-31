package space.kodirex.versustm;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.time.LocalDateTime;

public class Timer {
    private static int uncountedTicks = 0;

    @SubscribeEvent
    public static void tickEvent(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            uncountedTicks += 1;

            if(uncountedTicks >= 1200) {
                uncountedTicks = 0; //Reset tick counter after we reach one minute.
                VersusTM.SERVER.sendMessage(new TextComponentString(LocalDateTime.now().toString()));
            }
        }
    }
}
