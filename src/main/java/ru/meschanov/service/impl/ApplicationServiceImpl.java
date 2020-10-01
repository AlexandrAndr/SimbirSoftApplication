package ru.meschanov.service.impl;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.meschanov.domains.WordsEntity;
import ru.meschanov.repository.WordsRepository;
import ru.meschanov.service.api.ApplicationService;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private static final String EMPTY_URL_ERROR_TEXT = "Не задан URL";

    private static final Logger LOGGER = Logger.getLogger(ApplicationServiceImpl.class.getName());

    /**
     * Репозиторий
     */
    private WordsRepository wordsRepository;


    @Override
    public boolean urlValidation(String argument) {
        if (argument == null) {
            throw new RuntimeException(EMPTY_URL_ERROR_TEXT);
        }
        Pattern pattern = Pattern.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(argument);

        return matcher.find();
    }

    @Override
    public URL readUrlFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        LOGGER.info("Указываем Html- страницу");

        URL url = null;

        while (url == null) {
            LOGGER.info("Введите URL HTML-страницы в формате - https://www.HelloWorld.com/:");
            try {
                String line = bufferedReader.readLine();
                if (urlValidation(line)) {
                    url = new URL(line);
                } else {
                    LOGGER.error("Невалидный URL");
                }
            } catch (IOException e) {
                LOGGER.error("Ошибка чтения URL", e);
                throw new RuntimeException(e);
            }
        }
        return url;
    }

    @Override
    public void savePageToFile(String filePath, InputStream content) {

        LOGGER.debug(String.format("Начинаем запись в файл %s", filePath));

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.write("\\n\\r");
            }

        } catch (IOException exception) {
            LOGGER.error("Ошибка при записи в файл", exception);
        }
        LOGGER.debug(String.format("Запись в файл %s успешно завершена", filePath));
    }

    @Override
    public List<String> readPage(String filePath) {
        List<String> wordList = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Pattern rusWords = Pattern.compile("([а-яёА-ЯЁ]+)");
                Matcher matcher = rusWords.matcher(line);

                while (matcher.find()) {
                    wordList.add(matcher.group().toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Ошибка чтения из файла", e);
        }

        return wordList;
    }

    @Override
    public void countWord(List<String> wordList) {

        List<WordsEntity> words = new ArrayList<>();
        HashMap<String, Integer> resultUniqueWords = new HashMap<>();

        for (String s : wordList) {
            resultUniqueWords.put(s, Collections.frequency(wordList, s));
        }

        for (Map.Entry<String, Integer> pair : resultUniqueWords.entrySet()) {
            String value = pair.getKey() + "-" + pair.getValue();
            LOGGER.info(value);
            words.add(new WordsEntity(pair.getKey(), pair.getValue()));
        }

        LOGGER.info("Количество уникальных слов на странице - " + resultUniqueWords.size());

        wordsRepository.saveAll(words);
    }
}
