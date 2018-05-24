package com.prometheus.sisyphus.uaa.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by tommy on 2017/11/26.
 */
public final class SecurityUtils {


    public static String getCurrentUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
             currentUserName = authentication.getName();

        }
        return currentUserName;
    }

}
