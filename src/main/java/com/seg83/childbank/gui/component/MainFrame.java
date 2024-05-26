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

@Component
@Slf4j
public class MainFrame extends JFrame {
    private HomePanel homePanel;
    private WelcomePanel welcomePanel;
    private SetupPanel setupPanel;
    private SettingsPanel settingsPanel;
    private FixedAccountPanel fixedAccountPanel;
    private CurrentAccountPanel currentAccountPanel;
    private TaskPanel taskPanel;

    private SetupService setupService;
    private InterestService interestService;
    private DepositService depositService;

    public MainFrame() throws HeadlessException {
        setTitle("ChildBank");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        log.info("Create MainFrame");
    }

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

    @PostConstruct
    public void init() {
        if (setupService.checkFirstLogin()) {
            initWelcomePanel();
        } else {
            initHomePanel();
        }
    }

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

    private void initWelcomePanel() {
        welcomePanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.welcomePanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create welcomePanel in MainFrame");
    }

    private void initSetupPanel() {
        setupPanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.setupPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create setupPanel in MainFrame");
    }

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

    private void initSettingsPanel() {
        settingsPanel.$$$getRootComponent$$$().updateUI();
        setContentPane(this.settingsPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create settingPanel in MainFrame");
    }

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

    private void initCurrentPanel() {
        currentAccountPanel.$$$getRootComponent$$$().updateUI();
        currentAccountPanel.updatePanel();
        setContentPane(this.currentAccountPanel.$$$getRootComponent$$$());
        // 显式刷新
        revalidate();
        repaint();
        log.info("Create currentAccountPanel in MainFrame");
    }

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
