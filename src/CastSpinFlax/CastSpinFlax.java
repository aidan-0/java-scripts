package CastSpinFlax;

import Antiban.AntiBan;
import MasterSelector.MasterScript;
import org.dreambot.api.input.Keyboard;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.magic.Lunar;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.awt.*;



@ScriptManifest(name = "Cast Spin Flax", description = "Cast Spin Flax on Lunar Isle", author = "Luten",
        version = 1.0, category = Category.CRAFTING, image = "")
public class CastSpinFlax extends AbstractScript {

//    private final MasterScript masterScript;
    State state;
    Timer timer = new Timer();
    private int flaxSpun = 0;
    private int previousXP = 0;
//    String fishToCook;
    AntiBan antiBan = new AntiBan();

//    public CastSpinFlax(MasterScript masterScript, String selectedFishType) {
//        this.masterScript = masterScript;
////        this.fishToCook = selectedFishType;
//    }


    @Override
    public void onStart() {
        log("Script Started");
        SkillTracker.start();
        SkillTracker.start(Skill.CRAFTING);
//        setZoomAndYaw();

        previousXP = Skills.getExperience(Skill.CRAFTING);
    }


    @Override
    public int onLoop() {
        switch (getState()) {
            case WITHDRAW_ITEMS:
                sleep(antiBan.randomDelayMedium(18));
                sleep(antiBan.randomDelayLong(1));
                if (!Bank.isOpen() && NPCs.closest(6126) != null) {
                    NPCs.closest(6126).interact("Bank");
                    sleep(2600, 3400);
                }

                if(!Inventory.onlyContains("Astral rune", "Nature rune", "Bow string", "Flax")) {
                    log("Inventory contained unwanted item(s). Banking all and re-gearing");
                    Bank.depositAllItems();
                }

                if(Inventory.contains("Bow string")) {
                    Bank.depositAll("Bow string");
                    sleep(1600, 2100);
                }

                if (!Inventory.contains("Astral rune") && Bank.contains("Astral rune")) {
                    Bank.withdrawAll("Astral rune");
                    log("Withdrawing Astral runes");
                    sleep(1600, 2100);
                } else if (!Inventory.contains("Astral rune") && !Bank.contains("Astral rune")) {
                    log("Failed to withdraw Astral runes");
                    stop();
//                    masterScript.signalStop();
                }

                if(!Inventory.contains("Nature rune") && Bank.contains("Nature rune")) {
                    Bank.withdrawAll("Nature rune");
                    log("Withdrawing Nature runes");
                    sleep(1600, 2100);
                } else if (!Inventory.contains("Nature rune") && !Bank.contains("Nature rune")) {
                    log("Failed to withdraw Nature runes");
                    stop();
//                    masterScript.signalStop();
                }

                if (!Inventory.contains("Flax") && Bank.contains("Flax")) {
                    Bank.withdraw("Flax", 25);
                    log("Withdrawing 25 Flax");
                    sleep(1600, 2100);
                } else if (!Inventory.contains("Flax") && Bank.contains("Flax")) {
                    log("Failed to withdraw flax");
                    stop();
//                    masterScript.signalStop();
                }
                Bank.close();
                sleep(600, 1200);
                break;


            case CAST_SPIN_FLAX:
                antiBan.verySmallMouseAdjustment(8);
                Magic.castSpell(Lunar.SPIN_FLAX);
                log("casting Spin Flax");
                antiBan.mouseOffScreenForFewSeconds(4);
                antiBan.randomDelayShort(6);
                sleep(1400, 2300);
                checkXPGains();
                break;

            case RESTOCK_ASTRALS:
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
        int currentXP = Skills.getExperience(Skill.CRAFTING);
        if (currentXP > previousXP) {
            flaxSpun += 5;
            previousXP = currentXP;
        }
    }


    private State getState() {
        if (!Inventory.contains("Astral rune") && !Bank.contains("Astral rune")) {
            log("State is now RESTOCK_ASTRALS");
            return State.RESTOCK_ASTRALS;
//            If restock astrals checkbox is ticked (true) then when astrals run out it will purchase the amount of astrals input by the user from babas shop, before continuing the script
            //this will only work if you have started on lunar isle
        }

        if (!Inventory.contains("Astral rune") || !Inventory.contains("Nature rune") || !Inventory.contains("Flax")) {
            log("State is now WITHDRAW_ITEMS");
            return State.WITHDRAW_ITEMS;

        } else if (Inventory.contains("Astral rune") && Inventory.contains("Nature rune") && Inventory.count("Flax") > 4)  {
            //and air staff equipped
            log("State is now CAST_SPIN_FLAX");
            return State.CAST_SPIN_FLAX;

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
        g.drawString("Crafting XP/HR: " + (SkillTracker.getGainedExperiencePerHour(Skill.CRAFTING)), 35, 50);
        g.drawString("Flax spun: " + flaxSpun, 35, 70);
        g.drawString("Bowstrings per hour: " + timer.getHourlyRate(flaxSpun), 35, 90);
        g.drawString("Timer: " + timer.formatTime(), 35, 110);

        long milliseconds = SkillTracker.getTimeToLevel(Skill.CRAFTING);
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