package br.com.florentino.services;

import br.com.florentino.repository.FormRepository;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FormService {
    private final FormRepository formRepository;
    private final Scanner scanner;

    public FormService(FormRepository formRepository, Scanner scanner) {
        this.formRepository = formRepository;
        this.scanner = scanner;
    }

    public List<String> getQuestions() throws IOException {
        return formRepository.readForm();
    }

    public void addQuestionToForm() {
        while (true) {
            System.out.print("Insira a pergunta que você deseja adicionar ao formulário: ");
            String questionToAdd = scanner.nextLine();

            formRepository.appendToForm(questionToAdd);

            System.out.println("Você deseja fazer mais alguma mudança?");
            System.out.println("[1] Sim;");
            System.out.println("[2] Não.");
            System.out.print("Escolha uma opção: ");

            String option = scanner.nextLine();

            if (option.equals("2")) {
                break;
            }

            if (!option.equals("1")) {
                System.out.println("Opção inválida.");
            }
        }
    }
}
