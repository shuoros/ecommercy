package io.github.shuoros.allAboutSpring.config;

import com.opencsv.CSVReader;
import io.github.shuoros.allAboutSpring.model.dbs.UserRepositorySQL;
import io.github.shuoros.allAboutSpring.model.schema.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;


@Configuration
public class CSVParser implements CommandLineRunner {
    @Autowired
    UserRepositorySQL userRepositorySQL;

    @Value("${default.cv.users.path}") String path;
    @Override
    public void run(String... args) throws Exception {
        try (CSVReader csvReader = new CSVReader(new FileReader(path));) {
            for (String[] strings : csvReader) {
                if(strings.length<2)
                    continue;
                User r = new User(strings[0], strings[1]);
                userRepositorySQL.save(r);
                LoggerFactory.getLogger(CSVParser.class).info("added user {} from cvs file at {}",r,path);

            }
        } catch (IOException e) {
            LoggerFactory.getLogger(CSVParser.class).warn("CouldNot read/find CV file at {} resulting exception: ",path,e);
        }
    }
}
