package com.seg83.childbank.gui.component.welcome;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.seg83.childbank.gui.event.PanelSwitchEvent;
import com.seg83.childbank.service.SetupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

@Component
@Slf4j
public class SetupPanel {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private SetupService setupService;

    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton btnAdmin;
    private JButton btnTrade;
    private JPasswordField passwordField3;
    private JPasswordField passwordField4;
    private JButton startButton;
    private JTextPane welcomeToTheVirtualTextPane;

    public SetupPanel() {
        btnAdmin.addActionListener(e -> {
            log.info("Admin button clicked");
            int state = setupService.checkPass(new String(passwordField1.getPassword()), new String(passwordField2.getPassword()));
            switch (state) {
                case -1 -> {
                    log.error("password length illegal");
                    //Error Window
                    JOptionPane.showMessageDialog(null, "password length illegal", "Error", JOptionPane.ERROR_MESSAGE);
                }
                case -2 -> {
                    log.error("Admin password not match");
                    //Error Window
                    JOptionPane.showMessageDialog(null, "Tow Password not match", "Error", JOptionPane.ERROR_MESSAGE);
                }
                default -> {
                    setupService.setPassAdmin(new String(passwordField1.getPassword()));
                    log.info("Admin password set");
                    JOptionPane.showMessageDialog(null, "Admin Password Set", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnTrade.addActionListener(e -> {
            log.info("Trade button clicked");
            int state = setupService.checkPass(new String(passwordField3.getPassword()), new String(passwordField4.getPassword()));
            switch (state) {
                case -1 -> {
                    log.error("password length illegal");
                    //Error Window
                    JOptionPane.showMessageDialog(null, "password length illegal", "Error", JOptionPane.ERROR_MESSAGE);
                }
                case -2 -> {
                    log.error("Trade password not match");
                    //Error Window
                    JOptionPane.showMessageDialog(null, "Tow Password not match", "Error", JOptionPane.ERROR_MESSAGE);
                }
                default -> {
                    setupService.setPassAccount(new String(passwordField3.getPassword()));
                    log.info("Trade password set");
                    JOptionPane.showMessageDialog(null, "Trade Password Set", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        startButton.addActionListener(e -> {
            log.info("Start button clicked");
            // Remove First Login Flag
            setupService.setFirstLogin();
            // Switch to home panel
            publisher.publishEvent(new PanelSwitchEvent(this, "home"));
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.setBorder(BorderFactory.createTitledBorder(null, "Setup", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        tabbedPane1 = new JTabbedPane();
        rootPanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Introduction", panel1);
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Intro", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        welcomeToTheVirtualTextPane = new JTextPane();
        welcomeToTheVirtualTextPane.setBackground(new Color(-1118482));
        welcomeToTheVirtualTextPane.setEditable(false);
        welcomeToTheVirtualTextPane.setText("Welcome to the Virtual Bank Application for Kids, designed to be a secure and educational tool that grows with your child on their financial literacy journey. In this application, all deposits require a parental permission password to ensure security, while withdrawals can be managed independently by children within preset limits using their transaction password. Parents have the ability to assign taskLists and set rewards, allowing them to oversee taskList completion and reward achievements directly through the app. Additionally, the app includes robust security settings for managing password changes and account resets. A dedicated section for app functionalities and contact information is available for help and support. Our Virtual Bank Application for Kids is more than just a tool; it is a rich and supportive environment aimed at nurturing financially savvy children. We invite you to explore, learn, and grow with us in the safe, fun world of virtual banking for kids!  Now, let's set two passwords for admin and kids first!");
        scrollPane1.setViewportView(welcomeToTheVirtualTextPane);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("AdminPassword", panel2);
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Admin Password Setup", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Enter the Admin Password");
        panel2.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField1 = new JPasswordField();
        panel2.add(passwordField1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Confirm the Admin Password");
        panel2.add(label2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField2 = new JPasswordField();
        panel2.add(passwordField2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnAdmin = new JButton();
        btnAdmin.setText("Save");
        panel2.add(btnAdmin, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel2.add(spacer3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("TradePassword", panel3);
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Trade Password Setup", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer4 = new Spacer();
        panel3.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel3.add(spacer5, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Enter the Trade Password");
        panel3.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Confirm the Trade Password");
        panel3.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnTrade = new JButton();
        btnTrade.setText("Save");
        panel3.add(btnTrade, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField3 = new JPasswordField();
        panel3.add(passwordField3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField4 = new JPasswordField();
        panel3.add(passwordField4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel3.add(spacer6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Done", panel4);
        panel4.setBorder(BorderFactory.createTitledBorder(null, "Done", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label5 = new JLabel();
        label5.setText("You have finished the setup, click the button and start using!");
        panel4.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setText("Start !");
        panel4.add(startButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
