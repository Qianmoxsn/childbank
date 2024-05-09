package com.seg83.childbank.gui.event;

import org.springframework.context.ApplicationEvent;
/**
 * The PanelSwitchEvent class represents an event indicating a panel switch.
 */
public class PanelSwitchEvent extends ApplicationEvent {
    private String panelName;

    /**
     * Constructs a new PanelSwitchEvent with the specified source and panel name.
     *
     * @param source    The object on which the event initially occurred.
     * @param panelName The name of the panel.
     */
    public PanelSwitchEvent(Object source, String panelName) {
        super(source);
        this.panelName = panelName;
    }

    /**
     * Gets the name of the panel associated with this event.
     *
     * @return The name of the panel.
     */
    public String getPanelName() {
        return panelName;
    }
}
