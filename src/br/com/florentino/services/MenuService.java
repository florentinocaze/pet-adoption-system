package br.com.florentino.services;

import br.com.florentino.entity.Pet;
import br.com.florentino.repository.FormRepository;
import br.com.florentino.repository.PetRepository;
import br.com.florentino.utils.Constants;
import br.com.florentino.utils.StringFormatter;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void initiate() {
        FormRepository formRepository = new FormRepository();
        FormService formService = new FormService(formRepository);
        PetRepository petRepository = new PetRepository();
        PetService petService = new PetService(petRepository, formService, scanner);

        while (true) {
            System.out.println("=========================");
            System.out.println("SISTEMA DE ADOÇÃO DE PETS");
            System.out.println("=========================");

            System.out.println();
            System.out.println("Bem-vindo ao menu principal.");
            System.out.println("Qual das funções abaixo você deseja executar?");
            System.out.println();

            System.out.println("[1] Cadastrar um novo pet;");
            System.out.println("[2] Alterar os dados do pet cadastrado;");
            System.out.println("[3] Deletar um pet cadastrado;");
            System.out.println("[4] Listar todos os pets cadastrados;");
            System.out.println("[5] Listar pets por um ou dois critérios (nome, idade e/ou raça);");
            System.out.println("[6] Sair do programa.");

            System.out.println();
            System.out.print("Escolha uma opção: ");

            int option;

            try {
                option = scanner.nextInt();
                scanner.nextLine();
                System.out.println();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Digite somente números.");
                System.out.println();
                continue;
            }

            switch (option) {
                case 1 -> {
                    System.out.println("[CADASTRO DE PET]");

                    try {
                        petService.registerPet();
                    } catch (IOException e) {
                        System.out.println("Falha ao registrar pet. Tente novamente.");
                    }

                    System.out.println();
                }
                case 2 -> {
                    System.out.println("[ALTERAÇÃO DE DADO(S) DE PET CADASTRADO]");

                    try {
                        petService.updatePet();
                    } catch (IOException e) {
                        System.out.println("Falha ao alterar dados do pet. Tente novamente.");
                    }

                    System.out.println();
                }
                case 3 -> {
                    System.out.println("[EXCLUSÃO DE CADASTRO DE PET]");

                    try {
                        petService.deletePet();
                    } catch (IOException e) {
                        System.out.println("Falha ao deletar cadastro do pet. Tente novamente.");
                    }

                    System.out.println();
                }
                case 4 -> {
                    System.out.println("[LISTAGEM DE PETS CADASTRADOS]");

                    List<Pet> pets = petService.listAllPets();
                    int index = 1;

                    System.out.println();

                    System.out.println("[PETS ENCONTRADOS]");

                    for (Pet pet : pets) {
                        System.out.println(StringFormatter.formatPetDataForDisplay(index++, pet));
                    }

                    System.out.println();
                }
                case 5 -> {
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

                    System.out.println();
                }
                case 6 -> {
                    System.out.println("[FINALIZANDO PROGRAMA]");
                    System.out.println();
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
}