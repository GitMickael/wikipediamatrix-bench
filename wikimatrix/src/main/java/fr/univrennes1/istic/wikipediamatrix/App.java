package fr.univrennes1.istic.wikipediamatrix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    // Logger
    public static final Logger LOGGER = LogManager.getLogger(App.class);

    // Lancement de l'API web
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}