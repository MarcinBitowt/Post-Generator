package com.bitowt.postgenerator.gui;


import com.bitowt.postgenerator.controller.AppController;
import com.bitowt.postgenerator.service.FacebookService;
import com.bitowt.postgenerator.service.InstagramService;
import com.bitowt.postgenerator.service.LinkedinService;
import com.bitowt.postgenerator.service.TwitterService;
import com.vaadin.event.MouseEvents;
import com.vaadin.icons.VaadinIcons;
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
    private FacebookService facebookService;

    @Autowired
    private LinkedinService linkedinService;

    @Autowired
    private InstagramService instagramService;

    @Autowired
    private TwitterService twitterService;

    @Autowired
    AppController appController;

    private Button faceButton = new Button("Login to Facebook");

    private Button linkedButton = new Button("Login to Linkedin");

    private Button instaButton = new Button("Login to Instagram");

    private Button twittButton = new Button("Login to Twitter");

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(startMenu());
    }

    public VerticalLayout startMenu() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        verticalLayout.setResponsive(true);

        Label label = new Label("Welcome to Post Generator application ");
        Label label1 = new Label("Click the button to login");

        faceButton.setWidth(Float.parseFloat("180f"), Unit.PIXELS);
        linkedButton.setWidth(Float.parseFloat("180f"), Unit.PIXELS);
        instaButton.setWidth(Float.parseFloat("180f"), Unit.PIXELS);
        twittButton.setWidth(Float.parseFloat("180f"), Unit.PIXELS);


        if (!facebookService.getAccessToken().equals(""))
            faceButton.setIcon(VaadinIcons.CHECK);

        if (!linkedinService.getAccessToken().equals(""))
            linkedButton.setIcon(VaadinIcons.CHECK);

        if (!instagramService.getAccessToken().equals(""))
            instaButton.setIcon(VaadinIcons.CHECK);

        faceButton.addClickListener(clickEvent -> Page.getCurrent().setLocation(appController.createFacebookAuthorization()));
        linkedButton.addClickListener(clickEvent -> Page.getCurrent().setLocation(appController.createLinkedinAuthorization()));
        instaButton.addClickListener(clickEvent -> Page.getCurrent().setLocation(appController.createInstagramAuthorization()));
        //twittButton.addClickListener(clickEvent -> Page.getCurrent().setLocation(appController.createTwitterAuthorization()));

        verticalLayout.addComponents(label, label1, faceButton, linkedButton, instaButton, twittButton);
        return verticalLayout;
    }

}
