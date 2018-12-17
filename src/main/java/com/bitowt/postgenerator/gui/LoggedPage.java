package com.bitowt.postgenerator.gui;


import com.bitowt.postgenerator.service.FbAccessGrantContainer;
import com.bitowt.postgenerator.controller.AppController;
import com.bitowt.postgenerator.service.FacebookService;
import com.bitowt.postgenerator.service.LinkedinService;
import com.bitowt.postgenerator.service.LnAccessGrantContainer;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;

@SpringUI(path = "/logged")
public class LoggedPage extends UI {

    private LinkedIn linkedIn;

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private LinkedinService linkedinService;

    @Autowired
    private AppController appController;

    @Autowired
    private FbAccessGrantContainer fbAccessGrantContainer;

    @Autowired
    private LnAccessGrantContainer lnAccessGrantContainer;

    @Autowired
    private MainPage mainPage;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //validateFacebook(vaadinRequest);
        validateLinkedin(vaadinRequest);
    }

    private void validateFacebook(VaadinRequest vaadinRequest) {
        if (fbAccessGrantContainer.getAccessGrant() != null) {
            setContent(loggedPageFbView());
        } else {
            String code = vaadinRequest.getParameter("code");
            if (code != null) {
                facebookService.createFacebookAccessToken(code);
                setContent(loggedPageFbView());
            } else {
                Page.getCurrent().setLocation("http://localhost:8080");
            }
        }
    }

    private void validateLinkedin(VaadinRequest vaadinRequest) {
        if (lnAccessGrantContainer.getAccessGrant() != null) {
            setContent(loggedPageLinkedInView());
        } else {
            String code = vaadinRequest.getParameter("code");
            if (code != null) {
                linkedinService.createLinkedinAccessToken(code);
                setContent(loggedPageLinkedInView());
            } else {
                Page.getCurrent().setLocation("http://localhost:8080");
            }
        }
    }

    public VerticalLayout loggedPageFbView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label welcomeLabel = new Label("Hi, " + facebookService.getFacebookUser().getName());
        Label encourage = new Label("Add your post to Facebook");
        TextArea textArea = new TextArea();
        Button addPostButton = new Button("Send");
        Button fileUpload = new Button("Upload photo");
        //FileUpload fileUpload= new FileUpload();

        addPostButton.addClickListener(clickEvent -> Notification.show("Post sent", Notification.Type.TRAY_NOTIFICATION));
        addPostButton.addClickListener(e -> facebookService.post());


        verticalLayout.addComponents(welcomeLabel, encourage, textArea);
        horizontalLayout.addComponents(fileUpload, addPostButton);
        verticalLayout.addComponent(horizontalLayout);
        return verticalLayout;
    }

    public VerticalLayout loggedPageLinkedInView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        linkedIn = new LinkedInTemplate(lnAccessGrantContainer.getAccessGrant().getAccessToken());

        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label welcomeLabel = new Label("Hi, " + linkedIn.profileOperations().getUserProfile().getFirstName());
        Label encourage = new Label("Add your post");
        TextArea textArea = new TextArea();
        Button addPostButton = new Button("Send");
        Button fileUpload = new Button("Upload photo");
        //FileUpload fileUpload= new FileUpload();

        addPostButton.addClickListener(clickEvent -> Notification.show("Post sent", Notification.Type.TRAY_NOTIFICATION));
        addPostButton.addClickListener(clickEvent -> linkedinService.addPostTest());


        verticalLayout.addComponents(welcomeLabel, encourage, textArea);
        horizontalLayout.addComponents(fileUpload, addPostButton);
        verticalLayout.addComponent(horizontalLayout);
        return verticalLayout;
    }
}
