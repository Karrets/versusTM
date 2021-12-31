package space.kodirex.versustm.PlayerManager;

import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerTimerStorage implements Capability.IStorage<IPlayerTimer> {
    @Override
    public NBTBase writeNBT(Capability<IPlayerTimer> capability, IPlayerTimer instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setIntArray("dateOfRefresh", instance.getLastRefresh());
        nbt.setDouble("spentTime", instance.getTimeSpent());

        return nbt;
    }

    @Override
    public void readNBT(Capability<IPlayerTimer> capability, IPlayerTimer instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound data = (NBTTagCompound)nbt;

        instance.setLastRefresh(data.getIntArray("dateOfRefresh"));
        instance.setTimeSpent(data.getDouble("spentTime"));
    }
}
