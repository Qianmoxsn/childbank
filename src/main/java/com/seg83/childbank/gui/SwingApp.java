package com.seg83.childbank.gui;

import com.seg83.childbank.gui.component.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SwingApp {

    private final MainFrame mainFrame;

    @Autowired
    public SwingApp(MainFrame frame) {
        this.mainFrame = frame;
    }

    public void createUI() {
        mainFrame.init();
        mainFrame.setVisible(true);
    }
}
