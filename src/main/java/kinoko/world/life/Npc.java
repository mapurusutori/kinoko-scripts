package kinoko.world.life;

import kinoko.packet.life.NpcPacket;
import kinoko.provider.map.LifeInfo;
import kinoko.provider.npc.NpcInfo;
import kinoko.server.packet.OutPacket;
import kinoko.world.field.ControlledObject;
import kinoko.world.field.Field;
import kinoko.world.user.User;

import java.util.Optional;

public final class Npc extends Life implements ControlledObject {
    private final LifeInfo lifeInfo;
    private final NpcInfo npcInfo;
    private User controller;

    public Npc(Field field, LifeInfo lifeInfo, NpcInfo npcInfo) {
        super(field);
        this.lifeInfo = lifeInfo;
        this.npcInfo = npcInfo;
    }

    public int getTemplateId() {
        return lifeInfo.id();
    }

    public boolean isMove() {
        return npcInfo.move();
    }

    public Optional<String> getScript() {
        if (npcInfo == null || npcInfo.script() == null || npcInfo.script().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(npcInfo.script());
    }

    @Override
    public User getController() {
        return controller;
    }

    @Override
    public void setController(User controller) {
        this.controller = controller;
    }

    @Override
    public OutPacket enterFieldPacket() {
        return NpcPacket.npcEnterField(this);
    }

    @Override
    public OutPacket leaveFieldPacket() {
        return NpcPacket.npcLeaveField(this);
    }

    @Override
    public OutPacket changeControllerPacket(boolean forController) {
        return NpcPacket.npcChangeController(this, forController);
    }

    public void encodeInit(OutPacket outPacket) {
        // CNpc::Init
        outPacket.encodeShort(lifeInfo.x()); // ptPos.x
        outPacket.encodeShort(lifeInfo.y()); // ptPos.y
        outPacket.encodeByte(lifeInfo.f()); // nMoveAction
        outPacket.encodeShort(lifeInfo.fh()); // Foothold
        outPacket.encodeShort(lifeInfo.rx0()); // rgHorz.low
        outPacket.encodeShort(lifeInfo.rx1()); // rgHorz.high
        outPacket.encodeByte(true);
    }
}