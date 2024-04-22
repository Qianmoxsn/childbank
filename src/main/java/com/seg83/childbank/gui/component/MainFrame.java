package com.seg83.childbank.gui.component;

import com.seg83.childbank.gui.component.homepanel.HomePanel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Slf4j
public class MainFrame extends JFrame {
    private HomePanel homePanel;

    public MainFrame() throws HeadlessException {
        setTitle("ChildBank");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        log.info("Create MainFrame");
    }

    @Autowired
    private void setHomePanel(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

    @PostConstruct
    public void init() {
        homePanel.$$$getRootComponent$$$().updateUI();
        homePanel.updateCurrentBallance();

        setContentPane(this.homePanel.$$$getRootComponent$$$());
        log.info("Create homePanel in MainFrame");
    }
}
