package TestingScript;

import Antiban.OpenTabs;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.depositbox.DepositBox;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;

import java.util.Arrays;



@ScriptManifest(name = "Testing Script", description = "Test my scripts", author = "Luten",
        version = 1.0, category = Category.HERBLORE, image = "")


public class TestingScript extends AbstractScript {

    @Override
    public void onStart() {
        log("Script Started");
        test();
    }

    @Override
    public int onLoop() {
        log("looped");
        test();
        return 2500;
    }

    private void test() {
        if (DepositBox.get("Open fish barrel") != null) {

            int slot = DepositBox.get("Open fish barrel").getSlot();
            log(DepositBox.getSlotWidget(slot));
            if (Widgets.getWidget(192).getChild(2).getChild(2) != null) {
                sleep(1200, 2000);

//                Widgets.getWidget(192).getChild(2).getChild(2).interact();
                sleep(1200, 2000);

                Widgets.getWidget(192).getChild(2).getChild(2).interact("Empty");
            }
        }


        sleep(1200, 2000);
    }
}



