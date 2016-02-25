package com.romainehinds.backend;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@SpringBootApplication
@RestController
@EnableZuulProxy
public class TsmApplication implements CommandLineRunner{

	@RequestMapping(value = "/home/{name}",  method = RequestMethod.GET)
	public String greeting(Principal principal, @PathVariable("name") String name) {
		return "Hello, " + name + " : " + principal.toString();
	}
	
	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TsmApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		userRepository.deleteAll();
		User user = new User("user1", "password", "John", "Doe", "John.Doe@email.com");
		user = userRepository.save(user);
		System.out.println("Created User:\n" + user);
	}
	
//	@Bean
//	public SimpleFilter simpleFilter() {
//		return new SimpleFilter();
//	}
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
