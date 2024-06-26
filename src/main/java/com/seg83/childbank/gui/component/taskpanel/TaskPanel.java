package com.seg83.childbank.gui.component.taskpanel;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.seg83.childbank.gui.event.PanelSwitchEvent;
import com.seg83.childbank.service.TaskListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The TaskPanel class is responsible for displaying the task list and providing task confirmation functionality.
 */
@Component
@Slf4j
public class TaskPanel {

    /**
     * The TaskListService interface instance for handling task list-related business logic.
     */
    @Autowired
    TaskListService taskListService;

    /**
     * The TaskPop class instance for displaying the task confirmation dialog.
     */
    @Autowired
    TaskPop taskPop;

    /**
     * The ApplicationEventPublisher interface instance for publishing panel switch events.
     */
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * The Back Home button.
     */
    private JButton backHomeButton;

    /**
     * The Task list table.
     */
    private JTable table1;

    /**
     * The Confirm Task button.
     */
    private JButton confirmTaskButton;

    /**
     * The Main panel.
     */
    private JPanel rootpanel;

    /**
     * Constructor that initializes the UI components and sets up action listeners.
     */
    public TaskPanel() {
        $$$setupUI$$$();
        confirmTaskButton.addActionListener(e -> {
            log.info("Confirm task button clicked");
            taskPop.init();
            updatePanel();
        });
        backHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Back Home button clicked");
                publisher.publishEvent(new PanelSwitchEvent(this, "home"));

            }
        });
    }

    /**
     * Creates the Task list table.
     */
    public void createTable() {
        Object[][] data = taskListService.generateTaskList();
        String[] columnNames = {"Id", "Task Name", "Task Description", "Status"};

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
        columnModel.getColumn(0).setPreferredWidth(10);  // ID 列
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(70);
    }

    /**
     * Updates the panel by recreating the Task list table.
     */
    public void updatePanel() {
        table1.removeAll();
        createTable();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootpanel = new JPanel();
        rootpanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        backHomeButton = new JButton();
        backHomeButton.setText("< Back Home");
        rootpanel.add(backHomeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        rootpanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        rootpanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        confirmTaskButton = new JButton();
        confirmTaskButton.setText("Confirm task");
        rootpanel.add(confirmTaskButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        rootpanel.add(scrollPane1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootpanel;
    }

}
