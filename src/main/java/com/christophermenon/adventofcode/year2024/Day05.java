package com.christophermenon.adventofcode.year2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day05 {

    public static void main(String[] args) {
        String puzzle = PuzzleInput.getAsString(2024, 5);
        String[] puzzleSplit = puzzle.split("\n\n", 2);
        String[] rules = puzzleSplit[0].split("\n");
        String[] books = puzzleSplit[1].split("\n");

        // Save the rules to a HashMap with Integers
        HashMap<Integer, ArrayList<Integer>> rulesMap = new HashMap<>();
        for (String rule : rules) {
            Integer key = Integer.valueOf(rule.split("\\|")[0]);
            Integer val = Integer.valueOf(rule.split("\\|")[1]);
            if (!rulesMap.containsKey(key)) rulesMap.put(key, new ArrayList<>());
            rulesMap.get(key).add(val);
        }

        int part1Total = 0;
        int part2Total = 0;
        for (String book: books) {
            
            // Get a list of book numbers
            ArrayList<Integer> bookNumbers = new ArrayList<>();
            for (String number : book.split(",")) bookNumbers.add(Integer.valueOf(number));

            boolean validBook = true;
            for (int i = 0; i < bookNumbers.size(); i++) {

                // If we have a set of rule numbers for the book number
                Integer bookNumber = bookNumbers.get(i);
                if (rulesMap.containsKey(bookNumber)) {

                    // Does the rule number occur before the book number?
                    List<Integer> bookNumbersSubset = bookNumbers.subList(0, i);
                    for (Integer ruleNumber : rulesMap.get(bookNumber)) {
                        if (bookNumbersSubset.contains(ruleNumber)) {
                            validBook = false;
                        }
                    }
                }
            }

            // If valid then save the middle value in the book for part 1
            if (validBook) {
                part1Total += bookNumbers.get(Math.floorDiv(bookNumbers.size(), 2));
            // Otherwise reorder and save for part 2
            } else {
                part2Total += reorderAndGetTotal(rulesMap, bookNumbers);
            }
        }

        System.out.printf("PART 1: %d%n", part1Total);
        System.out.printf("PART 2: %d%n", part2Total);
    }

    private static int reorderAndGetTotal(HashMap<Integer, ArrayList<Integer>> rulesMap, ArrayList<Integer> bookNumbers) {

        // For each entry in the rules
        for (Map.Entry<Integer, ArrayList<Integer>> rule : rulesMap.entrySet()) {
            Integer ruleKey = rule.getKey();
            ArrayList<Integer> ruleValues = rule.getValue();

            // If the rule key is in the book
            if (bookNumbers.contains(ruleKey)) {
                List<Integer> bookNumbersSubset = bookNumbers.subList(0, bookNumbers.indexOf(ruleKey));

                // Find the earliest index where an offending rule value occurs
                int earliestIndex = -1;
                for (Integer ruleVal : ruleValues) {
                    int ruleValIndex = bookNumbersSubset.indexOf(ruleVal);
                    if ((ruleValIndex < earliestIndex || earliestIndex == -1) && ruleValIndex != -1) {
                        earliestIndex = ruleValIndex;
                    }
                }

                // Move the rule key to just before the earliest offending value
                if (earliestIndex != -1) {
                    bookNumbers.remove(ruleKey);
                    bookNumbers.add(earliestIndex, ruleKey);
                }
            }
        }
        
        return bookNumbers.get(Math.floorDiv(bookNumbers.size(), 2));
    }
}
