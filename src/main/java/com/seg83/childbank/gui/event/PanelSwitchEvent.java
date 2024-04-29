package com.seg83.childbank.gui.event;

import org.springframework.context.ApplicationEvent;

public class PanelSwitchEvent extends ApplicationEvent {
    private String panelName;

    public PanelSwitchEvent(Object source, String panelName) {
        super(source);
        this.panelName = panelName;
    }

    public String getPanelName() {
        return panelName;
    }
}
