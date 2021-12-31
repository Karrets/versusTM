package space.kodirex.versustm.PlayerManager;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import space.kodirex.versustm.ModConfig;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class GetTime implements ICommand {
    @Override
    public String getName() {
        return "timebal";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "View your remaining time!";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(sender.getCommandSenderEntity() != null) {
            IPlayerTimer timer = sender.getCommandSenderEntity().getCapability(TimerProvider.TIMER_CAPABILITY, null);

            if(timer != null) {
                String message = "You have ";
                message += Double.toString(timer.getTimeSpentAsMinutes());
                message += " minutes remaining...";

                sender.sendMessage(new TextComponentString(message));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return true;
    }

    @Override
    public int compareTo(ICommand iCommand) {
        return 0;
    }
}
