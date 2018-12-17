package com.bitowt.postgenerator.service;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

@Service
public class LnAccessGrantContainer {

    private AccessGrant accessGrant;

    public AccessGrant getAccessGrant() {
        return accessGrant;
    }

    public void setAccessGrant(AccessGrant accessGrant) {
        this.accessGrant = accessGrant;
    }
}
