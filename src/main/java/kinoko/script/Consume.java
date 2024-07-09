package kinoko.script;

import kinoko.script.common.Script;
import kinoko.script.common.ScriptHandler;
import kinoko.script.common.ScriptManager;

public final class Consume extends ScriptHandler {
    @Script("consume_2430112")
    public static void consume_2430112(ScriptManager sm) {
        // Miracle Cube Fragment (2430112)
        if (sm.hasItem(2430112, 10)) {
            if (sm.canAddItem(2049400, 1) && sm.removeItem(2430112, 10)) {
                sm.addItem(2049400, 1); // Advanced Potential Scroll
            } else {
                sm.sayNext("Please check if your inventory is full or not.");
            }
        } else if (sm.hasItem(2430112, 5)) {
            if (sm.canAddItem(2049401, 1) && sm.removeItem(2430112, 5)) {
                sm.addItem(2049401, 1); // Potential Scroll
            } else {
                sm.sayNext("Please check if your inventory is full or not.");
            }
        }
    }
}
