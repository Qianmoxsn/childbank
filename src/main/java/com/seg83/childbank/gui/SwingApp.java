package com.seg83.childbank.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seg83.childbank.gui.component.MainFrame;


@Component
public class SwingApp {

    private MainFrame mainFrame;

    @Autowired
    private void setMainFrame(MainFrame frame) {
        this.mainFrame = frame;
    }

    public void createUI() {
        mainFrame.init();
        mainFrame.setVisible(true);
    }
}
