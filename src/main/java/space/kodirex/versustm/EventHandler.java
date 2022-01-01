package space.kodirex.versustm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import space.kodirex.versustm.PlayerManager.IPlayerTimer;
import space.kodirex.versustm.PlayerManager.PlayerTimer;
import space.kodirex.versustm.PlayerManager.TimerProvider;

public class EventHandler {
    private static int uncountedTicks = 0;
    public static final ResourceLocation TIMER_CAPABILITY = new ResourceLocation(VersusTM.MOD_ID, "timer");

    @SubscribeEvent
    public static void tickEvent(TickEvent.ServerTickEvent event) {
        if(VersusTM.SERVER.getPlayerList().getPlayers().size() != 0) {
            //Don't bother counting ticks when no-one is online,
            //The calculations aren't that intense, but over time it adds up.
            if (event.phase == TickEvent.Phase.START) {
                uncountedTicks += 1;

                if (uncountedTicks % 20 == 0) {
                    SecondEvent(event);
                }

                if (uncountedTicks >= 1200) {
                    MinuteEvent(event);
                    uncountedTicks = 0; //Reset tick counter after we reach one minute.
                }
            }
        }
    }

    public static void SecondEvent(TickEvent.ServerTickEvent event) { //Fires every second!
        for (EntityPlayerMP player : VersusTM.SERVER.getPlayerList().getPlayers()) {
            IPlayerTimer timer = PlayerTimer.get(player);

            if (timer != null) {
                double secondsRemaining = timer.getTimeRemainingAsMinutes() * 60;
                if(secondsRemaining <= 30) {
                    String message = "§cWarning, you have ";
                    message += Integer.toString((int)secondsRemaining);
                    message += " seconds left!";
                    player.sendMessage(new TextComponentString(message));
                }

                timer.progress();

                if (!timer.isPlayable()) {
                    player.connection.disconnect(
                            new TextComponentString("You are out of time! Come back tomorrow!"));
                }
            }
        }
    }

    public static void MinuteEvent(TickEvent.ServerTickEvent event) { //Fires every minute!
        for (EntityPlayerMP player : VersusTM.SERVER.getPlayerList().getPlayers()) {
            IPlayerTimer timer = PlayerTimer.get(player);

            if (timer != null) {
                int remaining = (int)timer.getTimeRemainingAsMinutes();
                if(remaining <= 10 & remaining != 0) {
                    String message = "§eWarning, you have ";
                    message += Integer.toString(remaining);
                    message += " minutes left!";
                    player.sendMessage(new TextComponentString(message));
                }
            }
        }
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayerMP) {
            event.addCapability(TIMER_CAPABILITY, new TimerProvider());
        }
    }

    @SubscribeEvent
    public static void playerJoin(PlayerLoggedInEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;

        IPlayerTimer timer = PlayerTimer.get(player);

        if (timer != null) {
            if (!timer.isPlayable()) {
                player.connection.disconnect(
                        new TextComponentString("You are out of time! Come back tomorrow!"));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        IPlayerTimer oldTimer = PlayerTimer.get(event.getOriginal());

        PlayerTimer.copy(oldTimer, player);
    }
}
