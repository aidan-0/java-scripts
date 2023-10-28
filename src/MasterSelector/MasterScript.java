package MasterSelector;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import ZammyWineStealer.ZammyWineStealer;
import MortMyrePicker.MortMyrePicker;
import BuyAndCrushChocolate.BuyAndCrushChocolate;
import RedSalamanders.RedSalamanders;
import BlackSalamanders.BlackSalamanders;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@ScriptManifest(name = "Zeb's Ironman AIO Script", description = "Choose your script...", author = "Luten",
        version = 1.0, category = Category.UTILITY, image = "")
public class MasterScript extends AbstractScript {

    private AbstractScript chosenScript;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private final HashMap<String, String> scriptInstructions = new HashMap<>() {{
        put("Zammy Wine Stealer",
                """
                        Zammy Wine Stealer Instructions:
                        - Start your character in mage bank.
                        - You will need sufficient Air and Law runes, and a knife or wildy sword to slash the webs. Stock up on a few extra in case you die
                        - The bot will try hop worlds any time a player is nearby while it is in the wildy, but it can still get caught by pkers.
                        
                        Coming soon:
                        - Start from any bank coming soon...
                        - Auto reset from Lumbridge if you die (glory to edgeville, edgeville lever)...
                        """
        );


        put("Mort Myre Picker",
                """
                        Instructions for Mort Myre Picker:
                        Start from anywhere, but make sure to have these items equipped:
                        - Any Ardy Cloak.
                        - Drakan's Medalion
                        - Ivandis Flail or Enchanted emerald sickle (to cast bloom).
                        """

        );



        put("Buy And Crush Chocolate",
                """
                        Instructions for Buy And Crush Chocolate:
                        Start in Lumbridge Castle basement next to the bank with the following items in your bank:
                        - Pestle and mortar
                        - Knife
                        - Coins to purchase chocolate.
                        
                        You will need to have completed enough of recipe for disaster to have unlocked chocolate bars from the Culinaromancer's Chest.
                        """

        );
        put("Red Salamanders",
                """
                        Instructions for Red Salamanders:
                        Start next to the Red Salamanders by Ourania Altar with around 8 Rope and 8 Small fishing nets.
                        """
        );
        put("Black Salamanders",
                """
                        Instructions for Black Salamanders:
                        Start next to the Black Salamanders in the Wilderness with around 10 Rope and 10 Small fishing nets.
                        
                        You may slowly lose fishing nets / rope over time. This is being looked into and will be fixed in a later update. For now it works perfectly fine but may need you to watch over it.
                        Note that pkers sometimes, but rarely, come to this area. If you die the script will not re-gear yet. Coming in a later update.
                        """

        );
    }};

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(this::displayGUI);
    }

    private void displayGUI() {
        frame = new JFrame("Choose Script");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 200);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel selectionPanel = createSelectionPanel();
        JPanel confirmationPanel = createConfirmationPanel("");

        mainPanel.add(selectionPanel, "SELECTION");
        mainPanel.add(confirmationPanel, "CONFIRMATION");

        frame.add(mainPanel);
        cardLayout.show(mainPanel, "SELECTION");

        frame.setVisible(true);
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(Color.decode("#343437"));
        header.setPreferredSize(new Dimension(frame.getWidth(), 30));
        JLabel titleLabel = new JLabel("Choose your script");
        JLabel descriptionLabel = new JLabel("Please click on the desired script button");
        header.add(titleLabel);
        header.add(descriptionLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        String[] scripts = {
                "Zammy Wine Stealer", "Mort Myre Picker", "Buy And Crush Chocolate",
                "Red Salamanders", "Black Salamanders"
        };
        for (String script : scripts) {
            buttonPanel.add(createButton(script));
        }

        panel.add(header, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#343437"));
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> showConfirmationDialog(text));
        return button;
    }

    private JPanel createConfirmationPanel(String scriptName) {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setLineWrap(true); // This allows the text to wrap to the next line
        instructionsArea.setWrapStyleWord(true); // This ensures that words aren't broken apart when wrapping
        instructionsArea.setPreferredSize(new Dimension(frame.getWidth(), 35));
        instructionsArea.setBackground(Color.decode("#2f2f2f"));
        instructionsArea.setText(scriptInstructions.getOrDefault(scriptName, "Make sure you have the required items for " + scriptName + ". Once this is done, click start."));

        JButton startButton = createStartButton(scriptName);
        JButton backButton = createBackButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(startButton);

        panel.add(instructionsArea, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStartButton(String scriptName) {
        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.decode("#343437"));
        startButton.setPreferredSize(new Dimension(63, 28));
        startButton.addActionListener(e -> {
            switch (scriptName) {
                case "Zammy Wine Stealer":
                    chosenScript = new ZammyWineStealer();
                    break;
                case "Mort Myre Picker":
                    chosenScript = new MortMyrePicker();
                    break;
                case "Buy And Crush Chocolate":
                    chosenScript = new BuyAndCrushChocolate();
                    break;
                case "Red Salamanders":
                    chosenScript = new RedSalamanders();
                    break;
                case "Black Salamanders":
                    chosenScript = new BlackSalamanders();
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Script not recognized.");
                    return;
            }
            frame.dispose();
        });
        return startButton;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.decode("#343437"));
        backButton.setPreferredSize(new Dimension(63, 28));
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "SELECTION");
            frame.revalidate();
            frame.repaint();
        });
        return backButton;
    }

    private void showConfirmationDialog(String scriptName) {
        JPanel confirmationPanel = createConfirmationPanel(scriptName);
        mainPanel.add(confirmationPanel, "CONFIRMATION");
        cardLayout.show(mainPanel, "CONFIRMATION");

        frame.revalidate();
        frame.repaint();
    }

    @Override
    public int onLoop() {
        if (chosenScript != null) {
            return chosenScript.onLoop();
        }
        return 1000; // Default sleep time when no script is chosen
    }

    @Override
    public void onExit() {
        if (frame != null) {
            frame.dispose();
        }
    }

    // Optionally, if you have other lifecycle methods in the AbstractScript, you can override and add them here.

//    public static void main(String[] args) {
//        // This main method can be used for testing purposes if required.
//        new MasterScript().onStart();
//    }
}




//Please remember that the code is now structured in a cleaner and more modular way.
// If you have any other lifecycle methods in AbstractScript that you might use (like onPaint, onExit, etc.),
// you can override and implement them in the MasterScript class as required.
