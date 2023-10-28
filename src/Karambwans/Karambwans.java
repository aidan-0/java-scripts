package Karambwans;

import Antiban.OpenTabs;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.depositbox.DepositBox;
import org.dreambot.api.methods.fairyring.FairyLocation;
import org.dreambot.api.methods.fairyring.FairyRings;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.awt.*;

@ScriptManifest(name = "Karambwan Fisher", description = "Fish and bank Karambwans", author = "Luten",
        version = 1.0, category = Category.FISHING, image = "")
public class Karambwans extends AbstractScript {
    State state;
    public OpenTabs openTabsInstance = new OpenTabs(); //imports and uses OpenTabs Anti-ban

    Area gemMine = new Area(2847, 9382, 2830, 9393, 0);
    Area fishingArea = new Area(2896, 3121, 2903, 3111, 0);
    Area legendsGuildFairyRing = new Area(2745, 3355, 2722, 3342);
    Tile fishingTile = new Tile(2899, 3118, 0);
    int totalRawKarambwan = 0;
    Timer timer = new Timer();



    @Override
    public void onStart() {
        log("Script Started");
        openTabsInstance.onStart();
        SkillTracker.start();
        SkillTracker.start(Skill.FISHING);
    }


    @Override
    public int onLoop() {
        switch (getState()) {
            case GEARING:
                if (!Players.getLocal().isMoving()) {
                    Walking.walk(Bank.getClosestBankLocation());
                    log(Bank.getClosestBankLocation().distance(Bank.getClosestBankLocation().getCenter()));
                }
                if (!Bank.isOpen() && Players.getLocal().isStandingStill()) {
                    Bank.open();
                    log("Opening Bank");
                    Sleep.sleepUntil(Bank::isOpen, 15000);
                }

                if (!Inventory.isEmpty()) {
                    Bank.depositAllItems();
                    log("Depositing All Items");
                    Sleep.sleepUntil(Inventory::isEmpty, 2000);
                }

                if (!Equipment.isEmpty()) {
                    Bank.depositAllEquipment();
                    log("Depositing All Equipment");
                    Sleep.sleepUntil(Equipment::isEmpty, 2000);
                }
                sleep(600, 800);

                if (Inventory.isEmpty()) {
                    boolean hasWithdrawnKaramjaGloves = false;
                    int successfulWithdrawKaramjaGloves = -1;

                    for (int i = 1; i <= 6; i++) {
                        if (Bank.contains("Karamja gloves(" + i + ")")) {
                            Bank.withdraw("Karamja gloves(" + i + ")", 1);
                            log("Withdrawing Karamja gloves(" + i + ")");
                            sleep(1200, 1800);

                            if (Inventory.contains("Karamja gloves(" + i + ")")) {
                                hasWithdrawnKaramjaGloves = true;
                                successfulWithdrawKaramjaGloves = i;
                                break;
                            } else {
                                log("Failed to withdraw Karamja gloves");
                                stop();
                            }
                        }
                    }

                    if (Bank.contains("Quest point cape")) {
                        Bank.withdraw("Quest point cape");
                        log("Withdrawing Quest point cape");
                        sleep(1600, 2100);
                    } else {
                        log("Failed to withdraw Quest point cape");
                        stop();
                    }

                    if (Bank.contains("Dramen staff")) {
                        Bank.withdraw("Dramen staff");
                        log("Withdrawing Dramen staff");
                        sleep(1200, 1800);
                    } else {
                        log("Failed to withdraw Dramen staff");
                        stop();
                    }

                    //Equipping items
                    if (hasWithdrawnKaramjaGloves) {
                        Inventory.interact("Karamja gloves(" + successfulWithdrawKaramjaGloves + ")", "Wear");
                        log("Equipping Karamja gloves");
                        sleep(800, 1300);
                    }

                    if (Inventory.contains("Quest point cape")) {
                        Inventory.interact("Quest point cape", "Wear");
                        log("Equipping Quest point cape");
                        sleep(800, 1300);
                    } else {
                        log("Failed to withdraw ");
                        stop();
                    }

                    if (Inventory.contains("Dramen staff")) {
                        Inventory.interact("Dramen staff", "Wield");
                        log("Equipping Dramen staff");
                        sleep(800, 1300);
                    }

                    //Withdrawing Inventory Items
                    if (Bank.contains("Raw karambwanji")) {
                        Bank.withdrawAll("Raw karambwanji");
                        log("Withdrawing Raw karambwanji");
                        sleep(1200, 1800);
                    } else {
                        log("Failed to withdraw Raw karambwanji");
                        stop();
                    }

                    if (Bank.contains("Karambwan vessel")) {
                        Bank.withdraw("Karambwan vessel");
                        log("Withdrawing Karambwan vessel");
                        sleep(1200, 1800);
                    } else {
                        log("Failed to withdraw Karambwan vessel");
                        stop();
                    }

                    if (Bank.contains("Fish barrel")) {
                        Bank.withdraw("Fish barrel");
                        log("Withdrawing Fish barrel");
                        sleep(1200, 1800);
                    } else {
                        log("Failed to withdraw Fish barrel");
                        stop();
                    }
                    Bank.close();
                }
                break;

            case MOVE_TO_BANK_TO_DEPOSIT_ITEMS:
                Equipment.interact(EquipmentSlot.HANDS, "Gem Mine");
                sleep(4000, 5000);
                break;

            case OPEN_BANK:
                if (!Bank.isOpen() || Players.getLocal().isStandingStill()) {
                    GameObject bankDepositChest = GameObjects.closest("Bank Deposit Chest");
                    bankDepositChest.interact("Deposit");
                    sleep(2600, 3400);
                }
                break;

            case BANKING:
                if (DepositBox.get("Open fish barrel") != null) {
                    if (Widgets.getWidget(192).getChild(2).getChild(2) != null) {
                        sleep(600, 1250);

                        Widgets.getWidget(192).getChild(2).getChild(2).interact("Empty");
                    }
                    log("Depositing from fishing barrel");
                    totalRawKarambwan += 28;
                    sleep(300, 1000);
                }

                DepositBox.depositAll("Raw karambwan");
                log("Depositing from inventory");
                Sleep.sleepUntil(() -> !Inventory.contains("Raw karambwan"), 2000);
                if (!Inventory.contains("Raw karambwan")) {
                    DepositBox.close();
                    totalRawKarambwan += 28;
                    log("Total Raw Karambwan banked: " + totalRawKarambwan);
                } else {
                    stop();
                }
                break;

            case MOVE_TO_FISHING_AREA:
                if (!legendsGuildFairyRing.contains(Players.getLocal().getTile())) {
                    Equipment.interact(EquipmentSlot.CAPE, "Teleport");
                    sleep(2000, 3000);
                }

                GameObject fairyRing = GameObjects.closest("Fairy ring");
                if (fairyRing != null && !fishingArea.contains(Players.getLocal().getTile())) {
                    log("interacting with fairy ring");
                    fairyRing.interact("Configure");
                    sleep(4000, 6000);
                    FairyRings.travel(FairyLocation.KARAMJA_SOUTH_MUSA_POINT);
                    sleep(3000, 5000);
                    break;
                }

                break;

            case FISHING:
                if (!Players.getLocal().isAnimating()) {
                    if (!Players.getLocal().getTile().equals(fishingTile)) {
                        Walking.walkExact(fishingTile);
                        sleep(1000,2000);
                    }
                    NPCs.closest(4712).interact("Fish");
                    sleep(1000, 2000);
                    openTabsInstance.openInventory();
                    sleep(200,800);
                    Mouse.move(new Point(800, Calculations.random(0,502))); //Move mouse off the screen to the right
                }
                sleep(1000,15000);
                break;
        }
        return 1;
    }

