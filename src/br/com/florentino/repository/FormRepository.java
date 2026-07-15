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

    public void replaceFromForm(int index, String question) {
        Path formFilePath = Paths.get(Constants.FORM_PATH);

        try {
            List<String> lines = Files.readAllLines(formFilePath, StandardCharsets.UTF_8);

            if (index >= 0 && index < lines.size()) {
                lines.set(index, (index + 1) + " – " + question);
            }

            List<String> cleanedLines = new ArrayList<>();

            for (String line : lines) {
                String trimmedLine = line.replace("\n", "").replace("\r", "").trim();
                if (!trimmedLine.isEmpty()) {
                    cleanedLines.add(trimmedLine);
                }
            }

            String finalContent = String.join(System.lineSeparator(), cleanedLines);

            Files.writeString(formFilePath, finalContent, StandardCharsets.UTF_8);

            System.out.println("Pergunta alterada com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao alterar pergunta.");
        }
    }

    public void deleteFromForm(int index) {
        Path formFilePath = Paths.get(Constants.FORM_PATH);

        try {
            List<String> lines = Files.readAllLines(formFilePath, StandardCharsets.UTF_8);

            lines.remove(index);

            for (int i = 0; i < lines.size(); i++) {
                String[] splittedLine = lines.get(i).split(" – ");
                lines.set(i, (i + 1) + " – " + splittedLine[1]);
            }

            List<String> cleanedLines = new ArrayList<>();

            for (String line : lines) {
                String trimmedLine = line.replace("\n", "").replace("\r", "").trim();
                if (!trimmedLine.isEmpty()) {
                    cleanedLines.add(trimmedLine);
                }
            }

            String finalContent = String.join(System.lineSeparator(), cleanedLines);

            Files.writeString(formFilePath, finalContent, StandardCharsets.UTF_8);

            System.out.println("Pergunta removida com sucesso!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erro ao buscar índice da pergunta selecionada.");
        } catch (IOException e) {
            System.out.println("Erro ao remover a pergunta selecionada.");
        }
    }
}
