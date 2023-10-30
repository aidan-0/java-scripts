package ZammyWineStealer;

import Antiban.AntiBan;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Tile;
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
import org.dreambot.api.wrappers.items.GroundItem;

import java.util.Random;

@ScriptManifest(name = "Zammy Wine Stealer", description = "Steals wine of Zamorak from the deep wilderness.", author = "Luten",
        version = 1.0, category = Category.HERBLORE, image = "")


public class ZammyWineStealer extends AbstractScript {

    public AntiBan antiBanInstance = new AntiBan(); //imports and uses OpenTabs Anti-ban
    private static final Random random = new Random();
    Area mageBank = new Area(2531, 4711, 2545, 4721, 0);

    @Override
    public void onStart() {
        log("Script Started");
        setZoomLevel();
        enableRun();
//        turn auto attack off if its on
        antiBanInstance.onStart();
    }

    @Override
    public int onLoop() {
        if (!mageBank.contains(Players.getLocal())) {
           findBank();
           edgevilleTele();
           edgevilleLever();
           mageBankFromEdgeville();
        }

        gearingUp();
        enableRun();
        mageBankToWineOfZamorak();
        setPitch();
        setYaw();
//        settingMouse();
        stealingWines();
        enableRun();
        returnToMageBank();


//      figure out why ive been dying - if its players or not
//        if you die then break out of loop and start from the beginning
        log("looped");
        return 2500;
    }

    private void setZoomLevel() {
        int zoom = Calculations.random(289, 440);
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

    private void findBank() {
        Walking.walk(Bank.getClosestBankLocation());
        log("Walking to bank");
        sleepUntil(() -> Players.getLocal().isStandingStill(), 30000, 50);

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
            boolean hasWithdrawnGlory = false;
            boolean hasWithdrawnWildernessSword = false;
            boolean hasWithdrawnKnife = false;
            int successfulWithdrawGlory = -1;
            int successfulWithdrawSword = -1;

            for (int i = 1; i <= 6; i++) {
                if (Bank.contains("Amulet of glory(" + i + ")")) {
                    Bank.withdraw("Amulet of glory(" + i + ")", 1);
                    sleep(1200, 1800);

                    if (Inventory.contains("Amulet of glory(" + i + ")")) {
                        hasWithdrawnGlory = true;
                        successfulWithdrawGlory = i;
                        break;
                    }
                }
            }

            if (hasWithdrawnGlory) {
                Inventory.interact("Amulet of glory(" + successfulWithdrawGlory + ")", "Wear");
                log("Equipping glory");
            } else {
                // Handle the case where no amulet of glory(1-6) was found or could not be withdrawn.
                log("Failed to withdraw Amulet of glory with charges 1-6.");
                stop();
            }


            for (int i = 1; i <= 4; i++) {
                if (Bank.contains("Wilderness sword(" + i + ")")) {
                    Bank.withdraw("Wilderness sword(" + i + ")", 1);
                    sleep(1200, 1800);

                    if (Inventory.contains("Wilderness sword(" + i + ")")) {
                        hasWithdrawnWildernessSword = true;
                        successfulWithdrawSword = i;
                        break;
                    }
                }
            }

            if (hasWithdrawnWildernessSword) {
                Inventory.interact("Wilderness sword(" + successfulWithdrawSword + ")", "Wear");
                log("Equipping Wilderness Sword");
            } else if
            (Bank.contains("Knife")) {
                Bank.withdraw("Knife");
                sleep(1600, 2100);
            }

            if (Inventory.contains("Knife")) {
                hasWithdrawnKnife = true;
            }


            if (!hasWithdrawnKnife || !hasWithdrawnWildernessSword) {
                log("Failed to withdraw slash item.");
                stop();
            }


            Bank.close();
        }
    }

    private void edgevilleTele() {
        log(Bank.getClosestBankLocation());
        // if closest bank is edgeville dont use tele
        Equipment.interact(EquipmentSlot.AMULET, "Edgeville");
        log("Teleporting to Edgeville");
        sleep(2000, 2890);
    }

