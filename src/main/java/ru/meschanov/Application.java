package ru.meschanov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.meschanov.service.api.ApplicationService;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        //SpringApplication.run(Application.class, args);

        ApplicationContext context = SpringApplication.run(Application.class, args);
        ApplicationService service = context.getBean(ApplicationService.class);

        service.downloaderHttpPage();
        service.readPage();
        service.countWord();
    }
}



//
////        WordsRepository wordsRepository = new WordsRepository() {
////        };
//
//        ApplicationServiceImpl applicationService = new ApplicationServiceImpl();
//
//
//        applicationService.downloaderHttpPage();
//        applicationService.readPage();
//        applicationService.countWord();