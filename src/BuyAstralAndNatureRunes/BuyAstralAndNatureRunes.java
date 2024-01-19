package BuyAstralAndNatureRunes;

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



@ScriptManifest(name = "BuyAstralAndNatureRunes", description = "BuyAstralAndNatureRunes", author = "Luten",
        version = 1.0, category = Category.CONSTRUCTION, image = "")
public class BuyAstralAndNatureRunes extends AbstractScript {

    State state;
    Timer timer = new Timer();
    Area nearBabaYagaHouse = new Area(2094, 3928, 2084, 3932, 0);
    Area inBabaYagaHouse = new Area(2449, 4645, 2453, 4649, 0);
    Area bankArea = new Area(2095, 3916, 2101, 3919,0);
    int numOfAstralRunesToPurchase = 10000; //change this later to become whatever the user inputs on startup
    int numOfNatureRunesToPurchase = 10000;
    AntiBan antiBan = new AntiBan();


    @Override
    public void onStart() {
        log("Script Started");
        SkillTracker.start();
        SkillTracker.start(Skill.MAGIC);

    }


    @Override
    public int onLoop() {
        switch (getState()) {
            case RESTOCK_ASTRALS:
                if (!Shop.isOpen() && NPCs.closest(3837) != null) {
                    NPCs.closest(3837).interact("Trade");
                    sleep(2000, 3500);
                }

                if (Shop.isOpen() && Shop.count("Astral rune") > 101) {
                    Shop.purchaseFifty("Astral rune");
                    log("Total astrals in inventory is " + Inventory.count("Astral rune"));
                    log("need to purchase " + (numOfAstralRunesToPurchase - Inventory.count("Astral rune")) + " more Astral runes.");
                    sleep(100, 1100);
                } else if (Shop.isOpen() && Shop.count("Nature rune") > 101) {
                    Shop.purchaseFifty("Nature rune");
                    log("Total Natures in inventory is " + Inventory.count("Nature rune"));
                    log("need to purchase " + (numOfNatureRunesToPurchase - Inventory.count("Nature rune")) + " more Nature runes.");
                    sleep(100, 1100);
                } else if (Shop.isOpen() && Shop.count("Nature rune") <= 101) {
                    antiBan.hopWorlds();
                }
                sleep(1600, 2200);
                break;



            case STOP:
                stop();
                break;
        }
        return 1;
    }



    private State getState() {
        if (inBabaYagaHouse.contains(Players.getLocal().getTile()) && Inventory.count("Astral rune") < numOfAstralRunesToPurchase) {
            log("State is now RESTOCK_ASTRALS");
            return State.RESTOCK_ASTRALS;
//            If restock astrals checkbox is ticked (true) then when astrals run out it will purchase the amount of astrals input by the user from babas shop, before continuing the script
            //this will only work if you have started on lunar isle

        } else if (inBabaYagaHouse.contains(Players.getLocal().getTile()) && Inventory.count("Astral rune") >= numOfAstralRunesToPurchase) {
            log("State is now stop");
            return State.STOP;

        }
        log("returning state");
        stop();
        return state;
    }

    @Override
    public void onExit() {
        super.onExit();
    }
}