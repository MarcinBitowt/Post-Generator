package com.bitowt.postgenerator.gui;


import com.bitowt.postgenerator.controller.AppController;
import com.bitowt.postgenerator.service.FacebookService;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringUI(path = "/")
public class MainPage extends UI {
    @Autowired
    FacebookService facebookService;

    @Autowired
    AppController appController;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(startMenu());
    }

    public VerticalLayout startMenu() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label label = new Label("Welcome to Post Generator application " + "\n" +
                "Click the button to login");
        Button button = new Button("Login to Facebook");
        button.addClickListener(clickEvent -> Page.getCurrent().setLocation(appController.createFacebookAuthorization()));
        verticalLayout.addComponents(label, button);
        return verticalLayout;
    }


}