    private State getState() {
        if (Inventory.isEmpty() || Equipment.isEmpty() || !Inventory.contains("Karambwan vessel") || !Inventory.contains("Raw Karambwanji")) {
//            log("State is now GEARING");
            return State.GEARING;

        } else if (Inventory.isFull() && !gemMine.contains(Players.getLocal().getTile())) {
//            log("State is now MOVE TO BANK");
            return State.MOVE_TO_BANK_TO_DEPOSIT_ITEMS;

        } else if (Inventory.isFull() && gemMine.contains(Players.getLocal().getTile()) && !DepositBox.isOpen()) {
//            log("State is now OPEN BANK");
            return State.OPEN_BANK;

        } else if (DepositBox.isOpen() && Inventory.isFull()) {
//            log("State is now BANKING");
            return State.BANKING;

        } else if (!Inventory.isFull() && !fishingArea.contains(Players.getLocal().getTile())) {
//            log("State is now MOVE TO FISHING AREA");
            return State.MOVE_TO_FISHING_AREA;

        } else if (!Inventory.isFull() && fishingArea.contains(Players.getLocal().getTile())) {
//            log("State is now FISHING");
            return State.FISHING;
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
        g.drawString("Fishing XP/HR: " + (SkillTracker.getGainedExperiencePerHour(Skill.FISHING)), 35, 50);
        g.drawString("Fish Banked: " + totalRawKarambwan, 35, 70);
        g.drawString("Fish per hour: " + timer.getHourlyRate(totalRawKarambwan), 35, 90);
        g.drawString("Timer: " + timer.formatTime(), 35, 110);

        long milliseconds = SkillTracker.getTimeToLevel(Skill.FISHING);
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


