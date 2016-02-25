package com.romainehinds.backend.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Value("${spring.application.name}")
	String appName;
	
	@Value("${security.oauth2.client.clientId}")
	String clientId;
	
	@Value("${security.oauth2.client.clientSecret}")
	String clientSecret;
	
	@Autowired
	AuthenticationManagerBuilder authenticationManager;
	
	@Autowired
	OAuth2AccessTokenRepository accessTokenRepository;
	
	@Autowired
	OAuth2RefreshTokenRepository refreshTokenRepository;
	
	@Bean
	public TokenStore tokenStore(){
		return new OAuth2RepositoryTokenStore(accessTokenRepository, refreshTokenRepository);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.tokenStore(tokenStore());
//			endpoints.authenticationManager(new AuthenticationManager() {
//			@Override
//			public Authentication authenticate(Authentication authentication)
//					throws AuthenticationException {
//				return authenticationManager.getOrBuild().authenticate(authentication);
//			}
//		});
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()
			.withClient(clientId)
			.authorities("ROLE_USER")
			.resourceIds(appName)
			.scopes("read", "write")
			.authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
			.secret(clientSecret);
	}
}

