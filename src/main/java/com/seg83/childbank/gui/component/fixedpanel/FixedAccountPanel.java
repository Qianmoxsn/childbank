package com.seg83.childbank.gui.component.fixedpanel;


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.seg83.childbank.gui.event.PanelSwitchEvent;
import com.seg83.childbank.service.DepositService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * Panel of Deposit Account
 * FixedAccountPanel is a JPanel class that represents the fixed account panel in the childbank application.
 */
@Component
@Slf4j
public class FixedAccountPanel {
    @Autowired
    DepositService depositService;

    @Autowired
    private ApplicationEventPublisher publisher;

  /**
     * The backButton is a JButton that allows the user to go back to the home panel.
     */
    private JButton backButton;

    /**
     * The table1 is a JTable that displays the deposit account information.
     */
    private JTable table1;

    /**
     * The totalLabel is a JLabel that shows the total fixed balance.
     */
    private JLabel totalLabel;

    /**
     * Constructs a new FixedAccountPanel.
     */
    private JPanel panel1;

    public FixedAccountPanel() {
//        $$$setupUI$$$();

        // Add action listener to the back button
        backButton.addActionListener(e -> {
            publisher.publishEvent(new PanelSwitchEvent(this, "home"));
        });
    }

    /**
     * Creates the table1 and sets its model with the deposit account data.
     */
    public void createTable() {
        // TODO: use actual data
        Object[][] data = depositService.generateDepositList();


        String[] columnNames = {"Id", "Amount", "Rate", "Effective Date", "Expire Date"};

        table1.setModel(new DefaultTableModel(data, columnNames));
        table1.setFillsViewportHeight(true);
        table1.setFillsViewportHeight(true);
        table1.setRowHeight(30);
        table1.setRowMargin(5);
        table1.setRowSelectionAllowed(true);
        table1.setColumnSelectionAllowed(true);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 设置列宽
        TableColumnModel columnModel = table1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(25);  // ID 列
        columnModel.getColumn(1).setPreferredWidth(80);  // Amount 列
        columnModel.getColumn(2).setPreferredWidth(80); // Rate 列
        columnModel.getColumn(3).setPreferredWidth(200); // Effective Date 列
        columnModel.getColumn(4).setPreferredWidth(200); // Expire Date 列
    }

    /**
     * Sets the text of the totalLabel to the total fixed balance.
     */
    public void setTotalFixedLabel() {
        totalLabel.setText("$" + depositService.calculateTotalDeposits());
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
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "DepositAccount Operation", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Fixed Balance", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        totalLabel = new JLabel();
        totalLabel.setText("$114514--");
        panel3.add(totalLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("< Back Home");
        panel2.add(backButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(84, 34), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
