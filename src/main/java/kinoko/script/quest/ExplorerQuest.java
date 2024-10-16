package kinoko.script.quest;

import kinoko.provider.reward.Reward;
import kinoko.script.common.Script;
import kinoko.script.common.ScriptHandler;
import kinoko.script.common.ScriptManager;
import kinoko.util.Tuple;
import kinoko.world.field.mob.MobAppearType;
import kinoko.world.item.InventoryType;
import kinoko.world.job.Job;
import kinoko.world.job.JobConstants;
import kinoko.world.quest.QuestRecordType;

import java.util.List;
import java.util.Map;

public final class ExplorerQuest extends ScriptHandler {

    // JOB ADVANCEMENTS ------------------------------------------------------------------------------------------------
    @Script("rogue")
    public static void rogue(ScriptManager sm) { // TODO: Second Job Advancement for other Explorer Thief jobs
        if (sm.getUser().getJob() == 0 && sm.getUser().getLevel() >= 10) {
            sm.sayNext("So, you want to become a Thief? Well, you need to meet some requirements to do so. You need to be at least #bLevel 10#k. Let's see...");
            if (!sm.askYesNo("You look like someone who belongs with us. You just need to be a little more sinister. So, do you want to be a Thief?")) {
                sm.sayOk("Being a Thief isn't for everyone...");
                return;
            }
            if (!sm.addItems(List.of(
                    Tuple.of(2070015, 300), // Beginner Thief's Throwing Stars
                    Tuple.of(1472061, 1), // Beginner Thief's Wrist Guards
                    Tuple.of(1332063, 1) // Beginner Thief's short sword
            ))) {
                sm.sayNext("Please check if your inventory is full or not.");
                return;
            }
            if (sm.hasQuestStarted(2351)) {
                sm.setQRValue(QuestRecordType.DualBladeFirstMission, "1");
            }
            sm.setJob(Job.ROGUE);
            sm.addInventorySlots(InventoryType.EQUIP, 4);
            sm.addInventorySlots(InventoryType.ETC, 4);
            sm.sayNext("Alright, from here on out, you are a part of us! You'll be living the life of a wanderer at first, but just be patient. Soon, you'll be living the high life! Alright, it ain't much, but I'll give you some abilities.");
            sm.sayBoth("I have added slots to the EQUIP and ETC pockets of your Item Inventory. You have also gotten much stronger. Keep training, one day you'll reach the very top. I'll be watching you from afar.");
            sm.sayBoth("I just gave you a little bit of #bSP#k. Open the #bSkill menu#k at the lower right corner. There are skills you can learn by using SP. One warning, though: You can't boost them up all at once. Some skills are only available after you have learned other skills. Please keep that in mind.");
            sm.sayBoth("One more warning, though it seems kind of obvious. Once you have chosen your job, try your best to stay alive. Every death will cost you a certain amount of experience points, and you don't want to lose those, do you?");
            sm.sayBoth("Well, then! This is all I can teach you. Now, train and better yourself. Find me when you feel like you've done all that you can and need something interesting. I'll hook you up then. I'll be waiting for you.");
        } else {
            sm.sayOk("Not many are fit to be Thieves. I must ask you to leave, this isn't the kind of place you need to be in.");
        }
    }

    //@Script("hong-a") // Something about annotations, idk
    //public static void hong-a(ScriptManager sm) {
    ////idk what this is used for yet
    //}

    @Script("thief3")
    public static void thief3(ScriptManager sm) { // TODO: Third Job Advancement for other Explorer Thief jobs & Zakum Dungeon Quest
        final int answer = sm.askMenu("Now...ask me any questions you may have on traveling!!", Map.ofEntries(
                Map.entry(0, "I want to become a Blade Lord."),
                Map.entry(1, "Nevermind.") // "Grant me permission for the Zakum Dungeon Quest"
        ));
        switch (answer) {
            case 0 -> {
                sm.sayNext("Ahh, so you wish to make your 3rd Job Advancement? I have the power to make you stronger but I first must test your persistence. Do you wish to take my test?");
                sm.sayBoth("In order to attack the monsters, you'll need to be equipped with a weapon. When equipped, press #bCtrl#k to use the weapon. With the right timing, you'll be able to easily take down the monsters.");
            }
            case 1 -> {
                sm.sayNext("Safe travels, young adventurer.");
            }
        }
    }

