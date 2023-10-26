package BuyAndCrushChocolate;


import Antiban.OpenTabs;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.Shop;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skills;
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

import java.time.Period;
import java.util.Random;

@ScriptManifest(name = "Buy And Crush Chocolate", description = "Buys and crushes chocolate from Culinaromancer's Chest.", author = "Zeb",
        version = 1.0, category = Category.HERBLORE, image = "")


public class BuyAndCrushChocolate extends AbstractScript {

//    public OpenTabs openTabsInstance = new OpenTabs(); //imports and uses OpenTabs Anti-ban
    private static final Random random = new Random();
    Area mageBank = new Area(2531, 4711, 2545, 4721, 0);
    int chocolateCount = 0;

    @Override
    public void onStart() {
        log("Script Started");
        setZoomLevel();
//        openTabsInstance.onStart();
    }

    @Override
    public int onLoop() {
//        if (chocolateCount < 1000) {
//            log("Starting purchase cycle");
//            withdrawCoins();
//            openFoodChest();
//            purchaseChocolate();
//        } else {
            log("Starting crush cycle");
            depositCoins();
            inventorySetup();
            withdrawItems();
            crushChocolate();
            depositDust();
            if(!Bank.contains("Chocolate bar")) {
            chocolateCount = 0;
            }
//        }

        log("looped");
        return 500;
    }

    private void setZoomLevel() {
        int zoom = Calculations.random(350, 650);
        log("Setting zoom level");
        Camera.setZoom(zoom);
    }

    private void setPitch() {
        int pitch = Calculations.random(274, 347);
        log("Setting pitch");
        Camera.rotateToPitch(pitch);
        sleep(50, 350);
    }

    private void setYaw() {
        int yaw = Calculations.random(940, 1104);
        log("Setting yaw");
        Camera.rotateToYaw(yaw);
        sleep(50, 350);
    }

    private void withdrawCoins() {
        if(!Inventory.contains("Coins")) {

            Walking.walk(Bank.getClosestBankLocation());
            log("Walking to bank");
            sleepUntil(() -> Players.getLocal().isStandingStill(), 30000, 50);

            if (!Bank.isOpen() && Players.getLocal().isStandingStill()) {
                Bank.open();
                log("Opening Bank");
                Sleep.sleepUntil(Bank::isOpen, 20000);
            }

            if (!Inventory.isEmpty()) {
                Bank.depositAllItems();
                log("Depositing All Items");
                Sleep.sleepUntil(Inventory::isEmpty, 2000);
            }

            sleep(600, 800);

            if (Inventory.isEmpty()) {
                boolean hasWithdrawnCoins = false;

                if (Bank.contains("Coins")) {
                    Bank.withdrawAll("Coins");
                    sleep(1600, 2100);
                }

                if (Inventory.contains("Coins")) {
                    hasWithdrawnCoins = true;
                }

                if (!hasWithdrawnCoins) {
                    log("Failed to withdraw slash item.");
                    stop();
                }
                Bank.close();
            }
        }
    }

    private void openFoodChest() {
        GameObject chest = GameObjects.closest("Chest");
        if (chest != null && chest.canReach()) {
            log("Opening Food Chest");
            chest.interact("Buy-food");
            sleep(1000, 2000);
        }
    }

    private void purchaseChocolate() {
        if (Shop.isOpen()) {
            log("shop is open");
            log(Shop.count("Chocolate bar"));
        }

        if (Shop.isOpen() && !Inventory.isFull() && Shop.count("Chocolate bar") > 3) {
            Shop.purchaseFifty("Chocolate bar");
            sleep(800, 1400);
            purchaseChocolate();
        } else if (Inventory.isFull()) {
            depositChocolate();
            log("Collected " + (chocolateCount += 27) + " chocolate");
        } else if (Shop.count("Chocolate bar") < 5) {
            hopWorlds();
        }
        sleep(600, 1200);
    }

    private void depositChocolate() {
        if (Inventory.isFull()) {
            Bank.open();
            log("Opening Bank");
            Sleep.sleepUntil(Bank::isOpen, 15000);

            Bank.depositAll("Chocolate bar");
            log("Depositing 27 Chocolate bars");
            Sleep.sleepUntil(Inventory::isEmpty, 2000);
        }
        Bank.close();
        sleep(100, 350);
    }

    private void hopWorlds() {
        World world = Worlds.getRandomWorld(w -> w.isMembers() && w.isNormal() && !w.isHighRisk() && !w.isDeadmanMode() && Skills.getTotalLevel() >= w.getMinimumLevel() && !w.isPVP() && w.getPopulation() < 1000);
        WorldHopper.hopWorld(world.getWorld());
        log("Hopping to world " + world.getWorld());
        sleep(5000, 6000);
    }

    private void depositCoins() {
        if(Inventory.contains("Coins")) {
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
            sleep(600, 800);
        }
    }

    private void inventorySetup() {
        if (!Inventory.contains("Pestle and mortar")) {
            boolean hasWithdrawnPestleAndMortar = false;
            Bank.open();
            log("Opening Bank");
            Sleep.sleepUntil(Bank::isOpen, 15000);

            if (Bank.contains("Pestle and mortar")) {
                Bank.withdraw("Pestle and mortar", 1);
                sleep(1600, 2100);
            }

            if (Inventory.contains("Pestle and mortar")) {
                hasWithdrawnPestleAndMortar = true;
            }

            if (!hasWithdrawnPestleAndMortar) {
                log("Failed to withdraw Pestle and mortar.");
                stop();
            }
            Bank.close();
            sleep(600, 1200);
            Mouse.move(Inventory.slotBounds(0));
            Mouse.drag(Inventory.slotBounds(27));
        }

    }

    private void withdrawItems() {
        if (!Bank.isOpen() && Players.getLocal().isStandingStill()) {
            Bank.open();
            log("Opening Bank to withdraw chocolate bars");
            Sleep.sleepUntil(Bank::isOpen, 15000);
        }
        sleep(600, 800);

        if(!Inventory.contains("Chocolate bar")) {
            boolean hasWithdrawnChocolateBar = false;

            if (Bank.contains("Chocolate bar")) {
                Bank.withdrawAll("Chocolate bar");
                sleep(1600, 2100);
            }

            if (Inventory.contains("Chocolate bar")) {
                hasWithdrawnChocolateBar = true;
            }

            if (!hasWithdrawnChocolateBar) {
                log("Failed to withdraw chocolate bar.");
                log("Setting chocolate count to 0. Buying more chocolate bars.");
                chocolateCount = 0;
            }
        }
        Bank.close();
    }


    private void crushChocolate() {
        if (Inventory.contains("Pestle and mortar") && Inventory.contains("Chocolate bar")) {
            Inventory.interact("Pestle and mortar", "use");
            sleep(70, 180);
            Inventory.slotInteract(26);
            if (Inventory.isSlotEmpty(26)) {
                Inventory.slotInteract(25);
            } else if (Inventory.isSlotEmpty(25)){
                Inventory.slotInteract(24);
            }
            sleep(70, 180);
            crushChocolate();

        }
    }

    private void depositDust() {
        Bank.open();
        log("Opening Bank to deposit dust");
        Sleep.sleepUntil(Bank::isOpen, 15000);

        Bank.depositAll("Chocolate dust");
        log("Depositing 27 Chocolate dust");
        Sleep.sleepUntil(Inventory::isEmpty, 2000);

    }

    public static int randomBetween(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void onExit() {
        super.onExit();
    }
}



