//package MasterSelector;
//
//import org.dreambot.api.script.AbstractScript;
//import org.dreambot.api.script.Category;
//import org.dreambot.api.script.ScriptManifest;
//import ZammyWineStealer.ZammyWineStealer;
//import MortMyrePicker.MortMyrePicker;
//import BuyAndCrushChocolate.BuyAndCrushChocolate;
//import RedSalamanders.RedSalamanders;
//import BlackSalamanders.BlackSalamanders;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.HashMap;
//
//@ScriptManifest(name = "Zeb's Ironman AIO Script", description = "Choose your script.", author = "Zeb",
//        version = 1.0, category = Category.UTILITY, image = "")
//public class MasterScript extends AbstractScript {
//
//    private AbstractScript chosenScript;
//    private JFrame frame;
//    private CardLayout cardLayout;
//    private JPanel mainPanel;
//    private HashMap<String, String> scriptInstructions = new HashMap<>();
//
//
//    @Override
//    public void onStart() {
//        SwingUtilities.invokeLater(this::displayGUI);
//    }
//
//    private void displayGUI() {
//        frame = new JFrame("Choose Script");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setSize(500, 200);
//
//        cardLayout = new CardLayout();
//        mainPanel = new JPanel(cardLayout);
//
//        JPanel selectionPanel = createSelectionPanel();
//        JPanel confirmationPanel = createConfirmationPanel("");  // Initially empty
//
//        mainPanel.add(selectionPanel, "SELECTION");
//        mainPanel.add(confirmationPanel, "CONFIRMATION");
//
//        frame.add(mainPanel);
//        cardLayout.show(mainPanel, "SELECTION");
//
//        frame.setVisible(true);
//
//        //Script Instructions
//        scriptInstructions.put("Zammy Wine Stealer", "Instructions for Zammy Wine Stealer...");
//        scriptInstructions.put("Mort Myre Picker", "Instructions for Mort Myre Picker...");
//        scriptInstructions.put("Buy And Crush Chocolate", "Instructions for Buy And Crush Chocolate...");
//        scriptInstructions.put("Red Salamanders", "Instructions for Red Salamanders...");
//        scriptInstructions.put("Black Salamanders", "Instructions for Black Salamanders...");
//    }
//
//    private JPanel createSelectionPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        JPanel header = new JPanel();
//        header.setBackground(Color.decode("#343437"));
//        header.setPreferredSize(new Dimension(frame.getWidth(), 30));
//        JLabel titleLabel = new JLabel("Choose your script");
//        JLabel descriptionLabel = new JLabel("Please click on the desired script button");
//        header.add(titleLabel);
//        header.add(descriptionLabel);
//
//        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
//        JButton zammyButton = createButton("Zammy Wine Stealer");
//        JButton mortMyreButton = createButton("Mort Myre Picker");
//        JButton buyAndCrushChocolate = createButton("Buy And Crush Chocolate");
//        JButton redSalamanders = createButton("Red Salamanders");
//        JButton blackSalamanders = createButton("Black Salamanders");
//
//        buttonPanel.add(zammyButton);
//        buttonPanel.add(mortMyreButton);
//        buttonPanel.add(buyAndCrushChocolate);
//        buttonPanel.add(redSalamanders);
//        buttonPanel.add(blackSalamanders);
//
//        panel.add(header, BorderLayout.NORTH);
//        panel.add(buttonPanel, BorderLayout.CENTER);
//
//        return panel;
//    }
//
//    private JPanel createConfirmationPanel(String scriptName) {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        JTextArea instructionsArea = new JTextArea("Make sure you have the required items for " + scriptName + ". Once this is done, click start.");
//        instructionsArea.setPreferredSize(new Dimension(frame.getWidth(), 35));
//        instructionsArea.setBackground(Color.decode("#2f2f2f"));
//
//        JButton startButton = new JButton("Start");
//        startButton.setBackground(Color.decode("#343437"));
//        startButton.setPreferredSize(new Dimension(63, 28));
//
//        JButton backButton = new JButton("Back");
//        backButton.setBackground(Color.decode("#343437"));
//        backButton.setPreferredSize(new Dimension(63, 28));
//        backButton.addActionListener(e -> cardLayout.show(mainPanel, "SELECTION"));
//
//        JPanel southPanel = new JPanel();
//        southPanel.add(backButton);
//        southPanel.add(startButton);
//
//        panel.add(instructionsArea, BorderLayout.CENTER);
//        panel.add(southPanel, BorderLayout.SOUTH);
//
//        return panel;
//    }
//
//    private JButton createButton(String text) {
//        JButton button = new JButton(text);
//        button.setBackground(Color.decode("#343437"));
//        button.setForeground(Color.WHITE);
//        button.addActionListener(e -> showConfirmationDialog(text));
//        return button;
//    }
//
//    private void showConfirmationDialog(String scriptName) {
//        JPanel confirmationPanel = createConfirmationPanel(scriptName);
//
//        JTextArea instructionsArea = (JTextArea)confirmationPanel.getComponent(0); // Assuming the JTextArea is the first component of the panel
//        instructionsArea.setText(scriptInstructions.getOrDefault(scriptName, "Make sure you have the required items for " + scriptName + ". Once this is done, click start."));
//
//
//        JButton startButton = new JButton("Start");
//        startButton.setBackground(Color.decode("#343437"));
//        startButton.setPreferredSize(new Dimension(63, 28));
//        startButton.addActionListener(e -> {
//            switch (scriptName) {
//                case "Zammy Wine Stealer":
//                    chosenScript = new ZammyWineStealer();
//                    break;
//                case "Mort Myre Picker":
//                    chosenScript = new MortMyrePicker();
//                    break;
//                case "Buy And Crush Chocolate":
//                    chosenScript = new BuyAndCrushChocolate();
//                    break;
//                case "Red Salamanders":
//                    chosenScript = new RedSalamanders();
//                    break;
//                case "Black Salamanders":
//                    chosenScript = new BlackSalamanders();
//                    break;
//                default:
//                    JOptionPane.showMessageDialog(frame, "Script not recognized.");
//                    return;
//            }
//            frame.dispose(); // Close the script selection window
//        });
//
//        JButton backButton = new JButton("Back");
//        backButton.setBackground(Color.decode("#343437"));
//        backButton.setPreferredSize(new Dimension(63, 28));
//        backButton.addActionListener(e -> {
//            cardLayout.show(mainPanel, "SELECTION"); // Go back to the script selection
//            frame.revalidate();
//            frame.repaint();
//        });
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(backButton);
//        buttonPanel.add(startButton);
//        confirmationPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        mainPanel.add(confirmationPanel, "CONFIRMATION");
//        cardLayout.show(mainPanel, "CONFIRMATION"); // Switch to the confirmation panel
//
//        frame.revalidate();
//        frame.repaint();
//    }
//
//    @Override
//    public int onLoop() {
//        if (chosenScript != null) {
//            return chosenScript.onLoop();
//        }
//        return 1000;
//    }
//}



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

@ScriptManifest(name = "Zeb's Ironman AIO Script", description = "Choose your script...", author = "Zeb",
        version = 1.0, category = Category.UTILITY, image = "")
public class MasterScript extends AbstractScript {

    private AbstractScript chosenScript;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private HashMap<String, String> scriptInstructions = new HashMap<String, String>() {{
        put("Zammy Wine Stealer", "Instructions for Zammy Wine Stealer...");
        put("Mort Myre Picker", "Instructions for Mort Myre Picker...");
        put("Buy And Crush Chocolate", "Instructions for Buy And Crush Chocolate...");
        put("Red Salamanders", "Instructions for Red Salamanders...");
        put("Black Salamanders", "Instructions for Black Salamanders...");
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
