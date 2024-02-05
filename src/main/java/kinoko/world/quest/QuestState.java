package kinoko.world.quest;

public enum QuestState {
    NONE(0),
    PERFORM(1),
    COMPLETE(2),
    PARTYQUEST(3),
    NO(4);

    private final int value;

    QuestState(int value) {
        this.value = value;
    }

    public final int getValue() {
        return value;
    }
}