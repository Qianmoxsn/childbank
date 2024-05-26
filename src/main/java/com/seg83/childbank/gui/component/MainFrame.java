package com.seg83.childbank.gui.component;

import com.seg83.childbank.gui.component.currentpanel.CurrentAccountPanel;
import com.seg83.childbank.gui.component.fixedpanel.FixedAccountPanel;
import com.seg83.childbank.gui.component.homepanel.HomePanel;
import com.seg83.childbank.gui.component.settingpanel.SettingsPanel;
import com.seg83.childbank.gui.component.taskpanel.TaskPanel;
import com.seg83.childbank.gui.component.welcome.SetupPanel;
import com.seg83.childbank.gui.component.welcome.WelcomePanel;
import com.seg83.childbank.gui.event.PanelSwitchEvent;
import com.seg83.childbank.service.DepositService;
import com.seg83.childbank.service.InterestService;
import com.seg83.childbank.service.SetupService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame is the main window of the ChildBank application.
 */
@Component
@Slf4j
public class MainFrame extends JFrame {

    /**
     * HomePanel instance for displaying the home page.
     */
    private HomePanel homePanel;

    /**
     * WelcomePanel instance for displaying the welcome page.
     */
    private WelcomePanel welcomePanel;

    /**
     * SetupPanel instance for handling setup-related tasks.
     */
    private SetupPanel setupPanel;

    /**
     * SettingsPanel instance for displaying and updating user settings.
     */
    private SettingsPanel settingsPanel;

    /**
     * FixedAccountPanel instance for managing fixed-term accounts.
     */
    private FixedAccountPanel fixedAccountPanel;

    /**
     * CurrentAccountPanel instance for managing current accounts.
     */
    private CurrentAccountPanel currentAccountPanel;

    /**
     * TaskPanel instance for displaying and managing tasks.
     */
    private TaskPanel taskPanel;

    /**
     * SetupService instance for handling setup-related business logic.
     */
    private SetupService setupService;

    /**
     * InterestService instance for handling interest-related business logic.
     */
    private InterestService interestService;

     /**
     * DepositService instance for handling deposit-related business logic.
     */
    private DepositService depositService;

    /**
     * Constructor for creating a new MainFrame instance.
     *
     * @throws HeadlessException if the main window cannot be created
     */
    public MainFrame() throws HeadlessException {
        setTitle("ChildBank");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        log.info("Create MainFrame");
    }

    /**
     * Autowires the panels and services, and initializes the main window.
     */
    @Autowired
    private void setPanels(HomePanel homePanel, WelcomePanel welcomePanel, SetupPanel setupPanel, SetupService setupService, InterestService interestService,
                           SettingsPanel settingsPanel, FixedAccountPanel fixedAccountPanel, CurrentAccountPanel currentAccountPanel, TaskPanel taskPanel, DepositService depositService) {
        this.homePanel = homePanel;
        this.welcomePanel = welcomePanel;
        this.setupPanel = setupPanel;
        this.setupService = setupService;
        this.interestService = interestService;
        this.settingsPanel = settingsPanel;
        this.fixedAccountPanel = fixedAccountPanel;
        this.currentAccountPanel = currentAccountPanel;
        this.taskPanel = taskPanel;
        this.depositService = depositService;
}
    /**
     * Initializes the main window after all panels and services are autowired.
     */
    @PostConstruct
    public void init() {
        if (setupService.checkFirstLogin()) {
            initWelcomePanel();
        } else {
            initHomePanel();
        }
    }

     /**
     * Handles panel switch events by updating the main window's content pane.
     *
     * @param event the PanelSwitchEvent triggered by a panel switch
     */
    @EventListener
    public void onPanelSwitch(PanelSwitchEvent event) {
        switch (event.getPanelName()) {
            case "setup" -> initSetupPanel();
            case "home" -> initHomePanel();
            case "setting" -> initSettingsPanel();
            case "fixed" -> initFixedAccountPanel();
            case "current" -> initCurrentPanel();
            case "task" -> initTaskPanel();
            default -> throw new IllegalArgumentException("Unknown panel name: " + event.getPanelName());
        }
    }

    /**
     * Initializes the welcome panel and sets it as the main window's content pane.
     */
    private void initWelcomePanel() {
        welcomePanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.welcomePanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create welcomePanel in MainFrame");
    }

    /**
     * Initializes the setup panel and sets it as the main window's content pane.
     */
    private void initSetupPanel() {
        setupPanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.setupPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create setupPanel in MainFrame");
    }

    /**
     * Initializes the home panel and sets it as the main window's content pane.
     * Calculates current interest and processes matured deposits.
     */
    private void initHomePanel() {
        homePanel.$$$getRootComponent$$$().updateUI();
        interestService.calculateCurrentInterest();
        depositService.processMaturedDeposits();
        homePanel.updateCurrentBallance();
        homePanel.updateGoal();
        homePanel.updateFixBallance();

        setContentPane(this.homePanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create homePanel in MainFrame");
    }

    /**
     * Initializes the settings panel and sets it as the main window's content pane.
     */
    private void initSettingsPanel() {
        settingsPanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.settingsPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create settingPanel in MainFrame");
    }

    /**
     * Initializes the fixed account panel and sets it as the main window's content pane.
     * Creates the table and sets the total fixed label.
     */
    private void initFixedAccountPanel() {
        fixedAccountPanel.createTable();
        fixedAccountPanel.setTotalFixedLabel();
        fixedAccountPanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.fixedAccountPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create fixedAccountPanel in MainFrame");
    }


    /**
     * Initializes the task panel and sets it as the main window's content pane.
     * Creates the table.
     */
    private void initCurrentPanel() {
        currentAccountPanel.$$$getRootComponent$$$().updateUI();
        currentAccountPanel.updatePanel();
        setContentPane(this.currentAccountPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create currentAccountPanel in MainFrame");
    }

    /**
     * Initializes the task panel and sets it as the main window's content pane.
     * Creates the table.
     */
    private void initTaskPanel() {
        taskPanel.createTable();
        taskPanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.taskPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create taskPanel in MainFrame");
    }
}
