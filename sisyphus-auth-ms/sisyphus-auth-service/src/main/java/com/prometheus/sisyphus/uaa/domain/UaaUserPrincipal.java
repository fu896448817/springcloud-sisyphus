package com.prometheus.sisyphus.uaa.domain;

import com.prometheus.sisyphus.uaa.entity.UaaUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
/**
 * Created by tommy on 2017/11/26.
 */
public class UaaUserPrincipal implements UserDetails {
    private UaaUser user;

    public UaaUserPrincipal(UaaUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UaaUser getUser() {
        return user;
    }

    public void setUser(UaaUser user) {
        this.user = user;
    }
}
