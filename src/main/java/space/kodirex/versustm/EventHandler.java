package space.kodirex.versustm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import space.kodirex.versustm.PlayerManager.IPlayerTimer;
import space.kodirex.versustm.PlayerManager.TimerProvider;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;

public class EventHandler {
    private static int uncountedTicks = 0;
    public static final ResourceLocation TIMER_CAPABILITY = new ResourceLocation(VersusTM.MOD_ID, "timer");

    @SubscribeEvent
    public static void tickEvent(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            uncountedTicks += 1;

            if(uncountedTicks >= 1200) {
                for(EntityPlayer player : VersusTM.SERVER.getPlayerList().getPlayers()) {
                    player.sendMessage(new TextComponentString("Hello, a minute has passed!"));
                    IPlayerTimer timer = player.getCapability(TimerProvider.TIMER_CAPABILITY, null);
                    if(timer != null) {
                        timer.progress();

                        if(!timer.isPlayable()) {
                            ((EntityPlayerMP)player).connection.disconnect(new TextComponentString("You are out of time!"));
                        }
                    }
                }

                uncountedTicks = 0; //Reset tick counter after we reach one minute.
            }
        }
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(TIMER_CAPABILITY, new TimerProvider());
        }
    }

    @SubscribeEvent
    public static void playerJoin(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        IPlayerTimer timer = player.getCapability(TimerProvider.TIMER_CAPABILITY, null);

        if(timer != null) {
            if(!timer.isPlayable()) {
                ((EntityPlayerMP)player).connection.disconnect(new TextComponentString("You are out of time!"));
            }
        } else {
            player.sendMessage(new TextComponentString("You do not have the timer capability... Contact an admin / mod developer..."));
        }
    }

    @SubscribeEvent //PlayerEvent.Clone, namespacing is broken sadly...
    public void onPlayerClone(Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        IPlayerTimer timer = player.getCapability(TimerProvider.TIMER_CAPABILITY, null);
        IPlayerTimer oldTimer = event.getOriginal().getCapability(TimerProvider.TIMER_CAPABILITY, null);

        if(timer != null && oldTimer != null) {
            timer.setLastRefresh(oldTimer.getLastRefresh());
            timer.setTimeSpent(oldTimer.getTimeSpent());
        }
    }
}
