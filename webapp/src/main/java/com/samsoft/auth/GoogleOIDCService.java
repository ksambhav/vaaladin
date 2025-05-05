package com.samsoft.auth;

import com.samsoft.auth.model.BasicUserProfile;
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

    private final OpenIdLoginHandler openIdLoginHandler;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final var oidcUser = super.loadUser(userRequest);
        String fullName = oidcUser.getFullName();
        String email = oidcUser.getEmail();
        String profilePic = oidcUser.getPicture();
        var basicUserProfile = new BasicUserProfile(fullName, email, profilePic);
        openIdLoginHandler.handleLogin(basicUserProfile);
        return oidcUser;
    }
}