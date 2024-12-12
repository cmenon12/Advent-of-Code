package com.christophermenon.adventofcode.year2024;

import java.math.BigInteger;
import java.util.ArrayList;

import com.christophermenon.adventofcode.PuzzleInput;

public class Day11 {

    public static void main(String[] args) {
        String puzzle = PuzzleInput.getAsString(2024, 11); 

        // Prepare an arraylist of stones as BigInteger numbers
        ArrayList<BigInteger> stones = new ArrayList<>();
        for (String item : puzzle.substring(0, puzzle.length()-1).split("\s+")) stones.add(new BigInteger(item));

        BigInteger total = BigInteger.valueOf(0L);
        for (int i = 0; i < stones.size(); i++) {
            total = total.add(getTotalStones(stones.get(i), 25));
        }
        System.out.printf("PART 1: %d%n", total);
    }

    private static BigInteger getTotalStones(BigInteger stone, int blinks) {
        ArrayList<BigInteger> stones = new ArrayList<>();
        stones.add(stone);

        for (int blink = 0; blink < blinks; blink++) {
            for (int i = 0; i < stones.size(); i++) {
                BigInteger val = stones.get(i);
                String stringVal = String.valueOf(val);
                if (val.equals(BigInteger.ZERO)) {
                    stones.set(i, BigInteger.ONE);
                } else if (stringVal.length() % 2 == 0) {
                    // Split the string value into two halves, convert back to BigInteger, and then save it
                    stones.set(i, new BigInteger(stringVal.substring(0, stringVal.length() / 2)));
                    stones.add(i + 1, new BigInteger(stringVal.substring(stringVal.length() / 2)));

                    // Need to increment an extra time as we've added a stone
                    i++;
                } else {
                    stones.set(i, val.multiply(BigInteger.valueOf(2024L)));
                }
            }
        }
        return BigInteger.valueOf(stones.size());
    }
}