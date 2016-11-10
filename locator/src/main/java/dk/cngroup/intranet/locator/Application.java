package dk.cngroup.intranet.locator;

/**
 * Created by cano on 20.9.2016.
 */

import dk.cngroup.intranet.locator.services.storage.StorageProperties;
import dk.cngroup.intranet.locator.services.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        log.info("Starting everything");
        SpringApplication.run(Application.class, args);
    }

    public static Logger getLogger(){
        return log;
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}