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
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import io.github.shuoros.allAboutSpring.filter.JwtRequestFilter;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class Application {

	private static HashMap<String, String> users;
	private final String PATH = "src/main/resources/users.csv";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public static HashMap<String, String> getUsers() {
		return users;
	}

	public static void setUsers(HashMap<String, String> users) {
		Application.users = users;
	}

	public static void addUser(String name, String password) {
		Application.users.put(name, password);
	}

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

	@PreDestroy
	public void onExit() {
		System.out.println("hey");
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

	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)));
	}

	@Bean
	public JwtRequestFilter getFilter() {
		return new JwtRequestFilter();
	}

}
