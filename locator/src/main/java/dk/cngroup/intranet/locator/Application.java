package dk.cngroup.intranet.locator;

/**
 * Created by cano on 20.9.2016.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        log.info("Starting everything");
        SpringApplication.run(Application.class, args);
    }

    public static Logger getLogger(){
        return log;
    }
}