package com.seg83.childbank.gui;

import com.seg83.childbank.gui.component.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * The SwingApp class represents the main application for the Swing-based GUI.
 */
@Component
public class SwingApp {

    private final MainFrame mainFrame;

    /**
     * Constructs a new SwingApp with the specified main frame.
     *
     * @param frame The main frame of the application.
     */
    @Autowired
    public SwingApp(MainFrame frame) {
        this.mainFrame = frame;
    }

    /**
     * Creates the user interface by initializing the main frame and making it visible.
     */
    public void createUI() {
        mainFrame.init();
        mainFrame.setVisible(true);
    }
}

