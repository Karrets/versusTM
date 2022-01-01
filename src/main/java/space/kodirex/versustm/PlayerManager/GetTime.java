package space.kodirex.versustm.PlayerManager;

import com.mojang.authlib.GameProfile;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static net.minecraft.command.CommandBase.getListOfStringsMatchingLastWord;

public class GetTime implements ICommand {
    @Override
    public String getName() {
        return "timebal";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "View your remaining time! /timebal <username> or /timebal";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("TIMEBAL");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        Entity player = sender.getCommandSenderEntity();

        if(args.length > 0) {

            for(EntityPlayerMP possiblePlayer : server.getPlayerList().getPlayers()) {
                if(possiblePlayer.getName().equals(args[0])) {
                    player = possiblePlayer;
                }
            }
        }

        if(player != null & player instanceof EntityPlayer) {
            IPlayerTimer timer = PlayerTimer.get((EntityPlayer) player);

            if(timer != null) {
                String message = "You have ";
                message += timer.getTimeRemainingAsHumanReadable();
                message += " remaining...";

                player.sendMessage(new TextComponentString(message));
            }
        } else {
            sender.sendMessage(new TextComponentString("Target of command must be a player!"));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length >= 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
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
