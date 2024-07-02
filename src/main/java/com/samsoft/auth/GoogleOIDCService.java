package com.samsoft.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class GoogleOIDCService extends OidcUserService {

    private final UserProfileManager userProfileManager;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("Loading oidc user");
        OidcUser oidcUser = super.loadUser(userRequest);
        log.debug("Loaded Oidc user = {}", oidcUser.getUserInfo());
        return oidcUser;
    }
}
