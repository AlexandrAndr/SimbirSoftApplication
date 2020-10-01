import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.meschanov.domains.WordsEntity;
import ru.meschanov.repository.WordsRepository;
import ru.meschanov.service.impl.ApplicationServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

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

        String filePath = getClass().getResource("file.txt").getPath();

        String expected = "шайтан";

        List<String> actual = service.readPage(filePath);

        assertEquals(expected, actual.get(0));

    }

    @Test
    public void shouldCountWord() {

        List<String> wordList = Arrays.asList("TEST-1", "TEST-1", "TEST-2");
        List<WordsEntity> expectedList = new ArrayList<>();

        expectedList.add(new WordsEntity("TEST-1", 2));
        expectedList.add(new WordsEntity("TEST-2", 1));

        service.countWord(wordList);

        verify(wordsRepository).saveAll(expectedList);

    }
}
