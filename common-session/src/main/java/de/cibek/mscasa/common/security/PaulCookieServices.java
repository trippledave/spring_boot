/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cibek.mscasa.common.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 *
 * @author DaSee
 */
public class PaulCookieServices extends TokenBasedRememberMeServices {

    public PaulCookieServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
        setAlwaysRemember(true);
        setTokenValiditySeconds(Integer.MAX_VALUE);
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        super.onLoginSuccess(request, response, successfulAuthentication); //let normal service do its job

        String username = retrieveUserName(successfulAuthentication);
        String password = ((String) successfulAuthentication.getCredentials());

        System.out.println("USER " + username + " PW: " + password);
        //set paul cookie stuff
        addPaulCookiesToResponse(username, password, request, response);
    }

    private void addPaulCookiesToResponse(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        final String[] usernameArray = username.split("@");
        addCookieToResponse("user", usernameArray[0], request, response);
        addCookieToResponse("domain", usernameArray[1], request, response);
        addCookieToResponse("pw", password, request, response);
    }

    private void addCookieToResponse(String cookieName, String cookieValue, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("addCookieToResponse " + cookieName + " VA " + cookieValue);
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath(getCookiePath(request));
        cookie.setSecure(request.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
