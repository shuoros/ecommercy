package io.github.shuoros.allAboutSpring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
	 */
	private static HashMap<String, String> users;

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
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Users getter.
	 * 
	 * @return A hash map contains users.
	 * @since v1.0.0
	 */
	public static HashMap<String, String> getUsers() {
		return users;
	}

	/**
	 * Add new user in hash map.
	 * 
	 * @param name     Name of the user.
	 * @param password Password of the user.
	 * @since v1.0.0
	 */
	public static void addUser(String name, String password) {
		Application.users.put(name, password);
	}

	/**
	 * This function runs after the main application is created and loads the users
	 * saved in the CSV file on the <code>users</code>.
	 * 
	 * @since v1.0.0
	 */
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("IRDT"));

		Application.users = new HashMap<>();

		try (CSVReader csvReader = new CSVReader(new FileReader(PATH));) {
			String[] values = null;
			while ((values = csvReader.readNext()) != null) {
				users.put(Arrays.asList(values).get(0), Arrays.asList(values).get(1));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function runs before the main application is shot down and saves all
	 * users in the <code>users</code>, in the users CSV file.
	 * 
	 * @since v1.0.0
	 */
	@PreDestroy
	public void onExit() {
		File file = new File(PATH);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			users.forEach((name, password) -> {
				data.add(new String[] { name, password });
			});
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
