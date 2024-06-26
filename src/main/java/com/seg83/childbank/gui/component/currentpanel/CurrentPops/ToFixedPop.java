package com.seg83.childbank.gui.component.currentpanel.CurrentPops;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.seg83.childbank.dao.AdminDao;
import com.seg83.childbank.gui.component.currentpanel.CurrentAccountPanel;
import com.seg83.childbank.service.DepositService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A JDialog component that facilitates depositing money into fixed account
 * It includes fields for entering the amount, the mode and password, and buttons for OK and Cancel actions
 */
@Component
@Slf4j
public class ToFixedPop extends JDialog {

    @Autowired
    DepositService depositService;

    @Autowired
    AdminDao adminDao;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField amountField;
    private JPasswordField passwordField1;
    private JLabel rateLabel;
    private JTextField rateField;
    private JTextField monthField;

    public ToFixedPop() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * This method is called when the user clicks the OK button.
     */
    private void onOK() {
        // add your code here
        String amount = amountField.getText();
        int amountInt;
        // ammount should be a integer
        try {
            amountInt = Integer.parseInt(amount);
            if (amountInt < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // show a dialog
            JOptionPane.showMessageDialog(this, "Amount should be an non-negative Integer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rate = rateField.getText();
        double rateDouble;
        try {
            rateDouble = Double.parseDouble(rate);
            if (rateDouble < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // show a dialog
            JOptionPane.showMessageDialog(this, "Rate should be an non-negative Double", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String month = monthField.getText();
        int monthInt;
        try {
            monthInt = Integer.parseInt(month);
            if (monthInt < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // show a dialog
            JOptionPane.showMessageDialog(this, "Month should be an non-negative Integer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        char[] pass = passwordField1.getPassword();
        String password = new String(pass);
        log.debug("Amount: {}, Password: {}", amount, password);


        // 处理日期
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusMonths(monthInt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String todayStr = formatter.format(today);
        String futureDateStr = formatter.format(futureDate);


        String target = (String) adminDao.getAttribute("adminPassword");
        boolean check = password.equals(target);
        if (!check) {
            JOptionPane.showMessageDialog(this, "Password mistake", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (depositService.depositFixAccount(amountInt, rateDouble, todayStr, futureDateStr)) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Deposit failed", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * This method is called when the user clicks the Cancel button.
     */
    private void onCancel() {
        // add your code here if necessary


        dispose();
    }

    public void init() {
        log.debug("Initializing tofix dialog");
        // Clear the text fields
        amountField.setText("");
        monthField.setText("");
        passwordField1.setText("");
        this.pack();  // 使用已经存在的this引用而不是创建新的实例
        setLocationRelativeTo(null);  // null 使窗口居中于屏幕
        this.setVisible(true);
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Deposit Amount");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Deposit Period (mounth)");
        panel3.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Password");
        panel3.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField1 = new JPasswordField();
        panel3.add(passwordField1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        amountField = new JTextField();
        panel3.add(amountField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        rateLabel = new JLabel();
        rateLabel.setText("Deposit rate");
        panel3.add(rateLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rateField = new JTextField();
        panel3.add(rateField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        monthField = new JTextField();
        panel3.add(monthField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
