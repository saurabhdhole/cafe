package com.mm.ms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mm.ms.data.repo.UserRepository;
import com.mm.ms.entity.User;
import com.mm.ms.util.Const;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class FoMsApplication extends SpringBootServletInitializer {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		if (userRepository.count() <= 0){
			logger.info(Const.LOGSTR_FOMS + "creating default user " + Const.DEFAULT_USER);
			return (evt) -> {
				userRepository.save(new User(Const.DEFAULT_USER));
			};
		}else{
			logger.info(Const.LOGSTR_FOMS + "default user data already populated in db.");
		}
		return null;
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.info(Const.LOGSTR_FOMS + "initializing food order ms rest api.");
		return application.sources(FoMsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FoMsApplication.class, args);
	}
	
	
}