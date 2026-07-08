package br.com.florentino.repository;

import br.com.florentino.utils.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FormRepository {
    public List<String> readForm() throws IOException {
        return Files.readAllLines(Path.of(Constants.FORM_PATH));
    }
}
