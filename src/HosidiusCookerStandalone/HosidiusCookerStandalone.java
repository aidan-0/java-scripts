package HosidiusCookerStandalone;

import Antiban.AntiBan;
import org.dreambot.api.input.Keyboard;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.awt.*;


public class HosidiusCookerStandalone extends AbstractScript {

    private final HosidiusCookerGui hosidiusCookerGui;
    State state;
    Area hosidiusKitchen = new Area(1674, 3622, 1682, 3615);
    Timer timer = new Timer();
    Point clickCookLocation = null;
    private int totalCooked = 0;
    private int previousXP = 0;
    String fishToCook;
    AntiBan antiBan = new AntiBan();

    public HosidiusCookerStandalone(HosidiusCookerGui hosidiusCookerGui, String selectedFishType) {
        this.hosidiusCookerGui = hosidiusCookerGui;
        this.fishToCook = selectedFishType; // Initialize fishToCook with the selected fish type
    }


    @Override
    public void onStart() {
        log("Script Started");
        SkillTracker.start();
        SkillTracker.start(Skill.COOKING);
        setZoomAndYaw();
        previousXP = Skills.getExperience(Skill.COOKING);
        log("Fish to cook: " + fishToCook);
        log("Fish to cook: " + fishToCook.toLowerCase());
    }


    @Override
    public int onLoop() {
        switch (getState()) {
            case SETUP:
                if (!hosidiusKitchen.contains(Players.getLocal().getTile())) {
                    Walking.walk(hosidiusKitchen);
                    log("Moving to hosidius Kitchen");
                    sleep(600, 1200);
                }
                break;


            case WITHDRAWING_RAW_ITEMS:
                if (!Bank.isOpen()) {
                    GameObject bankChest = GameObjects.closest("Bank Chest");
                    if (bankChest != null) {
                        bankChest.interact("Use");
                    }
                    sleep(2600, 3400);
                }

                if (Inventory.isEmpty()) {
                    if (Bank.contains("Raw " + fishToCook)) {
                        Bank.withdrawAll("Raw " + fishToCook.toLowerCase());
                        log("Withdrawing Raw " + fishToCook);
                        sleep(1600, 2100);
                    } else {
                        log("Failed to withdraw Raw " + fishToCook.toLowerCase());
                        stop();
                        hosidiusCookerGui.signalStop();
                    }
                    Bank.close();
                    sleep(600, 1200);
                }
                break;


            case DEPOSITING_COOKED_ITEMS:
                sleep(antiBan.randomDelayMedium(20));
                sleep(antiBan.randomDelayLong(1));
                if (!Bank.isOpen() || Players.getLocal().isStandingStill()) {
                    GameObject bankChest = GameObjects.closest("Bank Chest");
                    bankChest.interact("Use");
                    sleep(2600, 3400);
                }

                Bank.depositAllItems();
                log("Depositing Inventory");
                sleep(600, 2400);
                break;


            case COOKING:
                // If not animating and clickCookLocation hasn't been set yet
                if (!Players.getLocal().isAnimating() && clickCookLocation == null && fishToCook.equals("Karambwan")) {
                    GameObject clayOven = GameObjects.closest("Clay oven");
                    clayOven.interact();
                    sleep(3000, 3800);
                    clickCookLocation = new Point(Calculations.random(272, 365), Calculations.random(393, 456));
                    Mouse.move(clickCookLocation);
                    sleep(10,250);
                    Mouse.click();
                    sleep(50,100);
                    Mouse.move(new Point(800, Calculations.random(0, 502))); //Move mouse off the screen to the right
                }
                // If not animating and clickCookLocation has been set
                else if (!Players.getLocal().isAnimating()) {
                    GameObject clayOven = GameObjects.closest("Clay oven");
                    clayOven.interact();
                    sleep(3000, 3800);
                    Keyboard.type(" ");
                    sleep(200, 450);
                    Mouse.move(new Point(800, Calculations.random(0, 502))); //Move mouse off the screen to the right
                    sleep(200, 450);
                } else if (!Players.getLocal().isAnimating()) {
                    sleep(1000, 5000);
                }
                sleep(400,600);
                checkXPGains();
                break;
        }
        return 1;
    }

    private void setZoomAndYaw() {
        int zoom = Calculations.random(275, 450);
        log("Setting zoom level");
        Camera.setZoom(zoom);

        int yaw = Calculations.random(1, 360);
        log("Setting Yaw");
        Camera.rotateToYaw(yaw);
    }

    private void checkXPGains() {
        int currentXP = Skills.getExperience(Skill.COOKING);
        if (currentXP > previousXP) {
            totalCooked++;
            previousXP = currentXP;
        }
    }

    private State getState() {
        if (!hosidiusKitchen.contains(Players.getLocal().getTile())) {
            log("State is now SETUP");
            return State.SETUP;
        } else if (Inventory.isEmpty() && hosidiusKitchen.contains(Players.getLocal().getTile())) {
            log("State is now WITHDRAWING_RAW_ITEMS");
            return State.WITHDRAWING_RAW_ITEMS;

        } else if (!Inventory.contains("Raw " + fishToCook.toLowerCase()) && hosidiusKitchen.contains(Players.getLocal().getTile())) {
            log("State is now DEPOSITING_COOKED_ITEMS");
            return State.DEPOSITING_COOKED_ITEMS;

        } else if (Inventory.contains("Raw " + fishToCook.toLowerCase()) && hosidiusKitchen.contains(Players.getLocal().getTile())) {
//        log("State is now COOKING");
            return State.COOKING;
        }
        log("returning state");
        return state;
    }

    @Override
    public void onPaint(Graphics g) {
        super.onPaint(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(30, 35, 150, 100);
        g.setFont(new Font("Arial", Font.PLAIN, 12));


        g.setColor(Color.white);
        g.drawString("Cooking XP/HR: " + (SkillTracker.getGainedExperiencePerHour(Skill.COOKING)), 35, 50);
        g.drawString("Fish Cooked: " + totalCooked, 35, 70);
        g.drawString("Cooked per hour: " + timer.getHourlyRate(totalCooked), 35, 90);
        g.drawString("Timer: " + timer.formatTime(), 35, 110);

        long milliseconds = SkillTracker.getTimeToLevel(Skill.COOKING);
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        g.drawString("TTL: " + hours + " hours, " + minutes + " minutes.", 35, 130);
    }

    @Override
    public void onExit() {
        super.onExit();
    }
}