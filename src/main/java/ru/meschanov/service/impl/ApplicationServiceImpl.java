package ru.meschanov.service.impl;

import org.springframework.stereotype.Service;
import ru.meschanov.domains.WordsEntity;
import ru.meschanov.repository.WordsRepository;
import ru.meschanov.service.api.ApplicationService;
import sun.nio.cs.StandardCharsets;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApplicationServiceImpl implements ApplicationService {


    private List<String> wordList = new ArrayList<String>();

    private WordsRepository wordsRepository;

    public ApplicationServiceImpl(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public ApplicationServiceImpl() {
    }

    /**
     * Метод загрузки HTML-страницы на жесткий диск компьютера
     *
     * @throws IOException
     */
    @Override
    public void downloaderHttpPage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String FILENAME = "D:\\Download_File.html";

        System.out.println("Введите URL HTML-страницы в формате - https://www.HelloWorld.com/:");

        URL url = new URL(reader.readLine());

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILENAME));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().
                     getInputStream(), "UTF-8"))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
            }

            bufferedWriter.close();
            bufferedReader.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Метод чтения загруженной HTML-страницы
     */
    @Override
    public void readPage() {
        String path = "D:\\Download_File.html";

        File file = new File(path);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Pattern rusWords = Pattern.compile("([а-яёА-ЯЁ]+)");
                Matcher matcher = rusWords.matcher(line);

                while (matcher.find()) {
                    wordList.add(matcher.group().toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * Метод подсчета уникальных слов
     */
    @Override
    public void countWord() {

        HashMap<String, Integer> resultUniqueWords = new HashMap<String, Integer>();


        for (String s : wordList) {
            resultUniqueWords.put(s, Collections.frequency(wordList, s));
        }

        //   System.out.println(resultUniqueWords.entrySet());

        for (Map.Entry<String, Integer> pair : resultUniqueWords.entrySet()) {
            String value = pair.getKey() + "-" + pair.getValue();
            System.out.println(value);
            //wordsRepository.save(new WordsEntity())
        }

        System.out.println("Количество уникальных слов на странице - " + resultUniqueWords.size());

        wordsRepository.save(new WordsEntity(resultUniqueWords.keySet().toString(), resultUniqueWords.values().toString()));
    }
}