    // NPCS ------------------------------------------------------------------------------------------------------------
    // TODO: Properly handle Power B. Fore's Training Centers, these are only handled for specific quests and / or free entry to everyone
    @Script("enter_warrior")
    public static void enter_warrior(ScriptManager sm) {
        sm.warpInstance(910220000, "start", 102020000, 60 * 30); // Victoria Road : Warrior Training Center
    }

    @Script("enter_magician")
    public static void enter_magician(ScriptManager sm) {
        sm.warpInstance(910120000, "start", 101020000, 60 * 30); // Victoria Road : Magician Training Center
    }

    @Script("enter_archer")
    public static void enter_archer(ScriptManager sm) {
        // Power B. Fore : Entrance to Bowman Training Center (1012119)
        //   Singing Mushroom Forest : Spore Hill (100020000)
        if (sm.hasQuestStarted(22518)) {
            sm.warpInstance(910060100, "start", 100020000, 60 * 30);
            return;
        }
        sm.warp(910060000); // Victoria Road : Bowman Training Center
    }

    @Script("enter_thief")
    public static void enter_thief(ScriptManager sm) {
        if (sm.hasQuestStarted(2377)) {
            sm.warpInstance(910310000, "start", 103010000, 60 * 30); // Victoria Road : Thief Training Center
            return;
        }
        sm.warpInstance(910310000, "start", 103010000, 60 * 30); // Victoria Road : Thief Training Center
    }

    @Script("enter_pirate")
    public static void enter_pirate(ScriptManager sm) {
        sm.warpInstance(912030000, "start", 120020000, 60 * 30); // Victoria Road : Pirate Training Center
    }

