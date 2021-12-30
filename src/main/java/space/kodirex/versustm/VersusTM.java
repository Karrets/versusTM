package space.kodirex.versustm;

import akka.actor.Cell;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import space.kodirex.versustm.Command.Configure;

@Mod(
        modid = VersusTM.MOD_ID,
        name = VersusTM.MOD_NAME,
        version = VersusTM.VERSION,
        serverSideOnly = true, //Don't load this mod if you are running as a client.
        acceptableRemoteVersions = "*" //Allow clients that connect to not have the mod.
)
public class VersusTM {

    public static final String MOD_ID = "versustm";
    public static final String MOD_NAME = "VersusTM";
    public static final String VERSION = "indev";

    public static MinecraftServer SERVER;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static VersusTM INSTANCE;

    @Mod.EventHandler
    public void init(FMLServerStartingEvent event) {
        SERVER = event.getServer();

        MinecraftForge.EVENT_BUS.register(Timer.class);
        event.registerServerCommand(new Configure());
    }
}
