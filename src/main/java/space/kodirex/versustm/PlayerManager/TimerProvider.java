package space.kodirex.versustm.PlayerManager;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TimerProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IPlayerTimer.class)
    public static Capability<IPlayerTimer> TIMER_CAPABILITY = null;
    private final IPlayerTimer instance = TIMER_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == TIMER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == TIMER_CAPABILITY ? TIMER_CAPABILITY.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return TIMER_CAPABILITY.getStorage().writeNBT(TIMER_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        TIMER_CAPABILITY.getStorage().readNBT(TIMER_CAPABILITY, this.instance, null, nbt);
    }
}
