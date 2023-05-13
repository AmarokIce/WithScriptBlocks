package club.someoneice.scriptblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class DataSBapi {
    public World world;
    public EntityPlayer player;
    public ChunkCoordinates pos;

    DataSBapi(World world, EntityPlayer player, ChunkCoordinates pos) {
        this.world = world;
        this.player = player;
        this.pos = pos;
    }
}
