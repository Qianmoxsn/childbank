package com.seg83.childbank;

import com.seg83.childbank.gui.SwingApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ChildbankApplication {

    public static void main(String[] args) {
        try {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(ChildbankApplication.class);
            ApplicationContext ctx = builder.headless(false).web(WebApplicationType.NONE).run(args);

            SwingApp app = ctx.getBean(SwingApp.class);
            app.createUI();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
