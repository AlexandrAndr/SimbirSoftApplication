package ru.meschanov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.meschanov.service.api.ApplicationService;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@SpringBootApplication
public class Application {

    private static final String FILE_PATH = "D:\\Download_File.html";

    public static void main(String[] args) throws IOException {

        ApplicationContext context = SpringApplication.run(Application.class, args);

        ApplicationService service = context.getBean(ApplicationService.class);

        URL url = new URL(args[0]);

        service.savePageToFile(FILE_PATH, url.openConnection().getInputStream());

        List<String> words = service.readPage(FILE_PATH);

        service.countWord(words);
    }
}
