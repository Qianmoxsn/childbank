package com.seg83.childbank.gui;

import com.seg83.childbank.gui.component.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SwingApp {

    /**
     * Main frame of the application
     */
    private final MainFrame mainFrame;

    /**
     * Constructor
     * @param frame Main frame of the application
     */
    @Autowired
    public SwingApp(MainFrame frame) {
        this.mainFrame = frame;
    }

    /**
     * Create the UI
     */
    public void createUI() {
        mainFrame.init();
        mainFrame.setVisible(true);
    }
}
