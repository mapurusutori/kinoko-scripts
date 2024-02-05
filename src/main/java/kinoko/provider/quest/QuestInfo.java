package kinoko.provider.quest;

import kinoko.provider.wz.property.WzListProperty;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class QuestInfo {
    private final int questId;
    private final boolean autoStart;
    private final boolean autoComplete;
    private final Set<QuestAction> startActs;
    private final Set<QuestAction> completeActs;
    private final Set<QuestCheck> startChecks;
    private final Set<QuestCheck> completeChecks;

    public QuestInfo(int questId, boolean autoStart, boolean autoComplete, Set<QuestAction> startActs, Set<QuestAction> completeActs,
                     Set<QuestCheck> startChecks, Set<QuestCheck> completeChecks) {
        this.questId = questId;
        this.autoStart = autoStart;
        this.autoComplete = autoComplete;
        this.startActs = startActs;
        this.completeActs = completeActs;
        this.startChecks = startChecks;
        this.completeChecks = completeChecks;
    }

    public int getQuestId() {
        return questId;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }

    public Set<QuestAction> getStartActs() {
        return startActs;
    }

    public Set<QuestAction> getCompleteActs() {
        return completeActs;
    }

    public Set<QuestCheck> getStartChecks() {
        return startChecks;
    }

    public Set<QuestCheck> getCompleteChecks() {
        return completeChecks;
    }

    public boolean isAutoAlert() {
        return autoStart || autoComplete;
    }

    @Override
    public String toString() {
        return "QuestInfo[" +
                "id=" + questId + ", " +
                "autoStart=" + autoStart + ", " +
                "autoComplete=" + autoComplete + ", " +
                "startActs=" + startActs + ", " +
                "completeActs=" + completeActs + ", " +
                "startChecks=" + startChecks + ", " +
                "completeChecks=" + completeChecks + ']';
    }

    public static QuestInfo from(int questId, WzListProperty questInfo, WzListProperty questAct, WzListProperty questCheck) {
        boolean autoStart = false;
        boolean autoComplete = false;
        for (var infoEntry : questInfo.getItems().entrySet()) {
            switch (infoEntry.getKey()) {
                case "autoStart" -> {
                    autoStart = (int) infoEntry.getValue() != 0;
                }
                case "autoComplete" -> {
                    autoComplete = (int) infoEntry.getValue() != 0;
                }
            }
        }
        return new QuestInfo(
                questId,
                autoStart,
                autoComplete,
                Collections.unmodifiableSet(resolveQuestActs(questAct.get("0"))),
                Collections.unmodifiableSet(resolveQuestActs(questAct.get("1"))),
                Collections.unmodifiableSet(resolveQuestChecks(questAct.get("0"))),
                Collections.unmodifiableSet(resolveQuestChecks(questAct.get("1")))
        );
    }

    private static Set<QuestAction> resolveQuestActs(WzListProperty actProps) {
        final Set<QuestAction> questActions = new HashSet<>();
        for (var entry : actProps.getItems().entrySet()) {
            // TODO
        }
        return questActions;
    }

    private static Set<QuestCheck> resolveQuestChecks(WzListProperty checkProps) {
        final Set<QuestCheck> questChecks = new HashSet<>();
        for (var entry : checkProps.getItems().entrySet()) {
            // TODO
        }
        return questChecks;
    }

}
