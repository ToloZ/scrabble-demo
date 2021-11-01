package com.example.demo.service.scrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScrabbleService {

    @Autowired
    private WordDataSet wordDataSet;

    /**
     * Returns a list of words that can be spelled from the given set of letters.
     * It is sorted by its Scrabble point value.
     *
     * @param letters The letters to form words from
     * @return A sorted set of words
     */
    public List<String> getWords(String letters) {
        Bag bag = new Bag(letters);
        return wordDataSet.stream()
                .filter(w ->  w.length() <= letters.length())
                .filter(bag::containsAllLetters)
                .collect(Collectors.toList());
    }
}
