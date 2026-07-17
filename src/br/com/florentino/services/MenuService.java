package br.com.florentino.services;

import br.com.florentino.entity.Pet;
import br.com.florentino.repository.FormRepository;
import br.com.florentino.repository.PetRepository;
import br.com.florentino.utils.StringFormatter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void initiate() {
        FormRepository formRepository = new FormRepository();
        FormService formService = new FormService(formRepository, scanner);
        PetRepository petRepository = new PetRepository();
        PetService petService = new PetService(petRepository, formService, scanner);

        while (true) {
            System.out.println();
            System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
            System.out.println();

            System.out.println("Seja bem-vindo ao menu inicial do Sistema de Adoção de Pets.");
            System.out.println("Qual das funções abaixo você deseja executar?");
            System.out.println();

            System.out.println("[1] Iniciar cadastro de pets;");
            System.out.println("[2] Iniciar alteração de formulário;");
            System.out.println("[3] Sair do programa.");
            System.out.println();

            System.out.print("Escolha uma opção: ");
            String startMenuOption = scanner.nextLine();

            switch (startMenuOption) {
                case "1" -> {
                    boolean isMainMenuLooping = true;

                    while (isMainMenuLooping) {
                        System.out.println();
                        System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                        System.out.println();

                        System.out.println("[MENU DE CADASTRO DE PETS]");
                        System.out.println();

                        System.out.println("Qual das funções abaixo você deseja executar?");
                        System.out.println();

                        System.out.println("[1] Cadastrar um novo pet;");
                        System.out.println("[2] Alterar os dados do pet cadastrado;");
                        System.out.println("[3] Deletar um pet cadastrado;");
                        System.out.println("[4] Listar todos os pets cadastrados;");
                        System.out.println("[5] Listar pets por um ou dois critérios (nome, idade e/ou raça);");
                        System.out.println("[6] Voltar ao menu inicial;");
                        System.out.println("[7] Sair do programa.");
                        System.out.println();

                        System.out.print("Escolha uma opção: ");
                        String mainMenuOption = scanner.nextLine();

                        switch (mainMenuOption) {
                            case "1" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[CADASTRO DE PET]");

                                try {
                                    petService.registerPet();
                                } catch (IOException e) {
                                    System.out.println("Falha ao registrar pet. Tente novamente.");
                                }
                            }
                            case "2" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[ALTERAÇÃO DE DADO(S) DE PET CADASTRADO]");
                                System.out.println();

                                try {
                                    petService.updatePet();
                                } catch (IOException e) {
                                    System.out.println("Falha ao alterar dados do pet. Tente novamente.");
                                }
                            }
                            case "3" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[EXCLUSÃO DE CADASTRO DE PET]");

                                try {
                                    petService.deletePet();
                                } catch (IOException e) {
                                    System.out.println("Falha ao deletar cadastro do pet. Tente novamente.");
                                }
                            }
                            case "4" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[LISTAGEM DE PETS CADASTRADOS]");

                                List<Pet> pets = petService.listAllPets();
                                int index = 1;

                                System.out.println();

                                System.out.println("[PETS ENCONTRADOS]");

                                for (Pet pet : pets) {
                                    System.out.println(StringFormatter.formatPetDataForDisplay(index++, pet));
                                }
                            }
                            case "5" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[LISTAGEM DE PETS POR CRITÉRIO]");

                                try {
                                    List<Pet> filteredPets = petService.listPetsByCriteria();
                                    int index = 1;

                                    System.out.println();

                                    if (filteredPets.isEmpty()) {
                                        System.out.println("Falha ao buscar pets. Tente novamente.");
                                    } else {
                                        System.out.println("[PETS ENCONTRADOS]");

                                        for (Pet pet : filteredPets) {
                                            System.out.println(StringFormatter.formatPetDataForDisplay(index++, pet));
                                        }
                                    }
                                } catch (IOException e) {
                                    System.out.println("Falha ao buscar pets. Tente novamente.");
                                }
                            }
                            case "6" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[VOLTANDO AO MENU INICIAL]");
                                isMainMenuLooping = false;
                            }
                            case "7" -> {
                                System.out.println("[FINALIZANDO PROGRAMA]");
                                scanner.close();
                                return;
                            }
                            default -> {
                                System.out.println("Opção inválida. Tente novamente.");
                                System.out.println();
                            }
                        }
                    }
                }
                case "2" -> {
                    boolean isAlterFormLooping = true;

                    while (isAlterFormLooping) {
                        System.out.println();
                        System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                        System.out.println();

                        System.out.println("[MENU DE ALTERAÇÃO DE FORMULÁRIO]");
                        System.out.println();

                        System.out.println("Qual das funções abaixo você deseja executar?");
                        System.out.println();

                        System.out.println("[1] Adicionar pergunta ao formulário;");
                        System.out.println("[2] Alterar pergunta do formulário;");
                        System.out.println("[3] Deletar pergunta do formulário;");
                        System.out.println("[4] Voltar para o menu inicial;");
                        System.out.println("[5] Sair do programa.");
                        System.out.println();

                        System.out.print("Escolha uma opção: ");
                        String formMenuOption = scanner.nextLine();

                        switch (formMenuOption) {
                            case "1" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[CRIAÇÃO DE PERGUNTA]");
                                System.out.println();

                                formService.addQuestionToForm();
                            }
                            case "2" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[ALTERAÇÃO DE PERGUNTA]");
                                System.out.println();

                                try {
                                    formService.replaceQuestionFromForm();
                                } catch (IOException e) {
                                    System.out.println("Erro ao alterar pergunta do formulário. Tente novamente.");
                                }
                            }
                            case "3" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[EXCLUSÃO DE PERGUNTA]");
                                System.out.println();

                                try {
                                    formService.deleteQuestionFromForm();
                                } catch (IOException e) {
                                    System.out.println("Erro ao deletar pergunta do formulário. Tente novamente.");
                                }
                            }
                            case "4" -> {
                                System.out.println();
                                System.out.println("\033[0;1m[SISTEMA DE ADOÇÃO DE PETS]\033[0;0m");
                                System.out.println();

                                System.out.println("[VOLTANDO AO MENU INICIAL]");
                                isAlterFormLooping = false;
                            }
                            case "5" -> {
                                System.out.println("[FINALIZANDO PROGRAMA]");
                                scanner.close();
                                return;
                            }
                            default -> {
                                System.out.println("Opção inválida. Tente novamente.");
                                System.out.println();
                            }
                        }
                    }
                }
                case "3" -> {
                    System.out.println("[FINALIZANDO PROGRAMA]");
                    return;
                }
                default -> {
                    System.out.println("Opção inválida. Tente novamente.");
                    System.out.println();
                }
            }
        }
    }
}