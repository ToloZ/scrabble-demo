package com.example.demo.service.scrabble;

import java.util.*;

public class ScrabbleComparator implements Comparator<String> {

    private HashMap<String, Integer> pointValues = new HashMap<>();;

    public ScrabbleComparator() {
        List<String> zeroPointList = new ArrayList(Arrays.asList("'", ""));
        List<String> onePointList = new ArrayList(Arrays.asList("a", "e", "i", "l", "n", "o", "r", "s", "t", "u"));
        List<String> twoPointsList = new ArrayList(Arrays.asList("d", "g"));
        List<String> threePointsList = new ArrayList(Arrays.asList("b", "c", "m", "p"));
        List<String> fourPointsList = new ArrayList(Arrays.asList("f", "h", "v", "w", "y"));
        List<String> fivePointsList = new ArrayList(Arrays.asList("k"));
        List<String> eightPointsList = new ArrayList(Arrays.asList("j", "x"));
        List<String> tenPointsList = new ArrayList(Arrays.asList("q", "z"));

        zeroPointList.forEach(letter -> pointValues.put(letter, 0));
        onePointList.forEach(letter -> pointValues.put(letter, 1));
        twoPointsList.forEach(letter -> pointValues.put(letter, 2));
        threePointsList.forEach(letter -> pointValues.put(letter, 3));
        fourPointsList.forEach(letter -> pointValues.put(letter, 4));
        fivePointsList.forEach(letter -> pointValues.put(letter, 5));
        eightPointsList.forEach(letter -> pointValues.put(letter, 8));
        tenPointsList.forEach(letter -> pointValues.put(letter, 10));
    }

    /**
     * @param word
     * @return the scrabble score value from a word
     */
    private Integer getScoreFromWord(String word) {
        return word.chars().mapToObj(c -> String.valueOf((char) c)).map(c -> pointValues.get(c)).reduce(0, Integer::sum);
    }

    /**
     * When both obtains the same score choose -1. Because if the value is 0 the set delete the word
     * from the collection
     *
     * @param w1
     * @param w2
     * @return
     */
    @Override
    public int compare(String w1, String w2) {
        return this.getScoreFromWord(w2) > this.getScoreFromWord(w1) ? 1 : -1;
    }
}
