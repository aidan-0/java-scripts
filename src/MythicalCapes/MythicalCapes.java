package MythicalCapes;

import Antiban.AntiBan;
import MasterSelector.MasterScript;
import org.dreambot.api.input.Keyboard;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.Shop;
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
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.awt.*;



@ScriptManifest(name = "Make mythical capes", description = "Make mythical cape mounts in POH", author = "Luten",
        version = 1.0, category = Category.CONSTRUCTION, image = "")
public class MythicalCapes extends AbstractScript {

    //    private final MasterScript masterScript;
    State state;
    Timer timer = new Timer();
    private int capesMade = 0;
    private int previousXP = 0;
    Area rimmington = new Area(2957, 3228, 2943, 3208,0);
    Area insideHouse = new Area(1, 2, 3, 4, 0);
    GameObject guildTrophySpace = GameObjects.closest("Guild trophy space");
    GameObject mountedMythCape = GameObjects.closest(31986);

    AntiBan antiBan = new AntiBan();



    @Override
    public void onStart() {
        log("Script Started");
        SkillTracker.start();
        SkillTracker.start(Skill.CONSTRUCTION);
//        setZoomAndYaw();
        log("Teak planks: ");
        log(Inventory.count("Teak plank"));

        previousXP = Skills.getExperience(Skill.CONSTRUCTION);
    }