    private void edgevilleLever() {
        Area closerToEdgevilleLever = new Area(3089, 3487, 3094, 3483, 0);
        Tile edgevilleLeverTile = new Tile(3090, 3475, 0);

        Walking.walk(closerToEdgevilleLever.getRandomTile());
        log("Walking closer to lever");
        sleepUntil(() -> Players.getLocal().isStandingStill(), 9000, 50);

        GameObject lever = GameObjects.closest("Lever");
        if (lever != null && lever.canReach()) {
            log("Moving to edgeville lever");
            lever.interact("Pull");
            sleepUntil(() -> Players.getLocal().getTile().distance(edgevilleLeverTile) < 2, 10000, 50);
            log("Pulled edgeville lever");
        }
        sleep(2000, 3000);
    }

    Tile web2Tile = new Tile(3095, 3957, 0);
    Tile web3Tile = new Tile(3092, 3957, 0);

    private void mageBankFromEdgeville() {
        Tile web1Tile = new Tile(3158, 3951, 0);
        Area halfwayToFirstWeb = new Area(3160, 3939, 3155, 3943, 0);
        Area nextToFirstWeb = new Area(3160, 3945, 3157, 3948, 0);

        Walking.walk(halfwayToFirstWeb.getRandomTile());
        log("Walking halfway to Web 1 Tile");
        sleep(4000, 6000);
        log("Arrived halfway to Web 1 Tile");

        Walking.walk(nextToFirstWeb.getRandomTile());
        log("Walking next to Web 1 Tile");
        sleep(4000, 6000);
        log("Arrived next to Web 1 Tile");

        while (isWebPresent(web1Tile)) {
            log("Slashing Web 1");
            slashWeb(web1Tile);
            sleep(1200, 1800);
        }

        log("Starting mage bank walk");
        Area[] area = {
                new Area(3160, 3956, 3156, 3959),
                new Area(3151, 3959, 3148, 3961),
                new Area(3145, 3954, 3142, 3956),
                new Area(3136, 3952, 3132, 3954),
                new Area(3128, 3950, 3125, 3952),
                new Area(3121, 3954, 3119, 3956),
                new Area(3115, 3958, 3112, 3961),
                new Area(3107, 3960, 3104, 3962),
                new Area(3100, 3956, 3097, 3959)
        };

        for (Area currentArea : area) {
            Tile randomTile = currentArea.getRandomTile();

            while (!currentArea.contains(Players.getLocal())) {
                Walking.walk(randomTile);
                log("Walking to random tile in current area");
                sleep(1200, 2000);
            }
        }
        log("Arrived outside mage bank");

        while (isWebPresent(web2Tile)) {
            log("Slashing web 2");
            slashWeb(web2Tile);
            sleep(1200, 1800);
        }

        while (isWebPresent(web3Tile)) {
            log("Slashing web 3");
            slashWeb(web3Tile);
            sleep(1200, 1800);
        }

        GameObject lever = GameObjects.closest("Lever");
        if (lever != null) {
            lever.interact("Pull");
            sleep(2000, 3000);
        }
    }

    private boolean isWebPresent(Tile tile) {
        GameObject[] gameObjects = GameObjects.getObjectsOnTile(tile);
        for (GameObject obj : gameObjects) {
            if (obj.getName().equals("Web")) {
                return true;
            }
        }
        return false;
    }

    private void slashWeb(Tile tile) {
        GameObject[] gameObjects = GameObjects.getObjectsOnTile(tile);
        for (GameObject obj : gameObjects) {
            if (obj.getName().equals("Web")) {
                obj.interact("Slash");
                return;
            }
        }
    }

