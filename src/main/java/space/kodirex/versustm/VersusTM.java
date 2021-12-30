package space.kodirex.versustm;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.Mod;

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
    }
}