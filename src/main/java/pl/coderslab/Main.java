package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class Main {
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] taskArr;
    public static void main(String[] args) {

        taskArr = prepareData();
            System.out.println("Please select an option: ");
            System.out.println("add");
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            Scanner scan = new Scanner(System.in);
             while (scan.hasNextLine()) {
                 String input = scan.nextLine();
                 switch (input) {
                     case "exit":
                         saveTabToFile();
                         System.out.println(ConsoleColors.RED + "Bye, bye.");
                         System.exit(0);
                         break;
                     case "add":
                         addTask();
                         break;
                     case "remove":
                         removeTask();
                         break;
                     case "list":
                         listTask();
                         break;
                     default:
                         System.out.println("Please select a correct option.");
                 }
                 System.out.println("Please select an option: ");
                 System.out.println("add");
                 System.out.println("remove");
                 System.out.println("list");
                 System.out.println("exit");
             }
    }
    public static void listTask(){
        for (int i = 0; i < taskArr.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < taskArr[i].length; j++) {
                System.out.print(taskArr[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static String[][] prepareData(){
        File file = new File("tasks.csv");
        int iteration = 0;
        int countLine=0;
        try (Scanner scan = new Scanner(file)){
            while (scan.hasNextLine()){
                scan.nextLine();
                countLine++;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        String[][] taskArr = new String[countLine][3];
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String tmpArr[]=scan.nextLine().split(",");
                for (int i=0; i<tmpArr.length;i++){
                    taskArr[iteration][i]=tmpArr[i];
                }
                    iteration++;
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return taskArr;
    }
    public static void addTask(){
        taskArr = Arrays.copyOf(taskArr, taskArr.length+1);
        taskArr[taskArr.length-1] = new String[3];
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description");
        taskArr[taskArr.length-1][0]=scan.nextLine();
        System.out.println("Please add task due date");
        taskArr[taskArr.length-1][1]=scan.nextLine();
        System.out.println("Is your task important: true/false");
        taskArr[taskArr.length-1][2]=scan.nextLine();

    }
    public static void removeTask(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        int line = scan.nextInt();
        try {
            if (line < taskArr.length) {
                taskArr = ArrayUtils.remove(taskArr, line);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }
    public static void saveTabToFile() {
        Path dir = Paths.get("tasks.csv");
        String[] lines = new String[taskArr.length];
        for (int i = 0; i < taskArr.length; i++) {
            lines[i] = String.join(",", taskArr[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
