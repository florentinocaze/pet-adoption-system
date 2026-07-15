package br.com.florentino.repository;

import br.com.florentino.utils.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FormRepository {
    public List<String> readForm() throws IOException {
        return Files.readAllLines(Path.of(Constants.FORM_PATH));
    }

    public void appendToForm(String question) {
        Path formFilePath = Paths.get(Constants.FORM_PATH);

        long fileLinesAmount;

        try (Stream<String> lines = Files.lines(formFilePath)) {
            fileLinesAmount = lines.count();
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo.");
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(formFilePath.toFile(), StandardCharsets.UTF_8, true))) {
            bufferedWriter.newLine();
            bufferedWriter.write((fileLinesAmount + 1) + " – " + question);
        } catch (IOException e) {
            System.out.println("Erro ao adicionar pergunta ao formulário.");
        }
    }
}
