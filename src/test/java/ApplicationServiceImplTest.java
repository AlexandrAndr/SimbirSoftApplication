
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.meschanov.domains.WordsEntity;
import ru.meschanov.repository.WordsRepository;
import ru.meschanov.service.impl.ApplicationServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {

    @Mock
    private WordsRepository wordsRepository;

    @InjectMocks
    private ApplicationServiceImpl service;

    @Test
    public void shouldSavePageToFileTest() throws IOException {
        //Подготовка
        String filePath = getClass().getResource("downloadFile.txt").getPath();
        InputStream stream = getClass().getResourceAsStream("page.html");
        String expected = "<html>\\n\\r<body>\\n\\r<div>\\n\\r    Шайтан\\n\\r</div>\\n\\r</body>\\n\\r</html>\\n\\r";

        //Запуск
        service.savePageToFile(filePath, stream);

        //Проверка
        String actual = IOUtils.toString(getClass().getResourceAsStream("downloadFile.txt"), "UTF-8");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReadPageTest() throws IOException {

       // String filePath = "C:/Users/79997/SimbirSoftApplication/target/test-classes/downloadFile.txt";
        String filePath = "C:\\Users\\79997\\SimbirSoftApplication\\src\\test\\resources\\downloadFile.txt";

        service.readPage(filePath);

        String expected = "шайтан";

        String actual = IOUtils.toString(getClass().getResourceAsStream("downloadFile.txt"), "UTF-8");

        assertEquals(expected, actual);

    }

    @Test
    public void shouldCountWord() {

        String value = null;

        List<String> wordList = new ArrayList<>();
        List<WordsEntity> words = new ArrayList<>();
        Map<String, Integer> resultUniqueWords = new HashMap<>();

        wordList.add("СимбирСофт");

        for (String s : wordList) {
            resultUniqueWords.put(s, Collections.frequency(wordList, s));
        }

        for (Map.Entry<String, Integer> pair : resultUniqueWords.entrySet()) {
            value = pair.getKey() + "-" + pair.getValue();

            words.add(new WordsEntity(pair.getKey(), pair.getValue()));
        }

        String expected = "СимбирСофт-1";
        String actual = value;

        service.countWord(wordList);

        assertEquals(expected, actual);
    }
}
