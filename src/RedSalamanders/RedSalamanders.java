package RedSalamanders;

import Antiban.OpenTabs;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.methods.map.Area;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@ScriptManifest(name = "Red Salamanders", description = "Catches Red Salamanders by Orania Altar!", author = "Zeb",
        version = 1.0, category = Category.HUNTING, image = "")
public class RedSalamanders extends AbstractScript {

    public OpenTabs openTabsInstance = new OpenTabs(); //imports and uses OpenTabs Anti-ban
    Area redSalamanderArea = new Area(2446, 3230, 2455, 3219, 0); //Sets Red Salamander Area
    public final Point INVENTORY_WIDGET = new Point(643, 185); // Inventory menu
    private static final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Override
    public void onStart() {
        log("Script Started");
        openTabsInstance.onStart();
        Tabs.openWithFKey(Tab.INVENTORY);
        scheduleRest();
    }

    private void scheduleRest() {
        long delay = (13 + random.nextInt(16)) * 60 * 1000;
        scheduler.schedule(this::rest, delay, TimeUnit.MILLISECONDS);
    }


    @Override
    public int onLoop() {
        drop();
        placeTraps();
        pickUpSalamander();
        pickUpTraps();
        areaCheck();
        log("looped");
        return 2500;
    }

    private void placeTraps() {
        if(Inventory.contains("Rope") && Inventory.contains("Small fishing net")) {
            GameObject youngTree = GameObjects.closest(gameObject -> (gameObject != null) && gameObject.getID() == 8990 && gameObject.distance() < 15);
            if (youngTree != null && youngTree.interact("Set-trap")) {
                log("placing trap");
                sleep(1506, 2610);
            } else {
                log("no available places to put a trap...waiting...");
            }
        }
        pickUpTraps();
    }

    private void pickUpSalamander() {
        if(!Inventory.isFull()) {
            GameObject salamanderSuccess = GameObjects.closest(gameObject -> (gameObject != null) && gameObject.getID() == 8986);
            if (salamanderSuccess != null && salamanderSuccess.interact("Check")) {
                log("pick up salamander");
                sleep(1212, 3398);
            }
            pickUpTraps();
            log("picking up traps after picking up salamander 1");
            pickUpTraps();
            log("picking up traps after picking up salamander 2");
            sleep(212, 1898);
        }
    }

    private void drop() {
        if (Inventory.emptySlotCount() < 4) {
            openInventory();
            log("inventory full, dropping salamanders");
            while (Inventory.contains("Red Salamander")) {
                Inventory.interact("Red salamander", "Release");
                sleep(220, 442);
            }
            log("inventory dropped");
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
            sleep(50, 250);
        }
    }



    private void pickUpTraps() {
        GroundItem item = GroundItems.closest("Rope", "Small fishing net");
        if (item != null) {
            item.interact("Take");
            log("picking up rope/net");
            sleep(297, 1386);
            item.interact("Take");
            log("picking up rope/net");
            sleep(297, 1386);
        } else {
            log("no rope/net to pick up");
        }
    }

    private void areaCheck() {
        if (!redSalamanderArea.contains(Players.getLocal())) {
            log("outside of area");
            Walking.walk(redSalamanderArea.getRandomTile());
            log("moving to area");
        }
    }


    private void rest() {
        drop();
        pickUpTraps();
        pickUpSalamander();
        sleep(4000, 12000);
        pickUpTraps();
        pickUpSalamander();
        sleep(60000, 333333);
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    @Override
    public void onPaint(Graphics graphics) {
        super.onPaint(graphics);
    }
}
//Young tree, Red salamander
//8990 is young tree with nothing on it
//8989 is young tree with net on it
//8986 is young tree with successful catch in it


//INVNET HAS 2-5 SLOTS LEFT, THEN DROP
//Antiban to add
//Random misclicks
//setting screen angle and occasionally rotating
//if script running longer than 10 mins, then slow down, if script running x amount then speed up. modulate this.
//wind mouse






