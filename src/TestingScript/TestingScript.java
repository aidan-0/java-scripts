package TestingScript;

import org.dreambot.api.methods.depositbox.DepositBox;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;


@ScriptManifest(name = "Testing Script", description = "Test my scripts", author = "Luten",
        version = 1.0, category = Category.HERBLORE, image = "")


public class TestingScript extends AbstractScript {




    @Override
    public void onStart() {
        log("Script Started");
//        disableSolver(RandomEvent.LOGIN);
        getRandomManager().disableSolver(RandomEvent.LOGIN);
        Tabs.logout();
        log("Sleeping");
        sleep(10000);
        log("Wake up");
        getRandomManager().disableSolver(RandomEvent.LOGIN);
        log("logging in");

    }

    public void disableSolver() {

    }

    @Override
    public int onLoop() {

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



