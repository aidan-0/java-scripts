package Antiban;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import java.awt.*;
import java.util.Random;


@ScriptManifest(name = "Open Tabs", description = "Anti-ban", author = "Luten",
        version = 1.0, category = Category.UTILITY, image = "")
public class AntiBan extends AbstractScript {

    public final Point INVENTORY_WIDGET = new Point(643, 185); // Inventory menu
    public final Point SKILLS_WIDGET = new Point(577, 186); // Stats menu
    public final Point FRIENDS_WIDGET = new Point (575, 485); // Friends menu
    private static final Skill[] SKILLHOVER = {
            Skill.ATTACK, Skill.HITPOINTS, Skill.MINING, Skill.STRENGTH, Skill.AGILITY, Skill.SMITHING, Skill.DEFENCE, Skill.HERBLORE, Skill.FISHING,
            Skill.RANGED, Skill.THIEVING, Skill.COOKING, Skill.PRAYER, Skill.CRAFTING, Skill.FIREMAKING, Skill.MAGIC, Skill.FLETCHING, Skill.WOODCUTTING,
            Skill.RUNECRAFTING, Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER
    };
    private static final Random random = new Random();

    @Override
    public int onLoop() {
        return 2500;
    }



    public void onStart() {
        log("loading anti ban");
        // call chooseRandomTab or delayTime
    }


    public void chooseRandomTab(Skill skill) {
        //enter the skill you're training / to check
        // 1% chance to execute an anti-ban action
        if (random.nextDouble() <= 0.01) {
            int action = random.nextInt(3); // Generates a random number between 0 (inclusive) and 3 (exclusive)

            switch(action) {
                case 0:
                    openSkills(Skill.FISHING);
                    break;
                case 1:
                    openFriends();
                    break;
                case 2:
                    openInventory();
                    break;
            }
        }
    }

    int delayTimeShort = randomDelayShort();
    int delayTimeMedium = randomDelayMedium();
    int delayTimeLong = randomDelayLong();


    public int randomDelayShort() {
        double meanDelay = 12000;
        double variance = 10000;
        int delay = (int) (meanDelay + random.nextGaussian() * variance);
        delay = Math.max(1000, delay); // Ensure we have a minimum of 1 second delay
        delay = Math.min(100000, delay); // Ensure we have a maximum of 50 seconds delay
        log("Delaying for " + delay + " seconds.");
        return delay;
    }

    public int randomDelayMedium() {
        double meanDelay = 12000;
        double variance = 10000;
        int delay = (int) (meanDelay + random.nextGaussian() * variance);
        delay = Math.max(1000, delay); // Ensure we have a minimum of 1 second delay
        delay = Math.min(100000, delay); // Ensure we have a maximum of 50 seconds delay
        log("Delaying for " + delay + " seconds.");
        return delay;
    }

    public int randomDelayLong() {
        double meanDelay = 200000;
        double variance = 100000;
        int delay = (int) (meanDelay + random.nextGaussian() * variance);
        delay = Math.max(100000, delay); // Ensure we have a minimum of 1.66 minute delay
        delay = Math.min(900000, delay); // Ensure we have a maximum of 15 minute delay
        log("Delaying for " + delay + " seconds.");
        return delay;
    }

    public static int randomBetween(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public void openInventory() {
        if (!Tabs.isOpen(Tab.INVENTORY)) {
            if (Calculations.random(1, 3) == 2)
                Tabs.openWithFKey(Tab.INVENTORY);
            else {
                int x = (int) INVENTORY_WIDGET.getX() + randomBetween(0, 11);
                int y = (int) INVENTORY_WIDGET.getY() + randomBetween(0, 11);
                Mouse.move(new Point(x, y));
                Mouse.click();
            }
            log("Opening Inventory");
            sleep(50, 250);
        }
    }

    public void openSkills(Skill skill) {
        if (!Tabs.isOpen(Tab.SKILLS)) {
            if (Calculations.random(1, 3) == 2)
                Tabs.openWithFKey(Tab.SKILLS);
            else {
                int x = (int) SKILLS_WIDGET.getX() + randomBetween(0, 11);
                int y = (int) SKILLS_WIDGET.getY() + randomBetween(0, 11);
                Mouse.move(new Point(x, y));
                Mouse.click();
            }
            log("Opening Skills");
            sleep(302, 750);
        }
//      Hover fishing skill
        Skills.hoverSkill(skill);
        sleep(600, 2500);
        openInventory();
        Mouse.move(new Point(800, Calculations.random(0,502))); //Move mouse off the screen to the right
    }

    public void openFriends() {
        if (!Tabs.isOpen(Tab.FRIENDS)) {
            if (Calculations.random(1, 3) == 2) {
                Tabs.openWithFKey(Tab.FRIENDS);
            }
            else {
                int x = (int) FRIENDS_WIDGET.getX() + randomBetween(0, 11);
                int y = (int) FRIENDS_WIDGET.getY() + randomBetween(0, 11);
                Mouse.move(new Point(x, y));
                Mouse.click();
            }
            log("Opening Friends");
            sleep(600, 1500);
            openInventory();
            Mouse.move(new Point(800, Calculations.random(0,502))); //Move mouse off the screen to the right
        }
    }

    @Override
    public void onExit() {
        super.onExit();
    }

}
