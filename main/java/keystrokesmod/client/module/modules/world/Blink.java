package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.Utils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.LinkedList;

public class Blink extends Module {
    public static

    LinkedList<Packet<?>> beforeblink = new LinkedList<>();
    public Blink() {
        super("Blink", ModuleCategory.world);
    }

    @Override
    public void onEnable() {
        beforeblink.clear();
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        if (e.isOutgoing() && mc.thePlayer.ticksExisted > 10) {
            beforeblink.add(e.getPacket());
            e.setCancelled(true);
        }
    }
    public void onDisable()
    {
        while(!beforeblink.isEmpty())
            mc.thePlayer.sendQueue.addToSendQueue(beforeblink.poll());
    }
}