    @Script("dual_wallpaper")
    public static void dual_wallpaper(ScriptManager sm) {
        switch (sm.getFieldId()) {
            case 103010100 -> {
                if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;0;1") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;1;1") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;0;1") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("211")) {
                    sm.sayOk("A poster has already been hung up here.");
                    return;
                }
                if (sm.hasQuestStarted(2358)) {
                    if (!sm.askYesNo("There is an empty space here for you to put up the poster. Do you wish to attach the poster here?")) {
                        return;
                    }
                    if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).isBlank()) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "0;0;1");
                        sm.setNpcAction(1043002, "q2358");
                        return;
                    }
                    if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;1;0")) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "0;1;1");
                        sm.setNpcAction(1043002, "q2358");
                        return;
                    }
                    if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;1;0")) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "211");
                        sm.setNpcAction(1043002, "q2358");
                    }
                }
            }
            case 103020000 -> {
                if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;1;0") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;1;1") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;1;0") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("211")) {
                    sm.sayOk("A poster has already been hung up here.");
                } else if (sm.hasQuestStarted(2358)) {
                    if (!sm.askYesNo("There is an empty space here for you to put up the poster. Do you wish to attach the poster here?")) {
                        return;
                    }
                    if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).isBlank()) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "0;1;0");
                        sm.setNpcAction(1043002, "q2358");
                    } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;0;1")) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "0;1;1");
                        sm.setNpcAction(1043002, "q2358");
                    } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;0;0")) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "2;1;0");
                        sm.setNpcAction(1043002, "q2358");
                    } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;0;1")) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "211");
                        sm.setNpcAction(1043002, "q2358");
                    }
                }
            }
        }
    }

    @Script("dual_blueAlcohol")
    public static void dual_blueAlcohol(ScriptManager sm) {
        if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;0;0") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;0;1") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;1;0") || sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("211")) {
            sm.sayOk("The bomb's already been placed.");
        } else if (sm.hasQuestStarted(2358)) {
            if (!sm.askYesNo("There's a blue bottle filled with liquid, do you wish to plant the bomb here?")) {
                return;
            }
            if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).isBlank()) {
                sm.setQRValue(QuestRecordType.DualBladeFifthMission, "2;0;0");
            } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;1;0")) {
                sm.setQRValue(QuestRecordType.DualBladeFifthMission, "2;1;0");
            } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;0;1")) {
                sm.setQRValue(QuestRecordType.DualBladeFifthMission, "2;0;1");
            } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;1;1")) {
                sm.setQRValue(QuestRecordType.DualBladeFifthMission, "211");
            }
        }
    }

    @Script("dual_Diary")
    public static void dual_Diary(ScriptManager sm) {
        if (!sm.getQRValue(QuestRecordType.DualBladeDiary).equals("1")) {
            if (!sm.askYesNo("A dust-covered diary is placed on the shelf. Do you wish to take it?")) {
                sm.setPlayerAsSpeaker(true);
                sm.sayOk("#bI should probably take the diary.");
                return;
            }
            sm.setQRValue(QuestRecordType.DualBladeDiary, "1");
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149);
        } else {
            if (sm.getUser().getField().getMobPool().getCount() > 0) {
                sm.sayOk("You can't take the #p1052126# with the #o9001019#s guarding it.");
            } else if (sm.hasItem(4032617)) {
                sm.sayOk("Time to get out of here before someone catches you!");
                return;
            }
            if (!sm.addItem(4032617, 1)) {
                sm.sayOk("Please check if your inventory is full or not.");
                return;
            }
            sm.setQRValue(QuestRecordType.DualBladeDiary, "2");
            sm.setPlayerAsSpeaker(true);
            sm.sayOk("#b(You've obtained the #p1052126#. Time to get out of here before someone catches you!)");
        }
    }

    // PORTALS ---------------------------------------------------------------------------------------------------------
    @Script("dual_ballRoom")
    public static void dual_ballRoom(ScriptManager sm) {
        if (sm.hasQuestStarted(2363)) {
            sm.playPortalSE();
            sm.warp(910350000, "out00");
        } else {
            sm.message("The Mirror Room is locked.");
        }
    }

    @Script("dual_lv20")
    public static void dual_lv20(ScriptManager sm) { // 103050300
        if (sm.hasQuestStarted(2365)) {
            sm.playPortalSE();
            sm.warpInstance(103050310, "out00", 103050300, 60 * 10);
        } else {
            sm.message("The training center is locked.");
        }
    }

    @Script("dual_lv25")
    public static void dual_lv25(ScriptManager sm) {
        if (sm.hasQuestStarted(2366)) {
            sm.playPortalSE();
            sm.warpInstance(103050340, "out00", 103050300, 60 * 10);
        } else {
            sm.message("The training center is locked.");
        }
    }

    @Script("dual_secret")
    public static void dual_secret(ScriptManager sm) {
        if (sm.hasQuestStarted(2369)) {
            sm.playPortalSE();
            sm.warpInstance(910350100, "out00", 103000000, 60 * 10);
        } else {
            sm.message("A mysterious force prevents you from entering.");
        }
    }

    @Script("dual_lv30")
    public static void dual_lv30(ScriptManager sm) {
        if (sm.hasQuestStarted(2370)) {
            sm.playPortalSE();
            sm.warpInstance(103050370, "out00", 103050300, 60 * 10);
        } else {
            sm.message("The training center is locked.");
        }
    }

    // QUESTS - JOB ADVANCEMENT ----------------------------------------------------------------------------------------
    @Script("q2363e")
    public static void q2363e(ScriptManager sm) {
        if (!sm.askAccept("So, the #t4032616# has chosen you. Very well. I will promote you to Blade Recruit when you're ready.")) {
            sm.sayOk("Gather your thoughts, this isn't a decision you should make without some deep thought.");
            return;
        }
        if (!sm.addItem(1342000, 1)) {
            sm.sayOk("Please check if your inventory is full or not.");
            return;
        }
        sm.forceCompleteQuest(2363);
        sm.removeItem(4032616);
        sm.setJob(Job.BLADE_RECRUIT);
        sm.getUser().getCharacterStat().getSp().addSp(JobConstants.getJobLevel(430), 5); // reference video displays gaining 5 SP at this advancement. Not sure if this is handled in setJob. Doesn't look like it.
        sm.addExp(2000);
        sm.sayOk("You are now a #bBlade Recruit#k. Take pride in that fact.");
    }

    @Script("q2369e")
    public static void q2369e(ScriptManager sm) {
        if (!sm.askAccept("Finally... I have my father's diary. Thank you. I'm starting to trust you even more... And with that, I think your current position fails to suit your seemingly astounding abilities. I think you have all the qualifications to advance to a #bBlade Acolyte#k. I'll proceed with your advancement to Blade Acolyte now.")) {
            sm.sayOk("Gather your thoughts, think it over. It's a big step, but not one you're not ready for.");
            return;
        }
        if (!sm.addItem(1052244, 1)) {
            sm.sayOk("Please check if your inventory is full or not.");
            return;
        }
        sm.forceCompleteQuest(2369);
        sm.removeItem(4032617);
        sm.setJob(Job.BLADE_ACOLYTE);
        sm.addSkill(4311003, 0, 20);
        //sm.addSkill(4311003, 0, 5); // GMS-like. Mastery book required
        sm.getUser().getCharacterStat().getSp().addSp(JobConstants.getJobLevel(431), 1); // reference video displays gaining 5 SP at this advancement. Not sure if this is handled in setJob. Doesn't look like it.
        sm.addExp(5000);
        sm.sayNext("My father's diary... Father would often right in a code that only he and I could understand...");
        sm.sayBoth("Wait! In the last entry... What's this?!");
        sm.sayBoth("This can't be! It's a lie! Jin...! How dare you lay a finger on my father's diary!");
        sm.sayBoth("#b(#p1056000# drops the diary and it falls to the ground.)");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth("#b(You pick up the diary and start reading it.)");
        sm.setPlayerAsSpeaker(false);
        sm.setSpeakerId(1052126);
        sm.sayBoth("#e - Date: XX-XX-XXXX - \r\n\r\nTeacher has passed away. Three days ago, teacher left for the #m105090900# at the request of Tristan. #p1056000# seemed worried so I decided to go looking for him. When I arrived at the entrance of #m105090900#, I heard a shriek that sent chills throughout my entire body.");
        sm.sayBoth("#eWhen I jumped into the darkness of the #m105090900#, I came face to face with a red-eyed monster spewing the most evil and vile energy I've ever felt. Teacher was nowhere to be seen. The monster started attacking. After a fierce battle, I finally succeeded in slaying it. However, the fallen monster soon turned into... teacher.");
        sm.sayBoth("#eI attempted to help teacher, but he passed away in my arms. Before he passed, he whispered:\r\nMy soul was trapped within the Balrog. You freed me... Now, take care of #m103000000# and #p1056000#... And... Please don't tell a soul about this, I can't forgive myself for allowing a demon to overtake my soul.");
        sm.sayBoth("#eAs he wished, I will never reveal what happened. His secret, along with this diary, will forever be sealed and kept hidden.\r\n\r\n -- Jin");
    }

    @Script("q2374e")
    public static void q2374e(ScriptManager sm) {
        sm.sayNext("I've been waiting for you. Do you have #p2020011#'s answer? PLease give me his letter.");
        sm.sayBoth("We have finally received #p2020011#'s official recognition. This is an important moment for us. It's also time that you experienced a change.");
        sm.sayBoth("Now that we have #p2020011#'s recognition, you can make another job advancement by going to see him when you reach Level 70. Finally, a new future has been opened for the Dual Blade.");
        if (!sm.addItem(1132021, 1)) {
            sm.sayOk("Please check if your inventory is full or not.");
            return;
        }
        sm.forceCompleteQuest(2374);
        sm.removeItem(4032619);
        sm.setJob(Job.BLADE_SPECIALIST);
        sm.addSkill(4321000, 0, 20);
        //sm.addSkill(4321000, 0, 5); // GMS-like. Mastery book required
        sm.addExp(25000);
    }

    // REACTORS --------------------------------------------------------------------------------------------------------
    @Script("dual_ball00")
    public static void dual_ball00(ScriptManager sm) {
        sm.dropRewards(List.of(
                Reward.item(4032616, 1, 1, 0.25, 2363) // Mirror of Insight
        ));
    }
}
