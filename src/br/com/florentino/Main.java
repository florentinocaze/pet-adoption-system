package br.com.florentino;

import br.com.florentino.services.MenuService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MenuService menuService = new MenuService(scanner);

        menuService.initiate();
    }
}
