package Antiban;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import java.awt.*;
import java.util.Random;


@ScriptManifest(name = "Open Tabs", description = "Anti-ban", author = "Luten",
        version = 1.0, category = Category.UTILITY)
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
        //Everything that can be called
//        sleep(antiBan.randomDelayMedium(100)); medium, short, or long, with % of it executing
//        antiBan.chooseRandomTab(Skill.FISHING, 50); //skill to hover and % of it executing
//        hoverRandomSkill
//        openInventory()
//        openSkills();
    }




    public void chooseRandomTab(Skill skill, double chance) {
        // Check if the method should execute based on the provided chance
        if (!shouldExecute(chance)) {
            log("Failed chance check for chooseRandomTab");
            return;
        }

        log("Choosing random tab");
        int action = random.nextInt(3); // Generates a random number between 0 (inclusive) and 3 (exclusive)

        switch(action) {
            case 0:
                openSkills(skill);  // Use the provided skill
                break;
            case 1:
                openFriends();
                break;
            case 2:
                openInventory();
                break;
        }
    }

    private boolean shouldExecute(double chance) {
        return random.nextDouble() < (chance / 100.0);
    }

//    Call them like this
//    int delayTimeShort = randomDelayShort(10); 10%
//    int delayTimeMedium = randomDelayMedium(10) 10%
//    sleep(antiBan.randomDelayMedium(100)); 100%
//    int delayTimeLong = randomDelayLong(10); 10%


    public int randomDelayShort(double chance) {
        if (shouldExecute(chance)) {
            log("Executing short delay");
            double meanDelay = 2000;
            double variance = 500;
            int delay = (int) (meanDelay + random.nextGaussian() * variance);
            delay = Math.max(400, delay); // Ensure we have a minimum of 1-second delay
            delay = Math.min(6500, delay); // Ensure we have a maximum of 50 seconds delay
            log("Delaying for " + (delay/1000) + " seconds.");
            return delay;
        } else {
            log("Skipped short delay");
            return 0; // or any default value for "skipped"
        }
    }

    public int randomDelayMedium(double chance) {
        if (shouldExecute(chance)) {
            log("executing medium delay");
            double meanDelay = 12000;
            double variance = 10000;
            int delay = (int) (meanDelay + random.nextGaussian() * variance);
            delay = Math.max(1000, delay); // Ensure we have a minimum of 1-second delay
            delay = Math.min(100000, delay); // Ensure we have a maximum of 50 seconds delay
            log("Delaying for " + (delay/1000) + " seconds.");
            return delay;
        } else {
            log("Skipped Medium delay");
            return 0; // or any default value for "skipped"
        }
    }

    public int randomDelayLong(double chance) {
        if (shouldExecute(chance)) {
            log("Executing long delay");
            double meanDelay = 200000;
            double variance = 100000;
            int delay = (int) (meanDelay + random.nextGaussian() * variance);
            delay = Math.max(100000, delay); // Ensure we have a minimum of 1.66-minute delay
            delay = Math.min(900000, delay); // Ensure we have a maximum of 15-minute delay
            log("Delaying for " + (delay/1000) + " seconds.");
            return delay;
        } else {
            log("Skipped Long delay");
            return 0; // or any default value for "skipped"
        }
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
//      Hover specific skill
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

    public void hoverRandomSkill() {
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
        Skill randomSkill = SKILLHOVER[random.nextInt(SKILLHOVER.length)];  // This line randomly selects a skill
        Skills.hoverSkill(randomSkill);
        sleep(600, 2500);
        openInventory();
        Mouse.move(new Point(800, Calculations.random(0,502))); //Move mouse off the screen to the right
    }

    public void mouseOffScreenForFewSeconds(double chance) {
        if (shouldExecute(chance)) {
            Mouse.move(new Point(800, Calculations.random(0,502))); //Move mouse off the screen to the right
            sleep(4200,7500);
            Mouse.move(new Point(770,  Calculations.random(0,502)));
            log("moving mouse off screen for a few seconds");
        } else {
//            log("Skipped mouseOffScreenForFewSeconds");
        }
    }

    public void verySmallMouseAdjustment(double chance) {
        if (shouldExecute(chance)) {
            log("very small mouse adjustment");
            Point mousePoint = Mouse.getPosition();
            int x = mousePoint.x;
            int y = mousePoint.y;

            int deltaX = random.nextInt(7) - 3;
            int deltaY = random.nextInt(7) - 3;
            Mouse.move(new Point(x + deltaX, y + deltaY));
        } else {
//            log("Skipped verySmallMouseAdjustment");
        }
    }

    public void hopWorlds() {
        World world = Worlds.getRandomWorld(w -> w.isMembers() && w.isNormal() && !w.isHighRisk() && !w.isDeadmanMode() && Skills.getTotalLevel() >= w.getMinimumLevel() && !w.isPVP() && w.getPopulation() < 1500);
        WorldHopper.hopWorld(world.getWorld());
        log("Hopping to world " + world.getWorld());
        sleep(5000, 6000);
    }

    @Override
    public void onExit() {
        super.onExit();
    }
}
