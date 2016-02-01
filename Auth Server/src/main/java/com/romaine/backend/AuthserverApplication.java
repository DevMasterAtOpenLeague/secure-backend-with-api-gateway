package com.romaine.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@SpringBootApplication
@RestController
@EnableAuthorizationServer
public class AuthserverApplication extends WebMvcConfigurerAdapter implements CommandLineRunner{

	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		userRepository.deleteAll();
		User user = new User("user1", "password", "John", "Doe", "John.Doe@email.com");
		user = userRepository.save(user);
		System.out.println("Created User:\n" + user);
	}
}



@Configuration
@EnableAuthorizationServer
class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

	@Value("${spring.application.name}")
	String appName;
	
	@Value("${security.oauth2.client.clientId}")
	String clientId;
	
	@Value("${security.oauth2.client.clientSecret}")
	String clientSecret;
	
	@Autowired
	AuthenticationManagerBuilder authenticationManager;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
			endpoints.authenticationManager(new AuthenticationManager() {
			@Override
			public Authentication authenticate(Authentication authentication)
					throws AuthenticationException {
				return authenticationManager.getOrBuild().authenticate(authentication);
			}
		});
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient(clientId)
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.authorities("ROLE_USER").scopes("write").resourceIds(appName)
				.secret(clientSecret);
	}
}

@Configuration
@EnableMongoRepositories
class MongoConfig extends AbstractMongoConfiguration{
	
	@Value("${authserver.dbName}")
	private String dbName;
	
	@Value("${authserver.dbURI}")
	private String dbURI;
	
	@Override
	protected String getDatabaseName() {
		return dbName;
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(new MongoClientURI(dbURI));
	}
	
	@Override
	protected String getMappingBasePackage() {
		return  "tivitUI.model";
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception{
		return new MongoTemplate(mongo(), getDatabaseName());
	}
	
}


@SuppressWarnings("serial")
class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String userId) {
		super("could not find user '" + userId + "'.");
	}
}
