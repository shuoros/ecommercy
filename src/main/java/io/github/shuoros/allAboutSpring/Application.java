package io.github.shuoros.allAboutSpring;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import io.github.shuoros.allAboutSpring.model.dbs.UserRepositorySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import io.github.shuoros.allAboutSpring.filter.JwtRequestFilter;

/**
 * This application is a set of functions and features of Spring Boot that has
 * been collected as a reference Spring Boot application.
 * 
 * @author Soroush Mehrad
 * @version 1.0.0
 * @since 2021-08-08
 */
@SpringBootApplication
public class Application {

	/**
	 * A hash map that stores users' usernames and passwords.
	 *
	 * @since v1.0.0
	 * @deprecated since v1.0.1
	 * @see UserRepositorySQL
	 */
	private static HashMap<String, String> users;

	@Autowired
	UserRepositorySQL userRepo;


	/**
	 * Path to CSV file containing users.
	 * 
	 * @since v1.0.0
	 */
	private final String PATH = "src/main/resources/users.csv";

	/**
	 * Application executor.
	 * 
	 * @param args java args.
	 * @since v1.0.0
	 */
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("IRDT"));
		SpringApplication.run(Application.class, args);
	}

	/**
	 * @return <code>JwtRequestFilter</code> bean.
	 * @since v1.0.0
	 */
	@Bean
	public JwtRequestFilter getFilter() {
		return new JwtRequestFilter();
	}

}
