package com.prometheus.sisyphus.uaa.security;

import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.repository.UaaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Created by sunliang
 */
@Service("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private UaaUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowcaseUsername = username.toLowerCase();
        Optional<UaaUser> realUser = sysUserRepository.findOneWithRolesByUsername(lowcaseUsername);


        return realUser.map(user -> {
            Set<GrantedAuthority> grantedAuthorities = user.getAuthorities();
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("用户" + lowcaseUsername + "不存在!"));
    }


}
