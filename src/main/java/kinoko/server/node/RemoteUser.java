package kinoko.server.node;

import kinoko.server.packet.InPacket;
import kinoko.server.packet.OutPacket;
import kinoko.util.Encodable;
import kinoko.world.user.User;

public final class RemoteUser implements Encodable {
    private final int accountId;
    private final int characterId;
    private final String characterName;
    private final int level;
    private final int job;
    private int channelId;
    private int fieldId;
    private int partyId;

    public RemoteUser(int accountId, int characterId, String characterName, int level, int job, int channelId, int fieldId, int partyId) {
        this.fieldId = fieldId;
        this.accountId = accountId;
        this.characterId = characterId;
        this.characterName = characterName;
        this.level = level;
        this.job = job;
        this.channelId = channelId;
        this.fieldId = fieldId;
        this.partyId = partyId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public int getLevel() {
        return level;
    }

    public int getJob() {
        return job;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(accountId);
        outPacket.encodeInt(characterId);
        outPacket.encodeString(characterName);
        outPacket.encodeInt(level);
        outPacket.encodeInt(job);
        outPacket.encodeInt(channelId);
        outPacket.encodeInt(fieldId);
        outPacket.encodeInt(partyId);
    }

    public static RemoteUser decode(InPacket inPacket) {
        final int accountId = inPacket.decodeInt();
        final int characterId = inPacket.decodeInt();
        final String characterName = inPacket.decodeString();
        final int level = inPacket.decodeInt();
        final int job = inPacket.decodeInt();
        final int channelId = inPacket.decodeInt();
        final int fieldId = inPacket.decodeInt();
        final int partyId = inPacket.decodeInt();
        return new RemoteUser(
                accountId,
                characterId,
                characterName,
                level,
                job,
                channelId,
                fieldId,
                partyId
        );
    }

    public static RemoteUser from(User user) {
        return new RemoteUser(
                user.getAccountId(),
                user.getCharacterId(),
                user.getCharacterName(),
                user.getLevel(),
                user.getJob(),
                user.getChannelId(),
                user.getFieldId(),
                user.getPartyId()
        );
    }
}