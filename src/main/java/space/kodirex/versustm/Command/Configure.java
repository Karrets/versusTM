package space.kodirex.versustm.Command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class Configure implements ICommand {
    public Configure() {

    }

    @Override
    public String getName() {
        return "versustm";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Used to configure the VersusTM mod.";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("Hello World!"));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Arrays.asList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand iCommand) {
        return 0;
    }
}
