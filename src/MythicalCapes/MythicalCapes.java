package MythicalCapes;

import Antiban.AntiBan;
import MasterSelector.MasterScript;
import org.dreambot.api.input.Keyboard;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.input.event.impl.keyboard.type.PressKey;
import org.dreambot.api.input.event.impl.mouse.type.ClickEvent;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.Shop;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.depositbox.DepositBox;
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
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.input.event.impl.keyboard.awt.Key;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

import static org.dreambot.api.input.Mouse.click;


@ScriptManifest(name = "Make mythical capes", description = "Make mythical cape mounts in POH", author = "Luten",
        version = 1.0, category = Category.CONSTRUCTION, image = "")
public class MythicalCapes extends AbstractScript {

    //    private final MasterScript masterScript;
    State state;
    Timer timer = new Timer();
    private int capesMade = 0;
    private int previousXP = 0;
    Area rimmington = new Area(2957, 3228, 2943, 3208,0);
    GameObject guildTrophySpace = GameObjects.closest("Guild trophy space");
    GameObject mountedMythCape = GameObjects.closest(31986);
    GameObject portal = GameObjects.closest("Portal");



    AntiBan antiBan = new AntiBan();



    @Override
    public void onStart() {
        log("Script Started");
        SkillTracker.start();
        SkillTracker.start(Skill.CONSTRUCTION);
//        setZoomAndYaw();
        log("Teak planks: ");
        log(Inventory.count("Teak plank"));
//        log("Noted teak plank id: 8781");
//        log(notedTeakPlanks.getNotedItemID());
//        log("Un-Noted teak plank id: 8780");
//        log(notedTeakPlanks.getUnnotedItemID());

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
                //widget 458 -> 7 is the build menu for the cape
                if (guildTrophySpace != null && Widgets.getWidget(458) == null && Players.getLocal().isStandingStill()) { //
                    guildTrophySpace.interact("Build");
                    sleep(450, 700);
                }

                if (Widgets.getWidget(458) != null) {
                    Keyboard.typeKey(Key.FOUR);
                    sleep(1150);
                    checkXPGains();
                }

                sleep(50, 250);
                guildTrophySpace = GameObjects.closest("Guild trophy space");
                mountedMythCape = GameObjects.closest(31986);
                break;


            case REMOVE_MYTH_CAPES:
                if (mountedMythCape != null) {
                    mountedMythCape.interact("Remove");
                    sleep(600,800);
                    Keyboard.typeKey(Key.ONE);
                    sleep(150, 200);
                }

                sleep(150, 300);
                mountedMythCape = GameObjects.closest(31986);
                guildTrophySpace = GameObjects.closest("Guild trophy space");

                break;


            case EXIT_HOUSE:
                if (!Inventory.contains("Mythical cape")) {
                    if (!Players.getLocal().isAnimating() && mountedMythCape != null) {
                        mountedMythCape.interact("Remove");
                        sleep(600,900);
                        Keyboard.typeKey(Key.ONE);
                    }
                }

                portal = GameObjects.closest("Portal");
                if (Inventory.contains("Mythical cape") && portal != null) {
                    portal.interact("Enter");
                    sleepUntil(() -> rimmington.contains(Players.getLocal().getTile()), 10000, 50);
                }

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

                Item notedTeakPlanks = Inventory.get(8781);

                if (NPCs.closest("Phials") != null && notedTeakPlanks != null) {
                    notedTeakPlanks.interact("Use");
                    sleep(150, 400);
                    NPCs.closest("Phials").interact("Use");
                    log("using teaks on phials");
                    sleepUntil(() -> Widgets.getWidget(219) != null, 10000, 50);
                }

                if (Widgets.getWidget(219) != null) {
                    sleep(150, 400);
                    Keyboard.typeKey(Key.THREE);
                    sleep(150, 400);
                    Keyboard.typeKey(Key.SPACE);
                }

                sleep(400,600);
                break;


            case RETURN_TO_HOUSE:
                portal = GameObjects.closest("Portal");
                if (portal != null) {
                    portal.interact("Build mode");
                    log("interacting with portal");
                }
                sleep(5500, 6500);
                mountedMythCape = GameObjects.closest(31986);
                guildTrophySpace = GameObjects.closest("Guild trophy space");
                sleep(antiBan.randomDelayMedium(13));
                sleep(antiBan.randomDelayLong(1));
                break;


            case FAILSAFE:
                mountedMythCape = GameObjects.closest(31986);
                guildTrophySpace = GameObjects.closest("Guild trophy space");
                sleep(200);
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
        int currentXP = Skills.getExperience(Skill.CONSTRUCTION);
        if (currentXP > previousXP) {
            capesMade++;
            previousXP = currentXP;
        }
    }




    //choose saw type - crystal saw or

    private State getState() {
        if (Inventory.count(8780) < 4 && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && Inventory.contains("Mythical cape") && rimmington.contains(Players.getLocal().getTile())) {
            log("State is now UNNOTE_PLANKS");
            return State.UNNOTE_PLANKS;

            //may need to change to check unnoted teaks only

        } else if (Inventory.count(8780) > 4 && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && Inventory.contains("Mythical cape") && rimmington.contains(Players.getLocal().getTile())) {
            log("State is now RETURN_TO_HOUSE");
            return State.RETURN_TO_HOUSE;

        } else if (Inventory.count(8780) >= 4 && guildTrophySpace != null && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && Inventory.contains("Mythical cape")) {
            log("State is now MAKE_MYTH_CAPES");
            return State.MAKE_MYTH_CAPES;

        } else if (Inventory.count(8780) >= 4 && mountedMythCape != null && Inventory.contains("Crystal saw") && Inventory.contains("Hammer")) {
            log("State is now REMOVE_MYTH_CAPES");
            return State.REMOVE_MYTH_CAPES;

        } else if (Inventory.count(8780) < 4 && Inventory.contains("Crystal saw") && Inventory.contains("Hammer") && !rimmington.contains(Players.getLocal().getTile())) {
            log("State is now EXIT_HOUSE");
            return State.EXIT_HOUSE;

//        } else if (!Inventory.contains("Crystal saw") || !Inventory.contains("Hammer") || !Inventory.contains("Mythical cape") || !Inventory.contains("Coins") || !Inventory.contains("Teak plank")) {
//            log("State is now BANKING");
//            return State.BANKING;

//        } else if (!rimmington.contains(Players.getLocal().getTile())) {
//            log("State is now MOVE_TO_HOUSE");
//            return State.MOVE_TO_HOUSE;
        }
        log("State is now FAILSAFE");
        return State.FAILSAFE;
//        log("returning state");
//        return state;
    }

    @Override
    public void onPaint(Graphics g) {
        super.onPaint(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(30, 35, 150, 100);
        g.setFont(new Font("Arial", Font.PLAIN, 12));


        g.setColor(Color.white);
        g.drawString("Construction XP/HR: " + (SkillTracker.getGainedExperiencePerHour(Skill.CONSTRUCTION)), 35, 50);
        g.drawString("Capes made: " + capesMade, 35, 70);
        g.drawString("Capes per hour: " + timer.getHourlyRate(capesMade), 35, 90);
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