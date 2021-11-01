package com.example.demo.service.scrabble;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

@Component
public class WordDataSet {

    @Value("${scrabble.wordpath:classpath:words.txt}")
    private String wordsFilePath;

    @Value("${scrabble.quote:true}")
    private boolean simpleQuote;

    @Value("${scrabble.trim:true}")
    private boolean trim;

    @Value("${scrabble.blank:false}")
    private boolean blankLineAccepted;

    private final Set<String> words = new TreeSet<>(new ScrabbleComparator());

    /**
     * Use @PostConstruct instead of Constructor because Spring gets a new instance first
     * and then reads the values from 'application.yaml'
     */
    @PostConstruct
    private void init() throws FileNotFoundException {
        Scanner sc = new Scanner(ResourceUtils.getFile(wordsFilePath));
        while (sc.hasNextLine()) {
            String fw = getFormattedWord(sc.nextLine());
            if (!fw.isEmpty() || blankLineAccepted)
                words.add(getFormattedWord(fw));
        }
    }

    /**
     * @return a sequential {@code Stream} over the elements in this collection
     */
    public Stream<String> stream() { return this.words.stream(); }

    /**
     * @param word The word from a line of the words file
     * @return a forrmated word according to the properties set
     */
    private String getFormattedWord(String word) {
        if (trim)
            word = word.trim();
        if (simpleQuote)
            word = word.replace("'", "");
        return word;
    }
}

