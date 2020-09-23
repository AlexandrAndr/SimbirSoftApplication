package ru.meschanov.service.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public interface ApplicationService {

    /** Метод считывает URL из кончоли
     *
     * @return url
     */
    URL readUrlFromConsole();

    /**
     * Метод загрузки HTML-мтраницы на жесткий диск
     *
     * @param filePath - путь до файла
     * @param content      - контент web - страницы
     * @throws IOException - исключение в случае отсутствия файла
     */
    void savePageToFile(String filePath, InputStream content) throws IOException;

    /**
     * Метод чтения загруженной HTML- страницы
     *
     * @param filePath - путь до файла
     * @return - список слов
     */
    List<String> readPage(String filePath);

    /**
     * Метод подсчета уникальных слов
     *
     * @param wordList - список слов
     */
    void countWord(List<String> wordList);
}
