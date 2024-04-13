package com.seg83.childbank.gui.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Slf4j
public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        setTitle("ChildBank");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    public void init() {
        log.info("Create Main Frame");
    }
}
