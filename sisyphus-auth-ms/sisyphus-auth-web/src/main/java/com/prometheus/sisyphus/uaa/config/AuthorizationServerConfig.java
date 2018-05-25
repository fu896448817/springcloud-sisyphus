package com.prometheus.sisyphus.uaa.config;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import java.util.HashMap;
import java.util.Map;

/**
 * sunliang
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore()).accessTokenConverter(jwtAccessTokenConverter()).tokenEnhancer(tokenEnhancerChain());
//        .pathMapping("/oauth/token", "/oauth2/token");

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("web")
                .scopes("read", "write")
                .secret("web")
                .authorizedGrantTypes("password",  "refresh_token","client_credentials").autoApprove(true)
            .and()
                .withClient("webapp")
                .scopes("xx")
                .authorizedGrantTypes("implicit","client_credentials");
    }
    private static class MyTokenEnhancer implements TokenEnhancer {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            logger.info(":>>> MyTokenEnhancer enhance ");
//            Principal principal = (Principal)authentication.getPrincipal();
//            final UaaUser user = uaaUserPrincipal
            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("userId", 1001L);
            additionalInfo.put("tenantId", 1001L);
            additionalInfo.put("roles", "admin");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            logger.debug(":>>> MyTokenEnhancer {$accessToken}:{}", accessToken);
            return accessToken;
        }
    }
    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        logger.info(":>>> tokenEnhancerChain with");
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Lists.newArrayList(new MyTokenEnhancer(), jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        logger.info(":>>> jwtAccessTokenConverter with {$converter}:{}", converter.toString());
        return converter;
    }
}
