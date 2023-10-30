package MortMyrePicker;

import Antiban.AntiBan;
import org.dreambot.api.Client;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.methods.map.Area;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@ScriptManifest(name = "Mort Myre Picker", description = "Collects Mort Myre Fungus", author = "Luten",
        version = 1.0, category = Category.HERBLORE, image = "")
public class MortMyrePicker extends AbstractScript {

    public AntiBan antiBanInstance = new AntiBan(); //imports and uses OpenTabs Anti-ban
    public final Point INVENTORY_WIDGET = new Point(643, 185); // Inventory menu
    private static final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    int CollectedItems = 0;


    @Override
    public void onStart() {
        log("Script Started");
        setZoomLevel();
        antiBanInstance.onStart();
    }

    @Override
    public int onLoop() {
        rechargePrayer();
        verSinhazaTele();
        setYaw();
        moveToFungus();
        hopIfPlayers();
        setPitch();
        pickUpFungus();
        verSinhazaTele();
        depositItems();
        CollectedItems += 28;
        log("CollectedItems: " + CollectedItems);
        return 2500;
    }

    private void setZoomLevel() {
        int zoom = Calculations.random(181, 333);
        log("Setting zoom level");
        Camera.setZoom(zoom);
    }

    private void setPitch() {
        int pitch = Calculations.random(275, 383);
        log("Setting pitch");
        Camera.rotateToPitch(pitch);
        sleep(50, 350);
    }

    private void setYaw() {
        int yaw = Calculations.random(1945, 2047);
        log("Setting yaw");
        Camera.rotateToYaw(yaw);
        sleep(50, 350);
    }

    private void rechargePrayer() {
        int prayerLevel = Skill.PRAYER.getBoostedLevel();
        if (prayerLevel < 40) {
            log("Current prayer level is " + prayerLevel + ", recharging prayer now.");
            Equipment.interact(EquipmentSlot.CAPE, "Kandarin Monastery");
            sleep(1500, 3000);

            if (!Walking.isRunEnabled()) {
                Walking.toggleRun();
                log("Enabling Run");
            }
            Area altarArea = new Area(2605, 3217, 2607, 3210, 0);
            if (!altarArea.contains(Players.getLocal())) {
                Walking.walk(altarArea.getRandomTile());
                log("Moving to altar area");
            }

            GameObject Altar = GameObjects.closest(gameObject -> (gameObject != null) && gameObject.getID() == 409);
            if (Altar != null && Altar.interact("Pray-at")) {
                log("Moving to pray at altar");
//          wait until action completed
                sleepUntil(() -> Players.getLocal().isAnimating(), 5000, 50);
                log("Prayer complete");
                sleep(2800, 3250);
            } else {
                log("Did not pray at the altar");
            }
        }
    }

    private void verSinhazaTele() {
        Equipment.interact(EquipmentSlot.AMULET, "Ver Sinhaza");
        log("Teleporting to Ver Sinhaza");
        sleep(2000, 2890);
    }

    private void moveToFungus() {
        if (!Walking.isRunEnabled()) {
            Walking.toggleRun();
            log("Enabling Run");
        }

        Area halfwayToFungusArea = new Area(3662, 3240, 3660, 3247, 0);
        Area fungusArea = new Area(3666, 3258, 3669, 3252, 0);
        Tile fungusTile = new Tile(3667, 3255, 0);

        if (Players.getLocal().distance(fungusTile) > 5) {
            Walking.walk(halfwayToFungusArea.getRandomTile());
            log("Moving to first fungus area");
//            sleep(8700,10000);
            sleep(1200, 1800);
            sleepUntil(() -> Players.getLocal().isStandingStill(), 9000, 50);
            log("Arrived at first fungus area");


            Walking.walk(fungusArea.getRandomTile());
            log("Moving to fungus area");
//            sleep(8000,10000);
            sleep(1200, 1800);
            sleepUntil(() -> Players.getLocal().isStandingStill() && !Players.getLocal().isMoving(), 9000, 50);
            log("Arrived at fungus area");

            if (!fungusArea.contains(Players.getLocal())) {
                Walking.walk(fungusArea.getRandomTile());
                log("Moving closer fungus area");
                sleep(1200, 1800);
                sleepUntil(() -> Players.getLocal().isStandingStill() && !Players.getLocal().isMoving(), 5000, 50);
                log("Arrived closer fungus area");
            }
        }

        Walking.walkExact(fungusTile);
        log("Walking to Fungus");
        sleepUntil(() -> !Players.getLocal().isMoving(), 2000, 50);
        log("Arrived at Fungus");
    }

    private void hopIfPlayers() {
//      If players within 5 tiles of you then hop to new p2p world
        if (Players.closest((p -> p != null && !p.equals(Players.getLocal()) && p.distance() < 5)) != null) {
            World world = Worlds.getRandomWorld(w -> w.isMembers() && w.isNormal());
            WorldHopper.hopWorld(world);
            log("Hopping worlds");
            sleep(6000, 10000);
        }
    }

    private void interactWithFungus() {
        GameObject MortMyreFungus = GameObjects.closest(gameObject -> (gameObject != null) && gameObject.getID() == 3509);

        //Stop the recursion
        if (MortMyreFungus == null || Inventory.isFull()) {
            return;
        }

        MortMyreFungus.interact("Pick");
        log("Picking up Mort Myre Fungus");
//        sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), 2500, 50); doesnt work
//        log("Picked up Mort Myre Fungus");
        sleep(1800, 2400);

        isLoggedIn();

        interactWithFungus();
    }

    private void pickUpFungus() {
        int prayerLevel = Skill.PRAYER.getBoostedLevel();
        if (prayerLevel <= 3 || Inventory.isFull()) {
            log("Inventory Full or Prayer level is too low");
            antiBanInstance.openInventory();
            return;
        }

        log("Current Prayer level is: " + prayerLevel);
        Equipment.interact(EquipmentSlot.WEAPON, "Bloom");
        log("Bloom");
        sleep(2000, 3000);
//        sleepUntil(() -> !Players.getLocal().isAnimating(), 2500, 50);
        log("Finished Bloom Animation");


        isLoggedIn();

        interactWithFungus();

        moveToFungus();

        pickUpFungus();

    }

    private void depositItems() {
        Area halfwayToBank = new Area (3653, 3221, 3656, 3216, 0);
        Area bankArea = new Area(3652, 3208, 3649, 3210, 0);

        Walking.walk(halfwayToBank.getRandomTile());
        Sleep.sleepUntil(() -> Players.getLocal().isStandingStill(), 5000);

        Walking.walk(bankArea.getRandomTile());
        Sleep.sleepUntil(() -> Players.getLocal().isStandingStill(), 5000);

        if (!Bank.isOpen() && !Players.getLocal().isMoving()) {
            GameObject bankBooth = GameObjects.closest("Bank booth");
            bankBooth.interact("Bank");
            Sleep.sleepUntil(Bank::isOpen, 15000);
        }
        Bank.depositAllItems();
        Sleep.sleepUntil(Inventory::isEmpty, 2000);
        sleep(400, 800);
        if (Inventory.isEmpty()) {
            Bank.close();
        }
    }


    //Check if player is logged in. If not then stop script loop
    public void isLoggedIn() {
        if (Client.getGameState().equals(GameState.LOGIN_SCREEN)) {
            stop();
            log("Logged out.");
            super.onExit();
        }
    }

    public static int randomBetween(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void onExit() {
        scheduler.shutdownNow();
        super.onExit();
    }

    @Override
    public void onPaint(Graphics graphics) {
        super.onPaint(graphics);
    }
}




