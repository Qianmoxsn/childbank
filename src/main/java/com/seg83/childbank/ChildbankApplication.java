package com.seg83.childbank;

import com.seg83.childbank.gui.SwingApp;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ChildbankApplication {

    @Autowired
    private SwingApp swingApp;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ChildbankApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE);
        builder.run(args);
    }

    @PostConstruct
    public void initUI() {
        swingApp.createUI();
    }

}
