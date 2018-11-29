package com.bitowt.postgenerator.gui;


import com.bitowt.postgenerator.service.AccessGrantContainer;
import com.bitowt.postgenerator.controller.AppController;
import com.bitowt.postgenerator.service.FacebookService;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/logged")
public class LoggedPage extends UI {
    @Autowired
    FacebookService facebookService;

    @Autowired
    AppController appController;

    @Autowired
    AccessGrantContainer accessGrantContainer;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        validate(vaadinRequest);
    }

    private void validate(VaadinRequest vaadinRequest)
    {
        if (accessGrantContainer.getAccessGrant() != null) {
            setContent(loggedPageView());
        } else {
            String code = vaadinRequest.getParameter("code");
            if (code != null) {
                facebookService.createFacebookAccessToken(code);
                setContent(loggedPageView());
            } else {
                Page.getCurrent().setLocation("http://localhost:8080");
            }
        }
    }

    public VerticalLayout loggedPageView() {
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
        //addPostButton.addClickListener(e -> facebookService.postStatusOnPage());
        addPostButton.addClickListener(e -> textArea.setCaption(facebookService.getFacebookUser().getBirthday().toUpperCase()));

        verticalLayout.addComponents(welcomeLabel, encourage, textArea);
        horizontalLayout.addComponents(fileUpload, addPostButton);
        verticalLayout.addComponent(horizontalLayout);
        return verticalLayout;
    }
}
