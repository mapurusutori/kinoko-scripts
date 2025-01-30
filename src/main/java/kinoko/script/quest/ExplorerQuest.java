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
    @Script("fighter")
    public static void fighter(ScriptManager sm) { // TODO: First and Second Job Adv. + Third Job Quest for Explorer Warrior
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (job == 0 && level >= 10) {
            sm.sayNext("So, you want to become a Warrior? Well, you need to meet some requirements to do so. You need to be at least #bLevel 10#k. Let's see...");
            if (!sm.askYesNo("Oh...! You look like someone that can definitely be a part of us... all you need is a little slang, and... yeah... so, what do you think? Wanna be the Warrior?")) {
                sm.sayOk("Being a Pirate isn't for everyone...");
                return;
            }
            if (!sm.addItems(List.of(
                    Tuple.of(1372043, 1), // Beginner Magician's wand
                    Tuple.of(1382100, 1) // Beginner Magician's Staff
            ))) {
                sm.sayNext("Please check if your inventory is full or not.");
                return;
            }
            sm.setJob(Job.WARRIOR);
            sm.addInventorySlots(InventoryType.EQUIP, 4);
            sm.addInventorySlots(InventoryType.ETC, 4);
            sm.sayNext("From here on out, you are going to the Warrior path. This is not an easy job, but if you have discipline and confidence in your own body and skills, you will overcome any difficulties in your path. Go, young Warrior!");
            if (sm.getUser().getLevel() > 10) {
                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
            }
            sm.sayBoth("I have added slots to the EQUIP and ETC pockets of your Item Inventory. You have also gotten much stronger. Keep training, one day you'll reach the very top. I'll be watching you from afar.");
            sm.sayBoth("I just gave you a little bit of #bSP#k. Open the #bSkill menu#k at the lower right corner. There are skills you can learn by using SP. One warning, though: You can't boost them up all at once. Some skills are only available after you have learned other skills. Please keep that in mind.");
            sm.sayBoth("One more warning, though it seems kind of obvious. Once you have chosen your job, try your best to stay alive. Every death will cost you a certain amount of experience points, and you don't want to lose those, do you?");
            sm.sayBoth("Well, then! This is all I can teach you. Now, train and better yourself. Find me when you feel like you've done all that you can and need some a change of pace... I'll be waiting for you.");
        } else if (sm.hasQRValue(QuestRecordType.ExplorerWarrior2ndJobAdv, "2")) {
            sm.sayNext("OHH... You came back safe! I know you'd breeze through... I'll admit you are a strong, formidable Warrior. Alright, I'll make you an even stronger Warrior than you already are right now... Before THAT, though, you need to choose one of the three paths that you'll be given... It isn't going to be easy, so if you have any questions, feel free to ask.");
            final int answer = sm.askMenu("Alright, when you have made your decision, click on #b[I'll choose my occupation!] at the very bottom!", Map.ofEntries(
                    Map.entry(0, "Please explain the role of a Fighter."),
                    Map.entry(1, "Please explain the role of a Page."),
                    Map.entry(2, "Please explain the role of a Spearman."),
                    Map.entry(3, "I'll choose my occupation!")
            ));
            switch (answer) {
                case 0 -> {
                    sm.sayNext("Let me explain the role of a Fighter. It's the most common kind of Warrior. The weapons they use are #bswords#k and #baxes#k, because there will be advanced skills available to acquire later on. I strongly recommend you avoid using both weapons, but rather, stick to one of your liking..."); // TODO: GMS-like text
                    sm.sayBoth("Other than that, there are also skills such as #bRage#k and #bPower Guard#k available for Fighters. #bRage#k is the kind of ability that allows you and your party to temporarily enhance your weapon power. With that you can take out the enemies with a sudden surge of power, so it'll come in handy for you. The downside to this is that your defense goes down a bit.");
                    sm.sayBoth("#bPower Guard#k is an ability that enables you to return a portion of the damage that you take from a physical hit by an enemy. The harder the hit, the harder the damage they'll get in return. It'll help those that prefer close combat. What do you think? Isn't being a Fighter pretty cool?");
                }
                case 1 -> {
                    sm.sayPrev("1"); // TODO: GMS-like text
                }
                case 2 -> {
                    sm.sayPrev("2"); // TODO: GMS-like text
                }
                case 3 -> {
                    final int jobselect = sm.askMenu("Okay, have you made your decision?", Map.ofEntries(
                            Map.entry(0, "Fighter"),
                            Map.entry(1, "Page"),
                            Map.entry(2, "Spearman")
                    ));
                    switch (jobselect) {
                        case 0 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Warrior to a #bFighter#k? Once you have made that decision, you can't go back and change your mind and choose another job... Do you still want to do it?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.FIGHTER);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.addInventorySlots(InventoryType.ETC, 4);
                            sm.setQRValue(QuestRecordType.ExplorerWarrior2ndJobAdv, "3");
                            sm.sayNext("Alright! You have no become a #bFighter#k. A Fighter strives to become the strongest of the strong, and never stops fighting. Don't ever lose that will to fight, and push forward 24/7. I'll help you become even stronger than you already are.");
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Fighter. I have also added a whole row to your USE and ETC inventory, along with boosting your Max HP and MP. Go check and see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you were a bit late with making the job advancement. No need to worry, I have compensated you with additional Skill Points (SP) that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("Fighters have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                        case 1 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Warrior to a #bPage#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.PAGE);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bPage#k. Pages revel in shadows and darkness, waiting for the right time to come for them to stick a dagger through the hearts of their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Page. I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            sm.sayBoth("Pages have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                        case 2 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Warrior to a #bSpearman#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.SPEARMAN);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bSpearman#k. Spearmen revel in shadows and darkness, waiting for the right time to come for them to stick a dagger through the hearts of their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Spearman. I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            sm.sayBoth("Spearmen have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                    }
                }
            }
        } else if (job == 100 && level >= 30) {
            if (!sm.hasQRValue(QuestRecordType.ExplorerWarrior2ndJobAdv, "1")) {
                if (sm.askYesNo("Whoa, you have definitely grown up! You don't look small and weak anymore... Rather, now I can feel your presence as a Warrior! Impressive... So, what do you think? Do you want to get even stronger than you are right now? Pass a simple test and I'll do just that! Wanna do it?")) {
                    sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                    return;
                }
                sm.sayNext("Good thinking. You look strong, don't get me wrong, but there's still a need to test your strength and see if you are for real. The test isn't too difficult, so you'll do just fine. Please go speak with to #b#p1072003##k around #b#m103030400##k near #m102000000#");
                sm.sayBoth("He's the one being the instructor now in place of me, as I am busy here. Go speak with him and he'll give you the test in place of me. If you want more details, hear it straight from him. Best of luck to you!");
                sm.setQRValue(QuestRecordType.ExplorerWarrior2ndJobAdv, "1");
            } else {
                sm.sayOk("He's the one being the instructor now in place of me, as I am busy here. Go speak with him and he'll give you the test in place of me. If you want more details, hear it straight from him. Best of luck to you!");
            }
        } else if (sm.hasQRValue(QuestRecordType.ExplorerWarrior3rdJobAdv, "1")) {
            sm.sayNext("I've been waiting for you... #b#p2020008##k of #b#m211000000##k told me about you awhile back. So, you're interested in taking the leap to the third job advancement for Warriors? To do that, I will have to test your strength to see whether you are worthy of the advancement. You will find a #p1061009# deep inside the Cursed Temple in the heart of Victoria Island. Once inside, you'll face my clone. Your task is to defeat him and bring the #b#t4031059##k back with you.");
            sm.sayBoth("Since he is a clone of me, you can expect a tough battle ahead. He uses a number of special attack skills unlike any you have very seen and it is your task to take him down. There is a time limit in the secret passage, so it is crucial you defeat him fast. I wish you the best of luck and I hope you bring the #b#t4031059##k back with you.");
        } else {
            sm.sayOk("You don't look like you're interested in becoming a Warrior. Please make your way out."); // placeholder text because I'm too lazy rn
        }
    }

    @Script("magician")
    public static void magician(ScriptManager sm) { // TODO: First and Second Job Adv. + Third Job Quest for Explorer Magician
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (job == 0 && level >= 8) {
            sm.sayNext("Do you want to be a Magician? You need to meet some requirements in order to do so. You need to be at least #bLevel 8#k. Let's see if you have what it takes to become a Magician...");
            if (!sm.askYesNo("You definitely have the look of a Magician. You may not be there yet, but I can see the Magician in you... What do you think? Do you want to become a Magician?")) {
                sm.sayOk("Being a Magician isn't for everyone...");
                return;
            }
            if (!sm.addItems(List.of(
                    Tuple.of(1372043, 1) // Beginner Wand
                    // TODO: Add starter potions
            ))) {
                sm.sayNext("Please check if your inventory is full or not.");
                return;
            }
            sm.setJob(Job.MAGICIAN);
            sm.addInventorySlots(InventoryType.EQUIP, 4);
            sm.addInventorySlots(InventoryType.ETC, 4);
            sm.sayNext("You're now a Magician from here on out! It ain't much, but as head Magician, I, #p1032001#, will give you a little bit of what I have...");
            if (sm.getUser().getLevel() > 8) {
                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
            }
            sm.sayBoth("You have just equipped yourself with more magical power. Please continue training and improving. I'll be watching you here and there.");
            sm.sayBoth("I've also added slots to the EQUIP and ETC pockets of your Item Inventory, and I just gave you a little bit of #bSP#k. Open the #bSkill menu#k at the lower right corner. There are skills you can learn by using SP. One warning, though: You can't boost them up all at once. Some skills are only available after you have learned other skills. Please keep that in mind.");
            sm.sayBoth("Oh! One more warning, though it seems kind of obvious. Once you have chosen your job, try your best to stay alive. Every death will cost you a certain amount of experience points, and you don't want to lose those, do you?");
            sm.sayBoth("Well, then! This is all I can teach you. Now, train and better yourself. Find me when you feel like you've done all that you can and need something interesting. I'll hook you up then. I'll be waiting for you.");
        } else if (sm.hasQRValue(QuestRecordType.ExplorerMagician2ndJobAdv, "2")) {
            sm.sayNext("So you got back here safely? I knew that test would be too easy for you. I admit, you are a great Magician. Now, I'll make you even more powerful than you already are. But, before that... You need to choose on of two paths. It'll be a difficult yet important decision for you to make, but if you have any questions, please ask.");
            final int answer = sm.askMenu("Okay, have you made your decision? Click on #b[I'll choose my occupation!] at the very bottom if you have.", Map.ofEntries(
                    Map.entry(0, "Please explain the characteristics of a Wizard (Fire/Poison)."),
                    Map.entry(1, "Please explain the characteristics of a Wizard (Ice/Lightning)."),
                    Map.entry(2, "Please explain the characteristics of a Cleric."),
                    Map.entry(3, "I'll choose my occupation!")
            ));
            switch (answer) {
                case 0 -> {
                    sm.sayPrev("Magicians that master #rfire and poison based magic#k.\r\n\r\n#bWizards#k are a active class that deal magical, elemental damage. These abilities grants them a significant advantage against enemies weak to their element. With their skills #rMeditation#k and #rSlow#k, #bWizards#k can increase their magic attack and reduce the opponent's mobility. #bFire & Poison Wizards#k contains a powerful flame arrow attack and poison attack."); // TODO: GMS-like text
                }
                case 1 -> {
                    sm.sayPrev("Magicians that master #rice and lightning based magic#k.\r\n\r\n#bWizards#k are a active class that deal magical, elemental damage. These abilities grants them a significant advantage against enemies weak to their element. With their skills #rMeditation#k and #rSlow#k, #bWizards#k can increase their magic attack and reduce the opponent's mobility. #bIce & Lightning Wizards#k have a freezing ice attack and a striking lightning attack."); // TODO: GMS-like text
                }
                case 2 -> {
                    sm.sayPrev("Magicians that master #rHoly magic#k.\r\n\r\n#bClerics#k are a powerful supportive class, bound to be accepted into any Party. That's because the have the power to #rHeal#k themselves and others in their party. Using #rBless#k, #bClerics#k can buff the attributes and reduce the amount of damage taken. This class is on worth going for if you find it hard to survive. #bClerics#k are especially effective against undead monsters."); // TODO: GMS-like text
                }
                case 3 -> {
                    final int jobselect = sm.askMenu("---", Map.ofEntries(
                            Map.entry(0, "Wizard (Fire/Poison)"),
                            Map.entry(1, "Wizard (Ice/Lightning)"),
                            Map.entry(2, "Cleric")
                    ));
                    switch (jobselect) {
                        case 0 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Magician to a #bWizard (Fire/Poison)#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.WIZARD_FP);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bWizard (Fire/Poison)#k. Wizards (Fire/Poison) strike from the shadows at a rapid pace, raining throwing stars down onto their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Wizard (Fire/Poison). I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("Wizards (Fire/Poison) have to be agile. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                        case 1 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Magician to a #bWizard (Ice/Lightning)#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.WIZARD_IL);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bWizard (Ice/Lightning)#k. Wizards (Ice/Lightning) revel in shadows and darkness, waiting for the right time to come for them to stick a dagger through the hearts of their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Wizard (Ice/Lightning). I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("Wizards (Ice/Lightning) have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                        case 2 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Magician to a #bCleric#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.CLERIC);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bCleric#k. Clerics revel in shadows and darkness, waiting for the right time to come for them to stick a dagger through the hearts of their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Cleric. I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("Clerics have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                    }
                }
            }
        } else if (job == 200 && level >= 30) {
            if (!sm.hasQRValue(QuestRecordType.ExplorerMagician2ndJobAdv, "1")) {
                if (sm.askYesNo("Hmmm... You seem to have gotten a whole lot stronger. You got rid of the old, weak self and look much more like a thief now. Well, what do you think? Don't you want to get even more powerful than that? Pass a simple test and I'll do just that for you. Do you want to do it?")) {
                    sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                    return;
                }
                sm.sayNext("Good thinking. But, I need to make sure you are as strong as you look. It's not a hard test, one that should be easy for you to pass. First, take this letter... Make sure you don't lose it.");
                sm.sayBoth("Please go speak with to #b#p1072003##k around #b#m103030400##k near #m101000000#. He's doing the job of an instructor in place of me. If you want more details, hear it straight from him. I'll be wishing you good luck.");
                sm.setQRValue(QuestRecordType.ExplorerMagician2ndJobAdv, "1");
            } else {
                sm.sayOk("Please go speak with #b#p1072003##k around #b#m103030400##k near #m101000000#. He's doing the job of an instructor in place of me. If you want more details, hear it straight from him. I'll be wishing you good luck.");
            }
        } else if (sm.hasQRValue(QuestRecordType.ExplorerMagician3rdJobAdv, "1")) {
            sm.sayNext("I've been waiting for you... #b#p2020009##k of #b#m211000000##k told me about you awhile back. So, you're interested in taking the leap to the third job advancement for Magicians? To do that, I will have to test your strength to see whether you are worthy of the advancement. You will find a #p1061009# deep inside the Cursed Temple in the heart of Victoria Island. Once inside, you'll face my clone. Your task is to defeat him and bring the #b#t4031059##k back with you.");
            sm.sayBoth("Since he is a clone of me, you can expect a tough battle ahead. He uses a number of special attack skills unlike any you have ever seen and it is your task to take him down. There is a time limit in the secret passage, so it is crucial you defeat him fast. I wish you the best of luck and I hope you bring the #b#t4031059##k back with you.");
        } else {
            sm.sayOk("You don't look like you're interested in becoming a Magician. However, feel free to browse our #b#m101000003##k."); // placeholder text because I'm too lazy rn
        }
    }

    @Script("bowman")
    public static void bowman(ScriptManager sm) { // TODO: First and Second Job Adv. + Third Job Quest for Explorer Archer
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (job == 0 && level >= 10) {
            sm.sayNext("So, you want to become a Archer? Well, you need to meet some requirements to do so. You need to be at least #bLevel 10#k. Let's see...");
            if (!sm.askYesNo("Oh...! You look like someone that can definitely be a part of us... all you need is a little slang, and... yeah... so, what do you think? Wanna be the Pirate?")) {
                sm.sayOk("Being a Archer isn't for everyone...");
                return;
            }
            if (!sm.addItems(List.of(
                    Tuple.of(1482000, 1), // Steel Knuckler
                    Tuple.of(1492000, 1), // Pistol
                    Tuple.of(2330000, 1000) // Bullet
            ))) {
                sm.sayNext("Please check if your inventory is full or not.");
                return;
            }
            sm.setJob(Job.ARCHER);
            sm.addInventorySlots(InventoryType.EQUIP, 4);
            sm.addInventorySlots(InventoryType.ETC, 4);
            sm.sayNext("Alright, from here out, you are a part of us! You'll be living the life of a wanderer, but just be patient as soon, you'll be living the high life. Alright, it ain't much, but I'll give you some of my abilities... HAAAHHH!!!");
            if (sm.getUser().getLevel() > 10) {
                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
            }
            sm.sayBoth("I have added slots to the EQUIP and ETC pockets of your Item Inventory. You have also gotten much stronger. Keep training, one day you'll reach the very top. I'll be watching you from afar.");
            sm.sayBoth("I just gave you a little bit of #bSP#k. Open the #bSkill menu#k at the lower right corner. There are skills you can learn by using SP. One warning, though: You can't boost them up all at once. Some skills are only available after you have learned other skills. Please keep that in mind.");
            sm.sayBoth("One more warning, though it seems kind of obvious. Once you have chosen your job, try your best to stay alive. Every death will cost you a certain amount of experience points, and you don't want to lose those, do you?");
            sm.sayBoth("Well, then! This is all I can teach you. Now, train and better yourself. Find me when you feel like you've done all that you can and need something interesting. I'll hook you up then. I'll be waiting for you.");
        } else if (sm.hasQRValue(QuestRecordType.ExplorerArcher2ndJobAdv, "2")) {
            sm.sayNext("So you got back here safely? I knew that test would be too easy for you. I admit, you are a great Archer. Now, I'll make you even more powerful than you already are. But, before that... You need to choose on of two paths. It'll be a difficult yet important decision for you to make, but if you have any questions, please ask.");
            final int answer = sm.askMenu("Alright, when you have made your decision, click on #b[I'll choose my occupation!] at the very bottom!", Map.ofEntries(
                    Map.entry(0, "Please explain the characteristics of a Hunter."),
                    Map.entry(1, "Please explain the characteristics of a Crossbowman."),
                    Map.entry(2, "I'll choose my occupation!")
            ));
            switch (answer) {
                case 0 -> {
                    sm.sayPrev("0"); // TODO: GMS-like text
                }
                case 1 -> {
                    sm.sayPrev("1"); // TODO: GMS-like text
                }
                case 2 -> {
                    final int jobselect = sm.askMenu("Okay, have you made your decision?", Map.ofEntries(
                            Map.entry(0, "Hunter"),
                            Map.entry(1, "Crossbowman")
                    ));
                    switch (jobselect) {
                        case 0 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as an Archer to an #bHunter#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.HUNTER);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bHunter#k. Hunters strike from the shadows at a rapid pace, raining throwing stars down onto their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Hunter. I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            sm.sayBoth("Hunters have to be agile. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                        case 1 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as an Archer to a #bCrossbowman#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.CROSSBOWMAN);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bCrossbowman#k. Crossbowmen revel in shadows and darkness, waiting for the right time to come for them to stick a dagger through the hearts of their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as a Crossbowman. I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            sm.sayBoth("Crossbowmen have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                    }
                }
            }
        } else if (job == 300 && level >= 30) {
            if (!sm.hasQRValue(QuestRecordType.ExplorerArcher2ndJobAdv, "1")) {
                if (sm.askYesNo("Hmmm... You seem to have gotten a whole lot stronger. You got rid of the old, weak self and look much more like a thief now. Well, what do you think? Don't you want to get even more powerful than that? Pass a simple test and I'll do just that for you. Do you want to do it?")) {
                    sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                    return;
                }
                sm.sayNext("Good thinking. But, I need to make sure you are as strong as you look. It's not a hard test, one that should be easy for you to pass. First, take this letter... Make sure you don't lose it.");
                sm.sayBoth("Please go speak with to #b#p1072003##k around #b#m103030400##k near #m100000000#. He's doing the job of an instructor in place of me. If you want more details, hear it straight from him. I'll be wishing you good luck.");
                sm.setQRValue(QuestRecordType.ExplorerArcher2ndJobAdv, "1");
            } else {
                sm.sayOk("Please go speak with #b#p1072003##k around #b#m103030400##k near #m100000000#. He's doing the job of an instructor in place of me. If you want more details, hear it straight from him. I'll be wishing you good luck.");
            }
        } else if (sm.hasQRValue(QuestRecordType.ExplorerArcher3rdJobAdv, "1")) {
            sm.sayNext("I've been waiting for you... #b#p2020010##k of #b#m211000000##k told me about you awhile back. So, you're interested in taking the leap to the third job advancement for Archers? To do that, I will have to test your strength to see whether you are worthy of the advancement. You will find a #p1061009# deep inside the Cursed Temple in the heart of Victoria Island. Once inside, you'll face my clone. Your task is to defeat her and bring the #b#t4031059##k back with you.");
            sm.sayBoth("Since she is a clone of me, you can expect a tough battle ahead. She uses a number of special attack skills unlike any you have ever seen and it is your task to take her down. There is a time limit in the secret passage, so it is crucial you defeat her fast. I wish you the best of luck and I hope you bring the #b#t4031059##k back with you.");
        } else {
            sm.sayOk("You don't look like you're interested in becoming an Archer. If you have any other questions, I might have an answer."); // placeholder text because I'm too lazy rn
        }
    }

    @Script("rogue")
    public static void rogue(ScriptManager sm) {
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (job == 0 && level >= 10) {
            sm.sayNext("So, you want to become a Thief? Well, you need to meet some requirements to do so. You need to be at least #bLevel 10#k. Let's see...");
            if (!sm.askYesNo("You look like someone who belongs with us. You just need to be a little more sinister. So, do you want to be a Thief?")) {
                sm.sayOk("Being a Thief isn't for everyone...");
                return;
            }
            if (sm.hasQuestStarted(2351)) {
                sm.setQRValue(QuestRecordType.DualBladeFirstMission, "1");
            }
            if (JobConstants.isDualJob(job)) { // Not GMS-like but it doesn't make sense to give Dual Blade throwing stars and wrist guards in post-BB. In early-BB, it still gave DB these items.
                if (!sm.addItems(List.of(
                        Tuple.of(1332063, 1) // Beginner Thief's short sword
                        // TODO: Add starter potions
                ))) {
                    sm.sayNext("Please check if your inventory is full or not.");
                    return;
                }
            } else {
                if (!sm.addItems(List.of(
                        Tuple.of(2070015, 300), // Beginner Thief's Throwing Stars
                        Tuple.of(1472061, 1), // Beginner Thief's Wrist Guards
                        Tuple.of(1332063, 1) // Beginner Thief's short sword
                        // TODO: Add starter potions
                ))) {
                    sm.sayNext("Please check if your inventory is full or not.");
                    return;
                }
            }
            sm.setJob(Job.ROGUE);
            sm.addInventorySlots(InventoryType.EQUIP, 4);
            sm.addInventorySlots(InventoryType.ETC, 4);
            sm.sayNext("Alright, from here on out, you are a part of us! You'll be living the life of a wanderer at first, but just be patient. Soon, you'll be living the high life! Alright, it ain't much, but I'll give you some abilities.");
            sm.sayBoth("I have added slots to the EQUIP and ETC pockets of your Item Inventory. You have also gotten much stronger. Keep training, one day you'll reach the very top. I'll be watching you from afar.");
            sm.sayBoth("I just gave you a little bit of #bSP#k. Open the #bSkill menu#k at the lower right corner. There are skills you can learn by using SP. One warning, though: You can't boost them up all at once. Some skills are only available after you have learned other skills. Please keep that in mind.");
            if (sm.getUser().getLevel() > 10) {
                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
            }
            sm.sayBoth("One more warning, though it seems kind of obvious. Once you have chosen your job, try your best to stay alive. Every death will cost you a certain amount of experience points, and you don't want to lose those, do you?");
            sm.sayBoth("Well, then! This is all I can teach you. Now, train and better yourself. Find me when you feel like you've done all that you can and need something interesting. I'll hook you up then. I'll be waiting for you.");
        } else if (sm.hasQRValue(QuestRecordType.ExplorerThief2ndJobAdv, "2")) {
            sm.sayNext("So you got back here safely? I knew that test would be too easy for you. I admit, you are a great Thief. Now, I'll make you even more powerful than you already are. But, before that... You need to choose on of two paths. It'll be a difficult yet important decision for you to make, but if you have any questions, please ask.");
            final int answer = sm.askMenu("When you have made your decision, click on #b[I'll choose my occupation!] at the very bottom.", Map.ofEntries(
                    Map.entry(0, "Please explain the characteristics of an Assassin."),
                    Map.entry(1, "Please explain the characteristics of a Bandit."),
                    Map.entry(2, "I'll choose my occupation!")
            ));
            switch (answer) {
                case 0 -> {
                    sm.sayPrev("0"); // TODO: GMS-like text
                }
                case 1 -> {
                    sm.sayPrev("1"); // TODO: GMS-like text
                }
                case 2 -> {
                    final int jobselect = sm.askMenu("Okay, have you made your decision?", Map.ofEntries(
                            Map.entry(0, "Assassin"),
                            Map.entry(1, "Bandit")
                    ));
                    switch (jobselect) {
                        case 0 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Thief to an #bAssassin#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.ASSASSIN);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are an #bAssassin#k. Assassins strike from the shadows at a rapid pace, raining throwing stars down onto their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as an #bAssassin#k. I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("#bAssassin#ks have to be agile. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                        case 1 -> {
                            if (!sm.askYesNo("So you want to make the 2nd job advancement as a Thief to a #bBandit#k? Once you have made your decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                                sm.sayOk("This isn't a decision you should make lightly. Speak with me again when you've made up your mind.");
                                return;
                            }
                            sm.setJob(Job.BANDIT);
                            sm.addInventorySlots(InventoryType.EQUIP, 4);
                            sm.addInventorySlots(InventoryType.CONSUME, 4);
                            sm.sayNext("Alright, from here on out you are a #bBandit#k. Bandits revel in shadows and darkness, waiting for the right time to come for them to stick a dagger through the hearts of their foes. Please keep training, I'll make sure you become even more powerful than you are now!");
                            sm.sayBoth("I have just given you a new book that gives you a list of skills you can acquire as an #bBandit#k. I have also added a whole row to your EQUIP and USE inventory, along with boosting your Max HP and MP. Go see it for yourself.");
                            sm.sayBoth("I've also given you a little bit of #bSP#k again. Open the #bSkill Menu (K)#k located at the bottom-left corner. You'll be able to boost up the newly-acquired 2nd Job skills. A word of warning, though, much like before, you won't be able to boost them all up at once. Some of the skills are only available after you've learned other skills. Make sure to remember that.");
                            if (sm.getUser().getLevel() > 30) {
                                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
                            }
                            sm.sayBoth("#bBandit#ks have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because... For you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                        }
                    }
                }
            }
        } else if (job == 400 && level >= 30 && !JobConstants.isDualJob(job)) {
            if (!sm.hasQRValue(QuestRecordType.ExplorerThief2ndJobAdv, "1")) {
                if (sm.askYesNo("Hmmm... You seem to have gotten a whole lot stronger. You got rid of the old, weak self and look much more like a thief now. Well, what do you think? Don't you want to get even more powerful than that? Pass a simple test and I'll do just that for you. Do you want to do it?")) {
                    sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                    return;
                }
                sm.sayNext("Good thinking. But, I need to make sure you are as strong as you look. It's not a hard test, one that should be easy for you to pass. First, take this letter... Make sure you don't lose it.");
                sm.sayBoth("Please go speak with to #b#p1072003##k around #b#m103030400##k near #m103000000#. He's doing the job of an instructor in place of me. If you want more details, hear it straight from him. I'll be wishing you good luck.");
                sm.setQRValue(QuestRecordType.ExplorerThief2ndJobAdv, "1");
            } else {
                sm.sayOk("Please go speak with #b#p1072003##k around #b#m103030400##k near #m103000000#. He's doing the job of an instructor in place of me. If you want more details, hear it straight from him. I'll be wishing you good luck.");
            }
        } else if (sm.hasQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "1")) { // 3rd Job -- After talking to instructor in El Nath
            sm.sayNext("I've been waiting for you... #b#p2020011##k of #b#m211000000##k told me about you awhile back. So, you're interested in taking the leap to the third job advancement for Thieves? To do that, I will have to test your strength to see whether you are worthy of the advancement. You will find a #p1061009# deep inside the Cursed Temple in the heart of Victoria Island. Once inside, you'll face my clone. Your task is to defeat him and bring the #b#t4031059##k back with you.");
            sm.sayBoth("Since he is a clone of me, you can expect a tough battle ahead. He uses a number of special attack skills unlike any you have very seen and it is your task to take him down. There is a time limit in the secret passage, so it is crucial you defeat him fast. I wish you the best of luck and I hope you bring the #b#t4031059##k back with you.");
            sm.setQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "2");
        } else if (sm.hasQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "2")) { // 3rd Job -- Recap of above
            sm.sayOk("You will find a #p1061009# deep inside the Cursed Temple in the heart of Victoria Island. Once inside, you'll face my clone. Your task is to defeat him and bring the #b#t4031059##k back with you.");
        } else if (sm.hasQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "3")) { // 3rd Job -- Retrieved Black Charm
            sm.sayOk("");
        } else {
            sm.sayOk("You don't look like you're interested in becoming a Thief. I suggest you make your way out of here, this place isn't for everybody."); // placeholder text because I'm too lazy rn
        }
    }

    @Script("kairinT")
    public static void kairinT(ScriptManager sm) { // TODO: First and Second Job Adv. + Third Job Quest for Explorer Pirate
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (job == 0 && level >= 10) {
            sm.sayNext("So, you want to become a Pirate? Well, you need to meet some requirements to do so. You need to be at least #bLevel 10#k. Let's see...");
            if (!sm.askYesNo("Oh...! You look like someone that can definitely be a part of us... all you need is a little slang, and... yeah... so, what do you think? Wanna be the Pirate?")) {
                sm.sayOk("Being a Pirate isn't for everyone...");
                return;
            }
            if (!sm.addItems(List.of(
                    Tuple.of(1482000, 1), // Steel Knuckler
                    Tuple.of(1492000, 1), // Pistol
                    Tuple.of(2330000, 1000) // Bullet
                    // TODO: Add starter potions
            ))) {
                sm.sayNext("Please check if your inventory is full or not.");
                return;
            }
            sm.setJob(Job.PIRATE);
            sm.addInventorySlots(InventoryType.EQUIP, 4);
            sm.addInventorySlots(InventoryType.ETC, 4);
            sm.sayNext("Alright, from here out, you are a part of us! You'll be living the life of a wanderer, but just be patient as soon, you'll be living the high life. Alright, it ain't much, but I'll give you some of my abilities... HAAAHHH!!!");
            sm.sayBoth("I have added slots to the EQUIP and ETC pockets of your Item Inventory. You have also gotten much stronger. Keep training, one day you'll reach the very top. I'll be watching you from afar.");
            sm.sayBoth("I just gave you a little bit of #bSP#k. Open the #bSkill menu#k at the lower right corner. There are skills you can learn by using SP. One warning, though: You can't boost them up all at once. Some skills are only available after you have learned other skills. Please keep that in mind.");
            if (sm.getUser().getLevel() > 10) {
                sm.sayBoth("I think you are a bit late with making a job advancement. But don't worry, I have compensated you with additional Skill Points that you didn't receive by making the advancement so late.");
            }
            sm.sayBoth("One more warning, though it seems kind of obvious. Once you have chosen your job, try your best to stay alive. Every death will cost you a certain amount of experience points, and you don't want to lose those, do you?");
            sm.sayBoth("Well, then! This is all I can teach you. Now, train and better yourself. Find me when you feel like you've done all that you can and need something interesting. I'll hook you up then. I'll be waiting for you.");
        } else if (job == 500 && level >= 30) {
            sm.setQRValue(QuestRecordType.ExplorerPirate2ndJobAdv, "1");
            // TODO: finish second job adv.
        } else if (sm.hasQRValue(QuestRecordType.ExplorerPirate3rdJobAdv, "1")) {
            sm.sayNext("I've been waiting for you... #b#p2020013##k of #b#m211000000##k told me about you awhile back. So, you're interested in taking the leap to the third job advancement for Pirates? To do that, I will have to test your strength to see whether you are worthy of the advancement. You will find a #p1061009# deep inside the Cursed Temple in the heart of Victoria Island. Once inside, you'll face my clone. Your task is to defeat her and bring the #b#t4031059##k back with you.");
            sm.sayBoth("Since she is a clone of me, you can expect a tough battle ahead. She uses a number of special attack skills unlike any you have ever seen and it is your task to take her down. There is a time limit in the secret passage, so it is crucial you defeat her fast. I wish you the best of luck and I hope you bring the #b#t4031059##k back with you.");
        } else {
            sm.sayOk("You don't look like you're interested in becoming a Pirate. Please make your way out."); // placeholder text because I'm too lazy rn
        }
    }

    @Script("change_swordman")
    public static void change_swordman(ScriptManager sm) {
        if (sm.hasQRValue(QuestRecordType.ExplorerWarrior2ndJobAdv, "1")) {
            sm.sayNext("Hmmm... #b#p1022000##k sent word about you... So you came all this way to take the test and make the 2nd job advancement as a Warrior? Alright, I'll explain the test to you. Don't need to worry, it's not that complicated.");
            sm.sayBoth("I'll send you to a hidden map. You'll see monsters not normally seen in normal fields. They look the same like the regular ones, but with a totally different attitude. They neither boost your experience level nor provide you with items and mesos.");
            sm.sayBoth("You'll be able to acquire a marble called #b#t4031013##k while knocking out those monsters. It is a special marble made out of their sinister, evil minds. Collect 30 of those then come speak with me again. That's how you pass the test.");
            if (!sm.askYesNo("If you die, you will still lose experience points... So you better really buckle up and get ready. Well, do you want to go for it now?")) {
                sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                return;
            }
            sm.sayNext("Alright! I'll let you in! Defeat the monsters inside to earn 30 #t4031013# and then talk to me again. Best of luck to you.");
            sm.warpInstance(910230000, "sp", 102040300, 60 * 10);
        } else {
            sm.sayOk("Being a Warrior isn't easy, not everyone is cut out for it. Oh, excuse me, I didn't see you there. I'm busy and don't have time to talk, sorry.");
        }
    }

    @Script("inside_swordman")
    public static void inside_swordman(ScriptManager sm) {
        if (sm.hasItem(4031013, 30)) {
            sm.sayNext("Ohhh... You collected all 30 #t4031013#! Should have been difficult... Absolutely incredible! Alright, you passed the test and for that, I'll send word to #p1022000# you passed! Now head back to #m102000000#.");
            sm.setQRValue(QuestRecordType.ExplorerWarrior2ndJobAdv, "2");
            sm.removeItem(4031013, 30);
            sm.warp(102000003, "out02"); // Not GMS-like, QoL change
        } else {
            if (!sm.askYesNo("You haven't collected 30 #t4031013#. Are you giving up?")) {
                sm.sayOk("Talk to me again if you decide to try again.");
                sm.warp(102040300);
            }
        }
    }

    @Script("change_magician")
    public static void change_magician(ScriptManager sm) {
        if (sm.hasQRValue(QuestRecordType.ExplorerMagician2ndJobAdv, "1")) {
            sm.sayNext("Hmmm... #b#p1032001##k sent word about you... So you came all this way to take the test and make the 2nd job advancement as a Magician? Alright, I'll explain the test to you. Don't sweat it too much, it's not that complicated.");
            sm.sayBoth("I'll send you to a hidden map. You'll see monsters not normally seen in normal fields. They look the same like the regular ones, but with a totally different attitude. They neither boost your experience level nor provide you with items and mesos.");
            sm.sayBoth("You'll be able to acquire a marble called #b#t4031013##k while knocking out those monsters. It is a special marble made out of their sinister, evil minds. Collect 30 of those then come speak with me again. That's how you pass the test.");
            if (!sm.askYesNo("If you die, you will still lose experience points... So you better really buckle up and get ready. Well, do you want to go for it now?")) {
                sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                return;
            }
            sm.sayNext("Alright! I'll let you in! Defeat the monsters inside to earn 30 #t4031013# and then talk to me again. Best of luck to you.");
            sm.warpInstance(910140000, "sp", 101040300, 60 * 10);
        } else {
            sm.sayOk("Being a Magician isn't easy, not everyone is cut out for it. Oh, excuse me, I didn't see you there. I'm busy and don't have time to talk, sorry.");
        }
    }

    @Script("inside_magician")
    public static void inside_magician(ScriptManager sm) {
        if (sm.hasItem(4031013, 30)) {
            sm.sayNext("Ohhh... You collected all 30 #t4031013#! Should have been difficult... Absolutely incredible! Alright, you passed the test and for that, I'll send word to #p1032001# you passed! Now head back to #m101000000#.");
            sm.setQRValue(QuestRecordType.ExplorerMagician2ndJobAdv, "2");
            sm.removeItem(4031013, 30);
            sm.warp(101000003, "rank00"); // Not GMS-like, QoL change
        } else {
            if (!sm.askYesNo("You haven't collected 30 #t4031013#. Are you giving up?")) {
                sm.sayOk("Talk to me again if you decide to try again.");
                sm.warp(101040300);
            }
        }
    }


    @Script("change_archer")
    public static void change_archer(ScriptManager sm) {
        if (sm.hasQRValue(QuestRecordType.ExplorerArcher2ndJobAdv, "1")) {
            sm.sayNext("Hmmm... #b#p1012100##k sent word about you... So you came all this way to take the test and make the 2nd job advancement as an Archer? Alright, I'll explain the test to you. Don't sweat it too much, it's not that complicated.");
            sm.sayBoth("I'll send you to a hidden map. You'll see monsters not normally seen in normal fields. They look the same like the regular ones, but with a totally different attitude. They neither boost your experience level nor provide you with items and mesos.");
            sm.sayBoth("You'll be able to acquire a marble called #b#t4031013##k while knocking out those monsters. It is a special marble made out of their sinister, evil minds. Collect 30 of those then come speak with me again. That's how you pass the test.");
            if (!sm.askYesNo("If you die, you will still lose experience points... So you better really buckle up and get ready. Well, do you want to go for it now?")) {
                sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                return;
            }
            sm.sayNext("Alright! I'll let you in! Defeat the monsters inside to earn 30 #t4031013# and then talk to me again. Best of luck to you.");
            sm.warpInstance(910070000, "sp", 100040400, 60 * 10);
        } else {
            sm.sayOk("Being an Archer isn't easy, not everyone is cut out for it. Oh, excuse me, I didn't see you there. I'm busy and don't have time to talk, sorry."); // Not GMS-like
        }
    }

    @Script("inside_archer")
    public static void inside_archer(ScriptManager sm) {
        if (sm.hasItem(4031013, 30)) {
            sm.sayNext("Ohhh... You collected all 30 #t4031013#! Should have been difficult... Absolutely incredible! Alright, you passed the test and for that, I'll send word to #p1012100# you passed! Now head back to #m100000000#.");
            sm.setQRValue(QuestRecordType.ExplorerArcher2ndJobAdv, "2");
            sm.removeItem(4031013, 30);
            sm.warp(100000201, "out02"); // Not GMS-like, QoL change
        } else {
            if (!sm.askYesNo("You haven't collected 30 #t4031013#. Are you giving up?")) {
                sm.sayOk("Talk to me again if you decide to try again.");
                sm.warp(100040400);
            }
        }
    }


    @Script("change_rogue")
    public static void change_rogue(ScriptManager sm) {
        if (sm.hasQRValue(QuestRecordType.ExplorerThief2ndJobAdv, "1")) {
            sm.sayNext("Hmmm... #b#p1052001##k sent word about you... So you came all this way to take the test and make the 2nd job advancement as a Thief? Alright, I'll explain the test to you. Don't sweat it too much, it's not that complicated.");
            sm.sayBoth("I'll send you to a hidden map. You'll see monsters not normally seen in normal fields. They look the same like the regular ones, but with a totally different attitude. They neither boost your experience level nor provide you with items and mesos.");
            sm.sayBoth("You'll be able to acquire a marble called #b#t4031013##k while knocking out those monsters. It is a special marble made out of their sinister, evil minds. Collect 30 of those then come speak with me again. That's how you pass the test.");
            if (!sm.askYesNo("If you die, you will still lose experience points... So you better really buckle up and get ready. Well, do you want to go for it now?")) {
                sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                return;
            }
            sm.sayNext("Alright! I'll let you in! Defeat the monsters inside to earn 30 #t4031013# and then talk to me again. Best of luck to you.");
            sm.warpInstance(910370000, "sp", 103030400, 60 * 10);
        } else {
            sm.sayOk("Being a Thief isn't easy, not everyone is cut out for it. Oh, excuse me, I didn't see you there. I'm busy and don't have time to talk, sorry.");
        }
    }

    @Script("inside_rogue")
    public static void inside_rogue(ScriptManager sm) {
        if (sm.hasItem(4031013, 30)) {
            sm.sayNext("Ohhh... You collected all 30 #t4031013#! Should have been difficult... Absolutely incredible! Alright, you passed the test and for that, I'll send word to #p1052001# you passed! Now head back to #m103000000#.");
            sm.setQRValue(QuestRecordType.ExplorerThief2ndJobAdv, "2");
            sm.removeItem(4031013, 30);
            sm.warp(103000003, "rank00"); // Not GMS-like, QoL change
        } else {
            if (!sm.askYesNo("You haven't collected 30 #t4031013#. Are you giving up?")) {
                sm.sayOk("Talk to me again if you decide to try again.");
                sm.warp(103030400);
            }
        }
    }


    @Script("warrior3")
    public static void warrior3(ScriptManager sm) { // TODO: Explorer Warrior Third Job Adv. + Zakum Dungeon Quest
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (JobConstants.isWarriorJob(job)) {
            final int answer = sm.askMenu("Yes? What is it? What do you want?", Map.ofEntries(
                    Map.entry(0, "I want to " + (job == 110 ? "become a Crusader" : job == 120 ? "become a White Knight" : job == 130 ? "become a Dragon Knight" : "take the next job advancement") + "."),
                    Map.entry(1, "Nevermind.") // "Grant me permission for the Zakum Dungeon Quest"
            ));
            switch (answer) {
                case 0 -> {
                    if (JobConstants.isWarrior2ndJob(job) && level >= 70) {
                        if (!sm.askYesNo("Ahh, so you wish to make your 3rd Job Advancement? I have the power to make you stronger but I first must test your persistence. Do you wish to take my test?")) {
                            sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                            return;
                        }
                        sm.setQRValue(QuestRecordType.ExplorerWarrior3rdJobAdv, "1");
                        sm.sayNext("There are two things you must prove you possess: Strength and Intelligence. First, I shall test your strength. Do you remember #p1022000# in #m102000000# who gave you your first job advancement? He will give you a mission. Complete that mission and bring back #b#t4031059##k from #p1052001#.");
                        sm.sayBoth("The second test will determine your intelligence, but first pass this one and bring back the #b#t##k. I will let #p1022000# know that you are coming... Good luck.");
                    } else {
                        sm.sayOk("You are not ready for the next job advancement. Please come back after you've trained more.");
                    }
                }
                case 1 -> {
                    sm.sayNext("Safe travels, young adventurer."); // Zakum Dungeon Quest text here
                }
            }
        } else {
            sm.sayOk("I have nothing to offer you.");
        }
    }

    @Script("wizard3")
    public static void wizard3(ScriptManager sm) { // TODO: Explorer Magician Third Job Adv. + Zakum Dungeon Quest
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (JobConstants.isMagicianJob(job)) {
            final int answer = sm.askMenu("Yes? What is it? What do you want?", Map.ofEntries(
                    Map.entry(0, "I want to " + (job == 210 ? "become a Mage (Fire/Poison)" : job == 220 ? "become a Mage (Ice/Lightning)" : job == 230 ? "become a Priest" : "take the next job advancement") + "."),
                    Map.entry(1, "Nevermind.") // "Grant me permission for the Zakum Dungeon Quest" -- TODO: Is available at level 50.
            ));
            switch (answer) {
                case 0 -> {
                    if (JobConstants.isMagician2ndJob(job) && level >= 70) {
                        if (!sm.askYesNo("Ahh, so you wish to make your 3rd Job Advancement? I have the power to make you stronger but I first must test your persistence. Do you wish to take my test?")) {
                            sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                            return;
                        }
                        sm.setQRValue(QuestRecordType.ExplorerMagician3rdJobAdv, "1");
                        sm.sayNext("There are two things you must prove you possess: Strength and Intelligence. First, I shall test your strength. Do you remember #p1032001# in #m101000000# who gave you your first job advancement? He will give you a mission. Complete that mission and bring back #b#t4031059##k from #p1052001#.");
                        sm.sayBoth("The second test will determine your intelligence, but first pass this one and bring back the #b#t##k. I will let #p1032001# know that you are coming... Good luck.");
                    } else {
                        sm.sayOk("You are not ready for the next job advancement. Please come back after you've trained more.");
                    }
                }
                case 1 -> {
                    sm.sayNext("Safe travels, young adventurer."); // Zakum Dungeon Quest text here
                }
            }
        } else {
            sm.sayOk("I have nothing to offer you."); // placeholder text, most likely won't be GMS-like
        }
    }

    @Script("bowman3")
    public static void bowman3(ScriptManager sm) { // TODO: Explorer Archer Third Job Adv. + Zakum Dungeon Quest
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (JobConstants.isArcherJob(job)) {
            final int answer = sm.askMenu("Yes? What is it? What do you want?", Map.ofEntries(
                    Map.entry(0, "I want to " + (job == 310 ? "become a Ranger" : job == 320 ? "become a Sniper" : "take the next job advancement") + "."),
                    Map.entry(1, "Nevermind.") // "Grant me permission for the Zakum Dungeon Quest" -- TODO: Is available at level 50.
            ));
            switch (answer) {
                case 0 -> {
                    if (JobConstants.isArcher2ndJob(job) && level >= 70) {
                        if (!sm.askYesNo("Ahh, so you wish to make your 3rd Job Advancement? I have the power to make you stronger but I first must test your persistence. Do you wish to take my test?")) {
                            sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                            return;
                        }
                        sm.setQRValue(QuestRecordType.ExplorerArcher3rdJobAdv, "1");
                        sm.sayNext("There are two things you must prove you possess: Strength and Intelligence. First, I shall test your strength. Do you remember #p1012100# in #m100000000# who gave you your first job advancement? He will give you a mission. Complete that mission and bring back #b#t4031059##k from #p1052001#.");
                        sm.sayBoth("The second test will determine your intelligence, but first pass this one and bring back the #b#t##k. I will let #p1012100# know that you are coming... Good luck.");
                    } else {
                        sm.sayOk("You are not ready for the next job advancement. Please come back after you've trained more.");
                    }
                }
                case 1 -> {
                    sm.sayNext("Safe travels, young adventurer."); // Zakum Dungeon Quest text here
                }
            }
        } else {
            sm.sayOk("I have nothing to offer you.");
        }
    }

    @Script("thief3")
    public static void thief3(ScriptManager sm) { // TODO: Third Job Advancement for other Explorer Thief jobs & Zakum Dungeon Quest, only handles Dual Blade at the moment
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (JobConstants.isThiefJob(job)) {
            final int answer = sm.askMenu("Yes? What is it? What do you want?", Map.ofEntries(
                    Map.entry(0, "I want to " + (job == 410 ? "become a Hermit" : job == 420 ? "become a Chief Bandit" : job == 432 ? "become a Blade Lord" : "take the next job advancement") + "."),
                    Map.entry(1, "Nevermind.") // "Grant me permission for the Zakum Dungeon Quest"
            ));
            switch (answer) {
                case 0 -> {
                    if (sm.hasQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "1")) {
                        sm.sayOk("#p1052001# in #m103000000# is waiting for your arrival. Make haste, young adventurer.");
                    } else if (JobConstants.isThief2ndJob(job) && level >= 70) {
                        if (!sm.askYesNo("Ahh, so you wish to make your 3rd Job Advancement? I have the power to make you stronger but I first must test your persistence. Do you wish to take my test?")) {
                            sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                            return;
                        }
                        sm.setQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "1");
                        sm.sayNext("There are two things you must prove you possess: Strength and Intelligence. First, I shall test your strength. Do you remember #p1052001# in #m103000000# who gave you your first job advancement? He will give you a mission. Complete that mission and bring back #b#t4031059##k from #p1052001#.");
                        sm.sayBoth("The second test will determine your intelligence, but first pass this one and bring back the #b#t4031059##k. I will let #p1052001# know that you are coming... Good luck.");
                    } else {
                        sm.sayOk("You are not ready for the next job advancement. Please come back after you've trained more.");
                    }
                }
                case 1 -> {
                    sm.sayNext("Safe travels, young adventurer."); // Zakum Dungeon Quest text here
                }
            }
        } else {
            sm.sayOk("I have nothing to offer you.");
        }
    }

    @Script("pirate3")
    public static void pirate3(ScriptManager sm) { // TODO: Explorer Pirate Third Job Adv. + Zakum Dungeon Quest
        final int job = sm.getUser().getJob();
        final int level = sm.getLevel();
        if (JobConstants.isThiefJob(job)) {
            final int answer = sm.askMenu("Yes? What is it? What do you want?", Map.ofEntries(
                    Map.entry(0, "I want to " + (job == 510 ? "become a Marauder" : job == 520 ? "become an Outlaw" : "take the next job advancement") + "."),
                    Map.entry(1, "Nevermind.") // "Grant me permission for the Zakum Dungeon Quest"
            ));
            switch (answer) {
                case 0 -> {
                    if (JobConstants.isPirate2ndJob(job) && level >= 70) {
                        if (sm.hasItem(4031059)) {
                            sm.sayNext("Great job completing the physical part of the test. I knew you could do it. Now that you have passed the first half of the test, here's the second half.");
                        }
                        if (!sm.askYesNo("Ahh, so you wish to make your 3rd Job Advancement? I have the power to make you stronger but I first must test your persistence. Do you wish to take my test?")) {
                            sm.sayOk("This test is not for everyone. Come speak to me again if you reconsider.");
                            return;
                        }
                        sm.setQRValue(QuestRecordType.ExplorerPirate3rdJobAdv, "1");
                        sm.sayNext("There are two things you must prove you possess: Strength and Intelligence. First, I shall test your strength. Do you remember #p1072008# in #m120000000# who gave you your first job advancement? He will give you a mission. Complete that mission and bring back #b#t4031059##k from #p1052001#.");
                        sm.sayBoth("The second test will determine your intelligence, but first pass this one and bring back the #b#t4031059##k. I will let #p1072008# know that you are coming... Good luck.");
                    } else {
                        sm.sayOk("You are not ready for the next job advancement. Please come back after you've trained more.");
                    }
                }
                case 1 -> {
                    sm.sayNext("Safe travels, young adventurer."); // Zakum Dungeon Quest text here
                }
            }
        } else {
            sm.sayOk("I have nothing to offer you.");
        }
    }

    @Script("crack")
    public static void crack(ScriptManager sm) {
        if (!sm.hasItem(4031059) && ((sm.hasQRValue(QuestRecordType.ExplorerWarrior3rdJobAdv, "2") || sm.hasQRValue(QuestRecordType.ExplorerMagician3rdJobAdv, "2") || sm.hasQRValue(QuestRecordType.ExplorerArcher3rdJobAdv, "2") || sm.hasQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "2") || sm.hasQRValue(QuestRecordType.ExplorerPirate3rdJobAdv, "2")))) {
            sm.playPortalSE();
            sm.warp(910540000, "sp");
        } else {
            sm.message("A mysterious force prevents you from entering.");
        }
    }

    @Script("3jobExit")
    public static void third_jobExit(ScriptManager sm) {
        if (!sm.askYesNo("(Do you wish to leave?)")) {
            return;
        }
        if (sm.hasItem(4031059)) {
            if (sm.hasQRValue(QuestRecordType.ExplorerWarrior3rdJobAdv, "2")) {
                sm.setQRValue(QuestRecordType.ExplorerWarrior3rdJobAdv, "3");
                sm.warp(102000003, "out02");
            } else if (sm.hasQRValue(QuestRecordType.ExplorerMagician3rdJobAdv, "2")) {
                sm.setQRValue(QuestRecordType.ExplorerMagician3rdJobAdv, "3");
                sm.warp(101000003, "rank00");
            } else if (sm.hasQRValue(QuestRecordType.ExplorerArcher3rdJobAdv, "2")) {
                sm.setQRValue(QuestRecordType.ExplorerArcher3rdJobAdv, "3");
                sm.warp(100000201, "out02");
            } else if (sm.hasQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "2")) {
                sm.setQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "3");
                sm.warp(103000003, "rank00");
            } else if (sm.hasQRValue(QuestRecordType.ExplorerPirate3rdJobAdv, "2")) {
                sm.setQRValue(QuestRecordType.ExplorerPirate3rdJobAdv, "3");
                sm.warp(120000101, "ao00");
            }
        } else {
            sm.warp(105030500); // Forbidden Altar -- did not retrieve Black Charm
        }
    }

    // NPCS ------------------------------------------------------------------------------------------------------------
    // TODO: Properly handle Power B. Fore's Training Centers, these are only handled for specific quests and / or free entry to everyone
    @Script("enter_warrior")
    public static void enter_warrior(ScriptManager sm) {
        sm.playPortalSE();
        sm.warpInstance(910220000, "start", 102020000, 60 * 30); // Victoria Road : Warrior Training Center
    }

    @Script("enter_magician")
    public static void enter_magician(ScriptManager sm) {
        sm.playPortalSE();
        sm.warpInstance(910120000, "start", 101020000, 60 * 30); // Victoria Road : Magician Training Center
    }

    @Script("enter_archer")
    public static void enter_archer(ScriptManager sm) {
        // Power B. Fore : Entrance to Bowman Training Center (1012119)
        //   Singing Mushroom Forest : Spore Hill (100020000)
        sm.playPortalSE();
        if (sm.hasQuestStarted(22518)) {
            sm.warpInstance(910060100, "start", 100020000, 60 * 30);
            return;
        }
        sm.warp(910060000); // Victoria Road : Bowman Training Center
    }

    @Script("enter_thief")
    public static void enter_thief(ScriptManager sm) {
        sm.playPortalSE();
        if (sm.hasQuestStarted(2377)) {
            sm.warpInstance(910310000, "start", 103010000, 60 * 30); // Victoria Road : Thief Training Center
            return;
        }
        sm.warpInstance(910310000, "start", 103010000, 60 * 30); // Victoria Road : Thief Training Center
    }

    @Script("enter_pirate")
    public static void enter_pirate(ScriptManager sm) {
        sm.playPortalSE();
        sm.warpInstance(912030000, "start", 120020000, 60 * 30); // Victoria Road : Pirate Training Center
    }

    @Script("hong-a")
    public static void honga(ScriptManager sm) {
        // Associated with Ryden in Kerning City
        // Currently unused?
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
                    } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("0;1;0")) {
                        sm.setQRValue(QuestRecordType.DualBladeFifthMission, "0;1;1");
                        sm.setNpcAction(1043002, "q2358");
                    } else if (sm.getQRValue(QuestRecordType.DualBladeFifthMission).contains("2;1;0")) {
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
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149, true);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149, true);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149, true);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149, true);
            sm.spawnMob(9001019, MobAppearType.NORMAL, 103, 149, true);
        } else {
            if (!sm.getUser().getField().getMobPool().isEmpty()) {
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
            sm.sayOk("You've obtained the #p1052126#. Time to get out of here before someone catches you!");
            sm.removeNpc(1052126);
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
    public static void dual_lv20(ScriptManager sm) {
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
            if (sm.getQRValue(QuestRecordType.DualBladeDiary).equals("2")) {
                sm.message("A mysterious force prevents you from entering.");
                return;
            }
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

    @Script("3th_jobQuestMap")
    public static void third_jobQuestMap(ScriptManager sm) {
        if (!sm.hasItem(4031059)) {
            if (sm.hasQRValue(QuestRecordType.ExplorerWarrior3rdJobAdv, "2")) {
                sm.playPortalSE();
                sm.warpInstance(910540100, "sp", 910540000, 60 * 30);
            } else if (sm.hasQRValue(QuestRecordType.ExplorerMagician3rdJobAdv, "2")) {
                sm.playPortalSE();
                sm.warpInstance(910540200, "sp", 910540000, 60 * 30);
            } else if (sm.hasQRValue(QuestRecordType.ExplorerArcher3rdJobAdv, "2")) {
                sm.playPortalSE();
                sm.warpInstance(910540300, "sp", 910540000, 60 * 30);
            } else if (sm.hasQRValue(QuestRecordType.ExplorerThief3rdJobAdv, "2")) {
                sm.playPortalSE();
                sm.warpInstance(910540400, "sp", 910540000, 60 * 30);
            } else if (sm.hasQRValue(QuestRecordType.ExplorerPirate3rdJobAdv, "2")) {
                sm.playPortalSE();
                sm.warpInstance(910540500, "sp", 910540000, 60 * 30);
            } else {
                sm.message("A mysterious force prevents you from entering.");
            }
        } else {
            sm.message("A mysterious force prevents you from entering.");
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
        sm.addSp(430, 5); // reference video displays gaining 5 SP at this advancement. Not sure if this is handled in setJob. Doesn't look like it.
        sm.addExp(2000);
        sm.sayOk("You are now a #bBlade Recruit#k. Take pride in that fact.");
    }

    @Script("q2369e")
    public static void q2369e(ScriptManager sm) { // Text is GMS-like, however, changes were made for "immersion" purposes.
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
        sm.addSp(431, 1);
        sm.addExp(5000);
        sm.sayNext("My father's diary... Father would often right in a code that only he and I could understand...");
        sm.sayBoth("Wait! In the last entry... What's this?!");
        sm.sayBoth("This can't be! It's a lie! Jin...! How dare you lay a finger on my father's diary!");
        sm.sayBoth("#b(#p1056000# drops the diary and it falls to the ground.)");
        sm.sayBoth("#b(You pick up the diary and start reading it.)");
        sm.setSpeakerId(1052126);
        sm.sayBoth("#e - Date: XX-XX-XXXX - \r\n\r\nTeacher has passed away. Three days ago, teacher left for the #m105090900# at the request of Tristan. #p1056000# seemed worried so I decided to go looking for him. When I arrived at the entrance of #m105090900#, I heard a shriek that sent chills throughout my entire body.");
        sm.sayBoth("#eWhen I jumped into the darkness of the #m105090900#, I came face to face with a red-eyed monster spewing the most evil and vile energy I've ever felt. Teacher was nowhere to be seen. The monster started attacking. After a fierce battle, I finally succeeded in slaying it. However, the fallen monster soon turned into... teacher.");
        sm.sayBoth("#eI attempted to help teacher, but he passed away in my arms. Before he passed, he whispered:\r\nMy soul was trapped within the Balrog. You freed me... Now, take care of #m103000000# and #p1056000#... And... Please don't tell a soul about this, I can't forgive myself for allowing a demon to overtake my soul.");
        sm.sayBoth("#eAs he wished, I will never reveal what happened. His secret, along with this diary, will forever be sealed and kept hidden.\r\n\r\n -- Jin");
    }

    @Script("q2374e")
    public static void q2374e(ScriptManager sm) {
        sm.sayNext("I've been waiting for you. Do you have #p2020011#'s answer? Please give me his letter.");
        sm.sayBoth("We've finally received #p2020011#'s official recognition. This is an important moment for us. It's also time that you experienced a change.");
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