    @Override
    public int onLoop() {
        switch (getState()) {
            case BANKING:
//!Inventory.contains("Crystal saw") || !Inventory.contains("Hammer") || !Inventory.contains("Mythical cape") || !Inventory.contains("Coins") || !Inventory.contains("Teak plank"))
// add banking setup later
//                    if(!bankArea.contains(Players.getLocal().getTile())) {
//                    Walking.walk(bankArea);
//                    log("Moving to bank area");
//                    sleepUntil(() -> bankArea.contains(Players.getLocal().getTile()), 5000, 50);
//                    sleep(600, 1200);
//                } else {
//                    sleep(antiBan.randomDelayMedium(18));
//                    sleep(antiBan.randomDelayLong(1));
//                    if (!Bank.isOpen() && NPCs.closest(6126) != null) {
//                        NPCs.closest(6126).interact("Bank");
//                        Sleep.sleepUntil(Bank::isOpen, 15000);
//                        sleep(600, 1400);
//                    }
//
//                    if (!Inventory.onlyContains("Astral rune", "Nature rune", "Teak plank", "Teak logs", "Coins")) {
//                        log("Inventory contained unwanted item(s). Banking all and re-gearing");
//                        Bank.depositAllItems();
//                        Sleep.sleepUntil(Inventory::isEmpty, 2000);
//                    }
//
//                    if (Inventory.contains("Teak plank")) {
//                        Bank.depositAll("Teak plank");
//                        Sleep.sleepUntil(Inventory::isEmpty, 2000);
//                        sleep(200, 600);
//                    }
//
//                    if (!Inventory.contains("Astral rune") && Bank.contains("Astral rune")) {
//                        Bank.withdrawAll("Astral rune");
//                        log("Withdrawing Astral runes");
//                        sleepUntil(() -> Inventory.contains("Astral rune"), 10000, 50);
//                        sleep(1600, 2100);
//                    } else if (!Inventory.contains("Astral rune") && !Bank.contains("Astral rune")) {
//                        log("Failed to withdraw Astral runes");
//                        if (numOfAstralRunesToPurchase > 1) {
//                            break;
//                        } else {
//                            stop();
//                            //                    masterScript.signalStop();
//                        }
//                    }
//
//                    if (!Inventory.contains("Nature rune") && Bank.contains("Nature rune")) {
//                        Bank.withdrawAll("Nature rune");
//                        log("Withdrawing Nature runes");
//                        sleepUntil(() -> Inventory.contains("Nature rune"), 10000, 50);
//
//                        sleep(1600, 2100);
//                    } else if (!Inventory.contains("Nature rune") && !Bank.contains("Nature rune")) {
//                        log("Failed to withdraw Nature runes");
//                        stop();
//                        //                    masterScript.signalStop();
//                    }
//
//                    if (!Inventory.contains("Teak Logs") && Bank.contains("Teak logs")) {
//                        Bank.withdrawAll("Teak Logs");
//                        log("Withdrawing Teak logs");
//                        sleepUntil(() -> Inventory.contains("Teak Logs"), 10000, 50);
//                        sleep(300,600);
//                    } else if (!Inventory.contains("Teak Logs") && !Bank.contains("Teak Logs")) {
//                        log("Failed to withdraw Teak Logs");
//                        stop();
//                        //                    masterScript.signalStop();
//                    }
//                    Bank.close();
//                }
                sleep(600, 1200);
                break;


            case MOVE_TO_HOUSE:
                sleep(400,600);
                break;

            case MAKE_MYTH_CAPES:
                //interact, "Build" "Guild trophy space" - 15394
                //widget 458 -> 7 -> interact


//                if (!Players.getLocal().isAnimating()) {
//                    Magic.castSpell(Lunar.PLANK_MAKE);
//                    log("casting PLANK MAKE");
//                    Mouse.click();
//                    sleep(600, 1200);
//                    checkXPGains();
//                    Mouse.move(new Point(800, Calculations.random(0,502))); //Move mouse off the screen to the right
//                    sleep(600);
//                }
//                checkXPGains();
                sleep(400,600);
                break;

            case REMOVE_MYTH_CAPES:
                //remove mythical cape - "Mythical cape" - 31986 - "Remove"
                //press 1
                sleep(400,600);
                break;

            case EXIT_HOUSE:
                sleep(antiBan.randomDelayMedium(18));
                sleep(antiBan.randomDelayLong(1));
                // "Enter" portal
                //delay until within area 2957, 3228, 2943, 3208, 0
                GameObject portal = GameObjects.closest("Portal");
                portal.interact("Exit");
                sleep(400,600);
                break;

            case UNNOTE_PLANKS:
                // in future i will integrate the butler. for now just set your house to rimmington - butler instructions below
                        //tab settings
                        // interact widget 116 -> 31
                        //call servant - interact widget 370 -> 22
                        // press 1

                //click noted teak planks
                //use Teak plank on Phials
                // wait until widget 219 -> 1 -> 3 !null and then interact with it or alternatively press 3 once it is not null

//                if (Inventory.contains("Coins") && !nearBabaYagaHouse.contains(Players.getLocal().getTile()) || !inBabaYagaHouse.contains(Players.getLocal().getTile())) {
//                    Walking.walk(nearBabaYagaHouse);
//                    log("Moving near Baba Yaga's House");
//                    sleepUntil(() -> nearBabaYagaHouse.contains(Players.getLocal().getTile()), 10000, 50);
//                    sleep(600, 1200);
//                }
//
//                if (NPCs.closest(3836) != null) { //the house
//                    NPCs.closest(3836).interact("Go-inside");
//                    sleepUntil(() -> inBabaYagaHouse.contains(Players.getLocal().getTile()), 10000, 50);
//                }


                sleep(400,600);
                break;


            case RETURN_TO_HOUSE:
                //interact "Build mode" Portal
                GameObject portal1 = GameObjects.closest("Portal");
                portal1.interact("Build mode");
                sleep(2500,4000);
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
        int currentXP = Skills.getExperience(Skill.MAGIC);
        if (currentXP > previousXP) {
            capesMade++;
            previousXP = currentXP;
        }
    }




    //choose saw type - crystal saw or

    private State getState() {
        if (!Inventory.contains("Crystal saw") || !Inventory.contains("Hammer") || !Inventory.contains("Mythical cape") || !Inventory.contains("Coins") || !Inventory.contains("Teak plank")) {
            log("State is now BANKING");
            return State.BANKING;

        } else if (!insideHouse.contains(Players.getLocal().getTile()) || !rimmington.contains(Players.getLocal().getTile()))  {
            log("State is now MOVE_TO_HOUSE");
            return State.MOVE_TO_HOUSE;

        } else if (Inventory.count("Teak plank") < 4 && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && Inventory.contains("Mythical cape") && insideHouse.contains(Players.getLocal().getTile())) {
            log("State is now EXIT_HOUSE");
            return State.EXIT_HOUSE;

        } else if (Inventory.count("Teak plank") < 4 && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && Inventory.contains("Mythical cape") && rimmington.contains(Players.getLocal().getTile())) {
            log("State is now UNNOTE_PLANKS");
            return State.UNNOTE_PLANKS;

        } else if (Inventory.count("Teak plank") < 4 && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && Inventory.contains("Mythical cape") && rimmington.contains(Players.getLocal().getTile())) {
            log("State is now RETURN_TO_HOUSE");
            return State.RETURN_TO_HOUSE;

        } else if (Inventory.count("Teak plank") >= 4 && guildTrophySpace != null && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && Inventory.contains("Mythical cape") && insideHouse.contains(Players.getLocal().getTile())) {
            log("State is now MAKE_MYTH_CAPES");
            return State.MAKE_MYTH_CAPES;

        } else if (Inventory.count("Teak plank") >= 4 && mountedMythCape != null && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && insideHouse.contains(Players.getLocal().getTile())) {
            log("State is now REMOVE_MYTH_CAPES");
            return State.REMOVE_MYTH_CAPES;
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
        g.drawString("Magic XP/HR: " + (SkillTracker.getGainedExperiencePerHour(Skill.CONSTRUCTION)), 35, 50);
        g.drawString("Planks made: " + capesMade, 35, 70);
        g.drawString("Planks per hour: " + timer.getHourlyRate(capesMade), 35, 90);
        g.drawString("Timer: " + timer.formatTime(), 35, 110);

        long milliseconds = SkillTracker.getTimeToLevel(Skill.CONSTRUCTION);
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