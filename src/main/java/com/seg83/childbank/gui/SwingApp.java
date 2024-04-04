package com.seg83.childbank.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seg83.childbank.gui.ui.MainFrame;



@Component
public class SwingApp{

    @Autowired
    private MainFrame mainFrame;

    public void createUI(){
        mainFrame.init();
        mainFrame.setVisible(true);
    }


}
