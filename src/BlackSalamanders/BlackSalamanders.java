package BlackSalamanders;

import Antiban.AntiBan;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.methods.map.Area;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;



@ScriptManifest(name = "Black Salamanders", description = "Catches Black Salamanders in the Wilderness", author = "Luten",
        version = 1.0, category = Category.HUNTING, image = "")
public class BlackSalamanders extends AbstractScript {

    public AntiBan antiBanInstance = new AntiBan(); //imports and uses OpenTabs Anti-ban
    Area blackSalamanderArea = new Area(3292, 3678, 3300, 3664, 0); //Sets Black Salamander Area
    public final Point INVENTORY_WIDGET = new Point(643, 185); // Inventory menu
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Override
    public void onStart() {
        log("Script Started");
        antiBanInstance.onStart();
        openInventory();
        SkillTracker.start();
        SkillTracker.start(Skill.HUNTER);
        placeTraps();
        placeTraps();
    }



    @Override
    public int onLoop() {
        drop();
        pickUpSalamander();
        pickUpSalamander();

        pickUpRope();
        pickUpNets();
        pickUpRope();
        pickUpNets();
        placeTraps();

        pickUpSalamander();
        pickUpSalamander();
        pickUpSalamander();


        pickUpRope();
        pickUpNets();
        pickUpRope();
        pickUpNets();
        placeTraps();

        pickUpSalamander();
        pickUpSalamander();

        pickUpRope();
        pickUpNets();
        pickUpRope();
        pickUpNets();

        areaCheck();
        log("looped");
        log(SkillTracker.getGainedExperiencePerHour(Skill.HUNTER));
        return 2500;
    }

//  place traps is good. don't change
    private void placeTraps() {
        // if there are 5 or more gameObjects with ID of 8996 then skip over this
        long countOfObjects = GameObjects.all(gameObject -> (gameObject != null) && (gameObject.getID() == 8996 || gameObject.getID() == 9002)).size();
        log("There are " + countOfObjects + " traps in use");
        if (countOfObjects >= 5) {
            log("5 or more game objects with ID 8996 or 9002 found. Skipping trap placement.");
            return;
        }

        if(Inventory.contains("Rope") && Inventory.contains("Small fishing net")) {
            GameObject youngTree = GameObjects.closest(gameObject -> (gameObject != null) && gameObject.getID() == 9000 && gameObject.distance() < 15);
            if (youngTree != null && youngTree.interact("Set-trap")) {
                log("placing trap");
//               wait until action completed
                sleepUntil(() -> Players.getLocal().isAnimating(), 5000, 50);
                sleep(1800, 2250);
            } else {
                log("no available places to put a trap...waiting...");
            }
        }
    }

    private void pickUpSalamander() {
        if(!Inventory.isFull()) {
            GameObject salamanderSuccess = GameObjects.closest(gameObject -> (gameObject != null) && gameObject.getID() == 8996);
            int currentHunterXp = Skills.getExperience(Skill.HUNTER);
            if (salamanderSuccess != null && salamanderSuccess.interact("Check")) {
                log("pick up salamander");
                //sleep until xp drop
                sleepUntil(() -> Skills.getExperience(Skill.HUNTER) > currentHunterXp, 5000, 50);
                sleep(400, 900);
            }
            placeTraps();
            drop();
        }
    }

    private void drop() {
        if (Inventory.emptySlotCount() < 4) {
            openInventory();
            log("inventory full, dropping salamanders");
            while (Inventory.contains("Black Salamander")) {
                Inventory.interact("Black salamander", "Release");
                sleep(220, 442);
            }
            log("inventory dropped");
            pickUpRope();
            pickUpNets();
            pickUpRope();
            pickUpNets();
            pickUpRope();
            pickUpNets();
        }
    }


    private void pickUpRope() {
        GroundItem rope = GroundItems.closest("Rope");
        if (rope != null) {
            rope.interact("Take");
            Sleep.sleep(2100,2600);
            log("picking up rope");
        }
        sleep(100, 300);
    }

    private void pickUpNets() {
        GroundItem net = GroundItems.closest("Small fishing net");
        if (net != null) {
            net.interact("Take");
            Sleep.sleep(1200,1800);
            log("picking up net");
        }
        sleep(100, 300);
    }


    private void areaCheck() {
        if (!blackSalamanderArea.contains(Players.getLocal())) {
            log("outside of area");
            Walking.walk(blackSalamanderArea.getRandomTile());
            log("moving to area");
        }
    }

    private void rest() {
        drop();
        pickUpNets();
        pickUpRope();
        pickUpNets();
        pickUpSalamander();
        sleep(4000, 12000);
        pickUpRope();
        pickUpNets();
        pickUpSalamander();
        log("Now starting Rest Period");
        sleep(60000, 333333);
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
//Young tree, Black salamander
//8990/9000 is young tree with nothing on it
//8989/9002 is young tree with net on it
//8986/8996 is young tree with successful catch in it


//wind mouse






