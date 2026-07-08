package br.com.florentino.services;

import br.com.florentino.repository.FormRepository;

import java.io.IOException;
import java.util.List;

public class FormService {
    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public List<String> getQuestions() throws IOException {
        return formRepository.readForm();
    }
}
