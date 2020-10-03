package ru.meschanov;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.meschanov.service.api.ApplicationService;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@SpringBootApplication
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private static final String INVALID_ARGUMENT_LIST_ERROR_TEXT = "Невалидный набор параметров запуска";
    private static final String INVALID_URL_TEXT = "Невалидный URL";
    private static final Integer USE_CONSOLE_NUMBER_PARAM = 0;
    private static final Integer ARGUMENT_URL_NUMBER_PARAM = 1;

    public static void main(String[] args) throws IOException {

        ApplicationContext context = SpringApplication.run(Application.class, args);

        ApplicationService service = context.getBean(ApplicationService.class);

        URL url;
        if (!isArgsValid(args)) {
            throw new RuntimeException(INVALID_ARGUMENT_LIST_ERROR_TEXT);
        }

        String useConsole = args[USE_CONSOLE_NUMBER_PARAM];

        if (Boolean.parseBoolean(useConsole)) {
            url = service.readUrlFromConsole();
        } else if (service.urlValidation(args[ARGUMENT_URL_NUMBER_PARAM])) {
            url = new URL(args[ARGUMENT_URL_NUMBER_PARAM]);
        } else {
            throw new RuntimeException(INVALID_URL_TEXT);
        }

        String filePath = service.readPathDownload();

        service.savePageToFile(filePath, url.openConnection().getInputStream());

        List<String> words = service.readPage(filePath);

        service.countWord(words);
    }

    private static boolean isArgsValid(String[] args) {
        boolean result = true;
        if (args.length == 0) {
            result = false;
        } else if (args.length == 1 && !Boolean.parseBoolean(args[USE_CONSOLE_NUMBER_PARAM])) {
            result = false;
        }
        return result;
    }
}