    private void gearingUp() {
        Walking.walk(Bank.getClosestBankLocation());
        log("Walking to bank");
        sleepUntil(() -> Players.getLocal().isStandingStill(), 5000, 50);

        if (!Bank.isOpen() && Players.getLocal().isStandingStill()) {
            Bank.open();
            log("Opening Bank");
            Sleep.sleepUntil(Bank::isOpen, 5000);
        }

        if (!Inventory.isEmpty()) {
            Bank.depositAllItems();
            log("Depositing All Items");
            Sleep.sleepUntil(Inventory::isEmpty, 2000);
        }

        if (!Equipment.isEmpty()) {
            Bank.depositAllEquipment();
            log("Depositing Glory");
            Sleep.sleepUntil(Equipment::isEmpty, 2000);
        }

        sleep(600, 800);

        if (Inventory.isEmpty()) {
            boolean hasWithdrawnAirRune = false;
            boolean hasWithdrawnLawRune = false;
            boolean hasWithdrawnKnife = false;

            if (Bank.contains("Air rune")) {
                Bank.withdraw("Air rune", 200);
                sleep(1200, 1800);

                if (Inventory.contains("Air rune")) {
                    hasWithdrawnAirRune = true;
                }
            }

            if (!hasWithdrawnAirRune) {
                log("Failed to withdraw Air rune.");
                stop();
            }

            if (Bank.contains("Law rune")) {
                Bank.withdraw("Law rune", 200);
                sleep(1200, 1800);

                if (Inventory.contains("Law Rune")) {
                    hasWithdrawnLawRune = true;
                }
            }

            if (!hasWithdrawnLawRune) {
                log("Failed to withdraw Law rune.");
                stop();
            }

            if (Bank.contains("Knife")) {
                Bank.withdraw("Knife");
                sleep(1600, 2100);

                if (Inventory.contains("Knife")) {
                    hasWithdrawnKnife = true;
                }
            }

            if (!hasWithdrawnKnife) {
                log("Failed to withdraw Knife.");
                stop();
            }

            Bank.close();
        }
    }

    private void mageBankToWineOfZamorak() {
        GameObject lever = GameObjects.closest("Lever");
        if (lever != null) {
            lever.interact("Pull");
            sleep(6500, 7800);
        }

        while (isWebPresent(web3Tile)) {
            log("Slashing web 3");
            slashWeb(web3Tile);
            sleep(1200, 1800);
        }

        while (isWebPresent(web2Tile)) {
            log("Slashing web 2");
            slashWeb(web2Tile);
            sleep(1200, 1800);
        }

        log("Starting walk to zamorak wines");
        Area[] area = {
                new Area(3092, 3960, 3089, 3962),
                new Area(3083, 3958, 3081, 3956),
                new Area(3078, 3952, 3075, 3950),
                new Area(3071, 3945, 3068, 3942),
                new Area(3062, 3938, 3059, 3935),
                new Area(3052, 3934, 3048, 3931),
                new Area(3049, 3927, 3046, 3923)
        };

        for (Area currentArea : area) {
            Tile randomTile = currentArea.getRandomTile();

            while (!currentArea.contains(Players.getLocal())) {
                Walking.walk(randomTile);
                log("Walking to random tile in current area");
                hopIfPlayers();
                sleep(1000, 1600);
            }
        }
        log("Arrived outside zamorak wine cave");


        GameObject stairs = GameObjects.closest("Staircase");
        if (stairs != null) {
            stairs.interact("Climb-down");
            sleep(5000, 6000);
        }

        GameObject crevice = GameObjects.closest("Crevice");
        if (crevice != null) {
            crevice.interact("Use");
            log("crevice use");
            sleep(6000, 8000);
        }


        Area[] deepWildyDungeon = {
                new Area(3056, 10334, 3058, 10332),
                new Area(3065, 10338, 3068, 10336),
                new Area(3065, 10346, 3063, 10349),
                new Area(3057, 10355, 3055, 10358),
                new Area(3042, 10361, 3045, 10359),
                new Area(3034, 10354, 3031, 10352),
                new Area(3019, 10354, 3020, 10352)
        };

        for (Area currentArea : deepWildyDungeon) {
            Tile randomTile = currentArea.getRandomTile();

            while (!currentArea.contains(Players.getLocal())) {
                Walking.walk(randomTile);
                log("Walking to random tile in current area");
                hopIfPlayers();
                sleep(1000, 1600);
            }
        }
    }

