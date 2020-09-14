package ru.meschanov.service.api;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface ApplicationService {
    void downloaderHttpPage() throws IOException;
    void readPage();
    void countWord();
}
