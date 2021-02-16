package br.com.acalapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
