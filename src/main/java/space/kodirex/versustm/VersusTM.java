package space.kodirex.versustm;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import space.kodirex.versustm.PlayerManager.GetTime;
import space.kodirex.versustm.PlayerManager.IPlayerTimer;
import space.kodirex.versustm.PlayerManager.PlayerTimer;
import space.kodirex.versustm.PlayerManager.PlayerTimerStorage;

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
    public static boolean serverLive = false;

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static VersusTM INSTANCE;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerTimer.class, new PlayerTimerStorage(), PlayerTimer::new);
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        SERVER = event.getServer();
        serverLive = true;
        
        event.registerServerCommand(new GetTime());
    }
}
