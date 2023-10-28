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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@ScriptManifest(name = "Open Tabs", description = "Randomly click on invent, friends, or skill tab. If on skill tab then hover random skill", author = "Developer Name",
        version = 1.0, category = Category.UTILITY, image = "")
public class OpenTabs extends AbstractScript {

    public final Point INVENTORY_WIDGET = new Point(643, 185); // Inventory menu
    public final Point SKILLS_WIDGET = new Point(577, 186); // Stats menu
    public final Point FRIENDS_WIDGET = new Point (575, 485); // Friends menu
    private static final Skill[] SKILLHOVER = {
            Skill.ATTACK, Skill.HITPOINTS, Skill.MINING, Skill.STRENGTH, Skill.AGILITY, Skill.SMITHING, Skill.DEFENCE, Skill.HERBLORE, Skill.FISHING,
            Skill.RANGED, Skill.THIEVING, Skill.COOKING, Skill.PRAYER, Skill.CRAFTING, Skill.FIREMAKING, Skill.MAGIC, Skill.FLETCHING, Skill.WOODCUTTING,
            Skill.RUNECRAFTING, Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER
    };
    private static final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public int onLoop() {
        return 600;
    }



    public void onStart() {
        log("loading tabs anti ban");
        scheduleChooseRandomTab();
        log("tabs anti-ban is loaded");

    }

    private void scheduleChooseRandomTab() {
        // Random delay between 5 and 15 minutes (converted to milliseconds)
        long delay = (5 + random.nextInt(11)) * 60 * 1000;

        scheduler.schedule(this::chooseRandomTab, delay, TimeUnit.MILLISECONDS);
    }

    private void chooseRandomTab() {
        int choice = random.nextInt(3);

        switch (choice) {
            case 0:
                openInventory();
                break;
            case 1:
                openSkills();
                break;
            case 2:
                openFriends();
                break;
        }
        scheduleChooseRandomTab();
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

    public void openSkills() {
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
//      Hover random skill
        Skill randomSkill = SKILLHOVER[random.nextInt(SKILLHOVER.length)];
        Skills.hoverSkill(randomSkill);
        sleep(150, 500);
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
            sleep(50, 250);
        }
    }

    @Override
    public void onExit() {
        scheduler.shutdownNow();
        super.onExit();
    }

}