    private void stealingWines() {
        GroundItem wine = GroundItems.closest(groundItem -> groundItem != null && groundItem.getID() == 23489);

        if (wine != null) {
            Magic.castSpellOn(Normal.TELEKINETIC_GRAB, wine);
            log("casting telegrab");
            sleep(200, 400);
            Magic.interact(Normal.TELEKINETIC_GRAB, "cast");
//            sleep(200, 400);
//            x += randomBetween(-5, 5);
//            y += randomBetween(-5, 5);
//            Mouse.move(new Point(x, y));
            sleep(400, 900);
        }

        if (!Inventory.contains("Air rune") || !Inventory.contains("Law rune")) {
            log("Out of runes, returning to bank");
            return;
        }

        log("looping stealing wines");
        hopIfPlayers();
//        if (hopIfPlayers()) {
//            settingMouse();
//        }
        sleep(500, 1000);

        stealingWines();

    }

    private void hopIfPlayers() {
        if (Players.closest(p -> p != null && !p.equals(Players.getLocal()) && p.distance() < 20) != null) {
            World world = Worlds.getRandomWorld(w -> w.isMembers() && w.isNormal() && !w.isHighRisk() && !w.isDeadmanMode() && Skills.getTotalLevel() >= w.getMinimumLevel() && !w.isPVP() && w.getPopulation() < 1000);

            WorldHopper.quickHop(world.getWorld());
            log("Hopping to world " + world.getWorld());
            sleep(2000, 3000);
            log("Hop if players returns true");
//            return true;
        }
        log("Hop if players returns false");
//        return false;
    }

    private void returnToMageBank() {
        Area[] deepWildyDungeon = {
                new Area(3020, 10354, 3024, 10350),
                new Area(3034, 10354, 3031, 10352),
                new Area(3042, 10361, 3045, 10359),
                new Area(3057, 10355, 3055, 10358),
                new Area(3065, 10346, 3063, 10349),
                new Area(3065, 10338, 3068, 10336),
                new Area(3056, 10334, 3058, 10332),
                new Area(3052, 10338, 3050, 10337)
        };

        for (Area currentArea : deepWildyDungeon) {
            Tile randomTile = currentArea.getRandomTile();

            while (!currentArea.contains(Players.getLocal())) {
                Walking.walk(randomTile);
                log("Walking to random tile in current area");
                hopIfPlayers();
                sleep(1000, 1700);
            }
        }

        GameObject crevice = GameObjects.closest("Crevice");
        if (crevice != null) {
            crevice.interact("Use");
            sleep(6000, 8000);
        }


        GameObject stairs = GameObjects.closest("Staircase");
        if (stairs != null) {
            stairs.interact("Climb-up");
            sleep(4000, 5000);
        }

        Area[] area = {
                new Area(3052, 3934, 3048, 3931),
                new Area(3062, 3938, 3059, 3935),
                new Area(3071, 3945, 3068, 3942),
                new Area(3078, 3952, 3075, 3950),
                new Area(3083, 3958, 3081, 3956),
                new Area(3092, 3960, 3089, 3962)
        };

        for (Area currentArea : area) {
            Tile randomTile = currentArea.getRandomTile();

            while (!currentArea.contains(Players.getLocal())) {
                Walking.walk(randomTile);
                log("Walking to random tile in current area");
                sleep(1000, 1700);
                hopIfPlayers();
            }
        }

        while (isWebPresent(web2Tile)) {
            log("Slashing web 2");
            slashWeb(web2Tile);
            hopIfPlayers();
            sleep(1200, 1800);
        }

        while (isWebPresent(web3Tile)) {
            log("Slashing web 3");
            slashWeb(web3Tile);
            hopIfPlayers();
            sleep(1200, 1800);
        }

        GameObject lever = GameObjects.closest("Lever");
        if (lever != null) {
            lever.interact("Pull");
            sleep(6500, 7800);
        }
    }



    public void enableRun() {
        if (!Walking.isRunEnabled()) {
            Walking.toggleRun();
            log("Enabling Run");
        }
    }

    @Override
    public void onExit() {
        super.onExit();
    }

//    @Override
//    public void onPaint(Graphics g) {
//        g.setColor(new Color(62, 32, 87));
////        g.
//    }
}



