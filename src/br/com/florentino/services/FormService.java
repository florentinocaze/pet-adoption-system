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
            System.out.println();

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

    public void replaceQuestionFromForm() throws IOException {
        List<String> questions = getQuestions();

        while (true) {
            if (questions.size() > 7) {
                System.out.println("Perguntas disponíveis no formulário para alteração: ");

                for (int i = 7; i < questions.size(); i++) {
                    System.out.println(questions.get(i));
                }
                System.out.println();

                System.out.print("Insira o índice da pergunta que você deseja alterar: ");
                int questionIndex = Integer.parseInt(scanner.nextLine());
                System.out.println();

                if (questionIndex <= 7 || questionIndex > questions.size()) {
                    System.out.println("O índice da pergunta selecionada é inválido. Tente novamente.");
                    System.out.println();

                    continue;
                }

                System.out.print("Insira a nova pergunta: ");
                String newQuestion = scanner.nextLine();
                System.out.println();

                formRepository.replaceFromForm(questionIndex - 1, newQuestion);

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
            } else {
                System.out.println("Você ainda não criou perguntas no formulário.");
                break;
            }
        }
    }

    public void deleteQuestionFromForm() throws IOException {
        List<String> questions = getQuestions();

        if (questions.size() > 7) {
            while (true) {
                System.out.println("Perguntas disponíveis no formulário para exclusão: ");

                for (int i = 7; i < questions.size(); i++) {
                    System.out.println(questions.get(i));
                }
                System.out.println();

                System.out.print("Insira o índice da pergunta que você deseja excluir: ");
                int questionIndex;

                try {
                    questionIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Número inválido. Tente novamente.");
                    continue;
                }

                if (questionIndex <= 7 || questionIndex > questions.size()) {
                    System.out.println("O índice da pergunta selecionada é inválido. Tente novamente.");
                    System.out.println();
                    continue;
                }

                formRepository.deleteFromForm(questionIndex - 1);
                System.out.println();

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
        } else {
            System.out.println("Você ainda não criou perguntas no formulário.");
        }
    }
}
