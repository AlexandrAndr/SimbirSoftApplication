package ru.meschanov.mainApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import ru.meschanov.repository.WordsRepository;
import ru.meschanov.service.impl.ApplicationServiceImpl;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);

//        WordsRepository wordsRepository = new WordsRepository() {
//        };

        ApplicationServiceImpl applicationService = new ApplicationServiceImpl();


        applicationService.downloaderHttpPage();
        applicationService.readPage();
        applicationService.countWord();
    }
}
