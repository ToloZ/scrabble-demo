package com.example.demo.service.scrabble;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a set of letters
 */
public class Bag {

    Map<Character, Integer> letterMap = new HashMap<>();

    public Bag(String letters) {
        for (char c : letters.toCharArray()) {
            if (letterMap.containsKey(c)) {
                letterMap.put(c, letterMap.get(c) + 1);
            } else {
                letterMap.put(c, 1);
            }
        }
    }

    /**
     * @param word a word from the file
     * @return boolean value if my letters bag contains all letters of the given word
     */
    public boolean containsAllLetters(String word) {
        Map<Character, Integer> copy = new HashMap<>(this.letterMap);
        for (char c : word.toCharArray()) {
            if (copy.containsKey(c) && copy.get(c) > 0) {
                copy.put(c, copy.get(c) -1);
            } else {
                return false;
            }
        }
        return true;
    }
}
