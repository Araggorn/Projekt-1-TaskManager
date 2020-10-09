package pl.codersLab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.coderslab.ConsoleColors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main00 {
    static final String filename = "tasks.csv";
    static final String[] options = {"add", "remove", "list", "exit"};
    static String[][] tasks;


    public static void main(String[] args) throws NullPointerException {
        tasks = loadDataToTab(filename);
        System.out.println(ConsoleColors.GREEN + "Siemka, co chciałbyś zrobić?" + Arrays.toString(options));
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    Addtask();
                    break;
                case "list":
                    printTab(tasks);
                    break;
                case "remove":
                    RemoveprintTab(tasks, getTheNumber());
                    System.out.println("Value was successfully deleted.");
                    break;
                case "exit":
                    saveTabToFile(filename, tasks);
                    exitApp();
                    System.exit(0);
                default:
                    System.out.println("Please select a correct option.");
            }
            printOptions(options);
        }


    }

    private static String[][] loadDataToTab(String filename) {
        Path dir = Paths.get(filename);
        if (!Files.exists(dir)) {
            System.out.println("File not exist.");
            System.exit(0);
        }

        String[][] tab = null;

        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void exitApp() {
        System.out.println(pl.coderslab.ConsoleColors.RED + "Bye Bye" + ConsoleColors.RESET);
    }

    public static void Addtask() throws NullPointerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add some task with priority please:");
        String s = scanner.next();
        System.out.println(("Please add task due to date"));
        String p = scanner.next();
        System.out.println("Is Your task important (true/false)?");
        String x = scanner.next();
        try {
            tasks = Arrays.copyOf(tasks, tasks.length + 1);
            tasks[tasks.length - 1] = new String[3];
            tasks[tasks.length - 1][0] = s;
            tasks[tasks.length - 1][1] = p;
            tasks[tasks.length - 1][2] = x;
        } catch (NullPointerException e) {
            System.out.println("Try again!");
            ;
        }
    }

    public static void RemoveprintTab(String[][] array, int index) {
        try {
            if (index < array.length) {
                tasks = ArrayUtils.remove(array, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }
    }


    public static void printOptions(String[] options) {
        {
            System.out.println(pl.coderslab.ConsoleColors.PURPLE);
            System.out.println("Please select an option: " + pl.coderslab.ConsoleColors.RESET);
            for (String option : options) System.out.println(option);
        }
    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static void saveTabToFile(String fileName, String[][] tab) {

        Path dir = Paths.get(fileName);
        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static int getTheNumber() {
        Scanner scani = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String n = scani.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            scani.nextLine();
        }
        return Integer.parseInt(n);
    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + ". ");
            }
            System.out.println();
        }
        System.out.println(ConsoleColors.BLUE + "Koniec listy");
    }
}


