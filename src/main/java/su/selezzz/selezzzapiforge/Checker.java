package su.selezzz.selezzzapiforge;

import net.minecraft.world.level.LevelAccessor;
import su.selezzz.selezzzapiforge.network.StorytoolsModVariables;

public class Checker {
    public boolean getActivated(LevelAccessor world) {
        return StorytoolsModVariables.MapVariables.get(world).isTokenActivated;
    }
}
