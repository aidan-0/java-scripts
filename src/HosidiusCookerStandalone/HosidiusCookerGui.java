package HosidiusCookerStandalone;

import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import javax.swing.*;
import java.awt.*;

@ScriptManifest(name = "Luten's Hosidius Cooker Gui", description = "Choose your script...", author = "Luten",
        version = 1.0, category = Category.COOKING, image = "Luten.png")
public class HosidiusCookerGui extends AbstractScript {

    private AbstractScript chosenScript;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String selectedFishType;
    private volatile boolean shouldStop = false; // Volatile ensures thread-safety.


    @Override
    public void onStart() {
        SwingUtilities.invokeLater(this::displayGUI);
    }

    private void displayGUI() {
        frame = new JFrame("Choose Script");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        cardLayout.show(mainPanel, "SELECTION");

        // Set the location of the JFrame to the center of the screen
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
        displayFishSelectorGUI();
        frame.dispose();
    }

    private void displayFishSelectorGUI() {
        String[] fishTypes = {"Karambwan", "Shark", "Manta ray", "Monk fish", "Sea turtle", "Anglerfish"}; // Add all the fish types here
        selectedFishType = (String) JOptionPane.showInputDialog(
                frame,
                "Select the fish type you wish to cook:",
                "Fish Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                fishTypes,
                fishTypes[0]);
        if (selectedFishType != null && !selectedFishType.isEmpty()) {
            chosenScript = new HosidiusCookerStandalone(this, selectedFishType); // Pass the selected fish type to the constructor
            chosenScript.onStart();
            frame.dispose();
        }
    }

    public void signalStop() {
        this.shouldStop = true;
    }

    @Override
    public int onLoop() {
        if (shouldStop) {
            log("Stopping MasterScript...");
            this.stop();
        }
        if (chosenScript != null) {
            return chosenScript.onLoop();
        }
        return 1000; // Default sleep time when no script is chosen
    }

    @Override
    public void onPaint(Graphics graphics) {
        if (chosenScript != null) {
            chosenScript.onPaint(graphics);
        }
    }


    @Override
    public void onExit() {
        Tabs.logout();
        if (frame != null) {
            frame.dispose();
        }
        stop();
    }
}
