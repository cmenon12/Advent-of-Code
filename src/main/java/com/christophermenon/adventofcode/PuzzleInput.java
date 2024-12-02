package com.christophermenon.adventofcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author cmenon12
 */
public class PuzzleInput {

    public static String[] getAsArray(int year, int day) {
        String path = String.format("src/main/java/com/christophermenon/adventofcode/year%d/input%02d.txt", year, day);
        List<String> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                result.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.printf("The file at %s was not found!\n", path);
        }

        return result.toArray(String[]::new);
    }

    public static String getAsString(int year, int day) {
        String[] array = getAsArray(year, day);
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

}
