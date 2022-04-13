package by.tovpenets;

/*
    Основное задание
    1. Допустим есть txt файл с номерами документов.
    Номером документа является строка, состоящая из букв и цифр(без
    служебных символов).
    Пусть этот файл содержит каждый номер документа с новой строки и в
    строке никакой другой информации, только номер документа.
    Валидный номер документа должен иметь длину 15 символов и
    начинаться с последовательности docnum(далее любая
    последовательность букв/цифр) или contract(далее любая
    последовательность букв/цифр).
    Написать программу для чтения информации из входного файла - путь к
    входному файлу должен задаваться через консоль.
    Программа должна проверять номера документов на валидность.
    Дополнительное задание
    2. Улучшить предыдущее задание. А именно:
    Валидные номера документов следует записать в один файл-отчет.
    Невалидные номера документов следует записать в другой файл-отчет,
    но после номеров документов следует добавить информацию о том,
    почему этот документ невалиден.
*/

/*
    -- только буква и цифры
    -- каждый номер с новой строки
    -- 15 символов
    -- начинается с "docnum" или "contract"
*/


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу: ");
        String path = scanner.nextLine();

        try {
            // Получили содержимое файла
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));

            // Список номеров документов из файла
            List<String> docNums = reader.lines().collect(Collectors.toList());

            List<String> validDocNums = new ArrayList<>();
            Map<String, String> invalidDocNums = new HashMap<>();
            for (String docNum : docNums) {
                if (docNum.length() != 15) {
                    invalidDocNums.put(docNum, "Длина не 15");
                    continue;
                }
                if (!(docNum.startsWith("docnum") || docNum.startsWith("contract"))) {
                    invalidDocNums.put(docNum, "Не начинается с docnum или contract");
                    continue;
                }
                if (!docNum.matches("^(\\d|\\w)+$")) {
                    invalidDocNums.put(docNum, "Содержит не только символы или цифры");
                    continue;
                }

                validDocNums.add(docNum);
            }

            FileWriter valid = new FileWriter("./resources/valid.txt");
            FileWriter invalid = new FileWriter("./resources/invalid.txt");

            for (String docNum : validDocNums) valid.write(docNum + "\n");

            for (Map.Entry<String, String> entry : invalidDocNums.entrySet())
                invalid.write(entry.getKey() + " error: " + entry.getValue() + "\n");

            reader.close();
            valid.close();
            invalid.close();
        } catch (IOException e){
            System.out.println("Something wrong");
            e.printStackTrace();
        }
        scanner.close();
    }
}
