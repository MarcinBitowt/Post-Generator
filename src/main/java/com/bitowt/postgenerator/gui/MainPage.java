package com.bitowt.postgenerator.gui;


import com.bitowt.postgenerator.controller.AppController;
import com.bitowt.postgenerator.service.FacebookService;
import com.bitowt.postgenerator.service.LinkedinService;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringUI(path = "/")
public class MainPage extends UI {
    @Autowired
    FacebookService facebookService;

    @Autowired
    LinkedinService linkedinService;

    @Autowired
    AppController appController;

    private Button faceButton = new Button("Login to Facebook");

    private Button linkedButton = new Button("Login to Linkedin");

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(startMenu());
    }

    public VerticalLayout startMenu() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label label = new Label("Welcome to Post Generator application " + "\n" +
                "Click the button to login");

        faceButton.addClickListener(clickEvent -> Page.getCurrent().setLocation(appController.createFacebookAuthorization()));
        linkedButton.addClickListener(clickEvent -> Page.getCurrent().setLocation(appController.createLinkedinAuthorization()));

        verticalLayout.addComponents(label, faceButton,linkedButton);
        return verticalLayout;
    }
    public Button getFaceButton() {
        return faceButton;
    }

    public Button getLinkedButton() {
        return linkedButton;
    }

}
